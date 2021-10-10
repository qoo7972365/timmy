/*      */ package sun.text.normalizer;
/*      */ 
/*      */ import java.text.ParsePosition;
/*      */ import java.util.Iterator;
/*      */ import java.util.TreeSet;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class UnicodeSet
/*      */   implements UnicodeMatcher
/*      */ {
/*      */   private static final int LOW = 0;
/*      */   private static final int HIGH = 1114112;
/*      */   public static final int MIN_VALUE = 0;
/*      */   public static final int MAX_VALUE = 1114111;
/*      */   private int len;
/*      */   private int[] list;
/*      */   private int[] rangeList;
/*      */   private int[] buffer;
/*  301 */   TreeSet<String> strings = new TreeSet<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  312 */   private String pat = null;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int START_EXTRA = 16;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int GROW_EXTRA = 16;
/*      */ 
/*      */   
/*  323 */   private static UnicodeSet[] INCLUSIONS = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public UnicodeSet() {
/*  334 */     this.list = new int[17];
/*  335 */     this.list[this.len++] = 1114112;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public UnicodeSet(int paramInt1, int paramInt2) {
/*  347 */     this();
/*  348 */     complement(paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public UnicodeSet(String paramString) {
/*  360 */     this();
/*  361 */     applyPattern(paramString, (ParsePosition)null, (SymbolTable)null, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public UnicodeSet set(UnicodeSet paramUnicodeSet) {
/*  371 */     this.list = (int[])paramUnicodeSet.list.clone();
/*  372 */     this.len = paramUnicodeSet.len;
/*  373 */     this.pat = paramUnicodeSet.pat;
/*  374 */     this.strings = (TreeSet<String>)paramUnicodeSet.strings.clone();
/*  375 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final UnicodeSet applyPattern(String paramString) {
/*  388 */     return applyPattern(paramString, (ParsePosition)null, (SymbolTable)null, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void _appendToPat(StringBuffer paramStringBuffer, String paramString, boolean paramBoolean) {
/*  396 */     for (int i = 0; i < paramString.length(); i += UTF16.getCharCount(i)) {
/*  397 */       _appendToPat(paramStringBuffer, UTF16.charAt(paramString, i), paramBoolean);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void _appendToPat(StringBuffer paramStringBuffer, int paramInt, boolean paramBoolean) {
/*  406 */     if (paramBoolean && Utility.isUnprintable(paramInt))
/*      */     {
/*      */       
/*  409 */       if (Utility.escapeUnprintable(paramStringBuffer, paramInt)) {
/*      */         return;
/*      */       }
/*      */     }
/*      */     
/*  414 */     switch (paramInt) {
/*      */       case 36:
/*      */       case 38:
/*      */       case 45:
/*      */       case 58:
/*      */       case 91:
/*      */       case 92:
/*      */       case 93:
/*      */       case 94:
/*      */       case 123:
/*      */       case 125:
/*  425 */         paramStringBuffer.append('\\');
/*      */         break;
/*      */       
/*      */       default:
/*  429 */         if (UCharacterProperty.isRuleWhiteSpace(paramInt)) {
/*  430 */           paramStringBuffer.append('\\');
/*      */         }
/*      */         break;
/*      */     } 
/*  434 */     UTF16.append(paramStringBuffer, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private StringBuffer _toPattern(StringBuffer paramStringBuffer, boolean paramBoolean) {
/*  444 */     if (this.pat != null) {
/*      */       
/*  446 */       byte b = 0;
/*  447 */       for (int i = 0; i < this.pat.length(); ) {
/*  448 */         int j = UTF16.charAt(this.pat, i);
/*  449 */         i += UTF16.getCharCount(j);
/*  450 */         if (paramBoolean && Utility.isUnprintable(j)) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  455 */           if (b % 2 == 1) {
/*  456 */             paramStringBuffer.setLength(paramStringBuffer.length() - 1);
/*      */           }
/*  458 */           Utility.escapeUnprintable(paramStringBuffer, j);
/*  459 */           b = 0; continue;
/*      */         } 
/*  461 */         UTF16.append(paramStringBuffer, j);
/*  462 */         if (j == 92) {
/*  463 */           b++; continue;
/*      */         } 
/*  465 */         b = 0;
/*      */       } 
/*      */ 
/*      */       
/*  469 */       return paramStringBuffer;
/*      */     } 
/*      */     
/*  472 */     return _generatePattern(paramStringBuffer, paramBoolean, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StringBuffer _generatePattern(StringBuffer paramStringBuffer, boolean paramBoolean1, boolean paramBoolean2) {
/*  484 */     paramStringBuffer.append('[');
/*      */     
/*  486 */     int i = getRangeCount();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  491 */     if (i > 1 && 
/*  492 */       getRangeStart(0) == 0 && 
/*  493 */       getRangeEnd(i - 1) == 1114111) {
/*      */ 
/*      */       
/*  496 */       paramStringBuffer.append('^');
/*      */       
/*  498 */       for (byte b = 1; b < i; b++) {
/*  499 */         int j = getRangeEnd(b - 1) + 1;
/*  500 */         int k = getRangeStart(b) - 1;
/*  501 */         _appendToPat(paramStringBuffer, j, paramBoolean1);
/*  502 */         if (j != k) {
/*  503 */           if (j + 1 != k) {
/*  504 */             paramStringBuffer.append('-');
/*      */           }
/*  506 */           _appendToPat(paramStringBuffer, k, paramBoolean1);
/*      */         }
/*      */       
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  513 */       for (byte b = 0; b < i; b++) {
/*  514 */         int j = getRangeStart(b);
/*  515 */         int k = getRangeEnd(b);
/*  516 */         _appendToPat(paramStringBuffer, j, paramBoolean1);
/*  517 */         if (j != k) {
/*  518 */           if (j + 1 != k) {
/*  519 */             paramStringBuffer.append('-');
/*      */           }
/*  521 */           _appendToPat(paramStringBuffer, k, paramBoolean1);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  526 */     if (paramBoolean2 && this.strings.size() > 0) {
/*  527 */       Iterator<String> iterator = this.strings.iterator();
/*  528 */       while (iterator.hasNext()) {
/*  529 */         paramStringBuffer.append('{');
/*  530 */         _appendToPat(paramStringBuffer, iterator.next(), paramBoolean1);
/*  531 */         paramStringBuffer.append('}');
/*      */       } 
/*      */     } 
/*  534 */     return paramStringBuffer.append(']');
/*      */   }
/*      */ 
/*      */   
/*      */   private UnicodeSet add_unchecked(int paramInt1, int paramInt2) {
/*  539 */     if (paramInt1 < 0 || paramInt1 > 1114111) {
/*  540 */       throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(paramInt1, 6));
/*      */     }
/*  542 */     if (paramInt2 < 0 || paramInt2 > 1114111) {
/*  543 */       throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(paramInt2, 6));
/*      */     }
/*  545 */     if (paramInt1 < paramInt2) {
/*  546 */       add(range(paramInt1, paramInt2), 2, 0);
/*  547 */     } else if (paramInt1 == paramInt2) {
/*  548 */       add(paramInt1);
/*      */     } 
/*  550 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final UnicodeSet add(int paramInt) {
/*  560 */     return add_unchecked(paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   private final UnicodeSet add_unchecked(int paramInt) {
/*  565 */     if (paramInt < 0 || paramInt > 1114111) {
/*  566 */       throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(paramInt, 6));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  572 */     int i = findCodePoint(paramInt);
/*      */ 
/*      */     
/*  575 */     if ((i & 0x1) != 0) return this;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  589 */     if (paramInt == this.list[i] - 1) {
/*      */       
/*  591 */       this.list[i] = paramInt;
/*      */       
/*  593 */       if (paramInt == 1114111) {
/*  594 */         ensureCapacity(this.len + 1);
/*  595 */         this.list[this.len++] = 1114112;
/*      */       } 
/*  597 */       if (i > 0 && paramInt == this.list[i - 1])
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  603 */         System.arraycopy(this.list, i + 1, this.list, i - 1, this.len - i - 1);
/*  604 */         this.len -= 2;
/*      */       }
/*      */     
/*      */     }
/*  608 */     else if (i > 0 && paramInt == this.list[i - 1]) {
/*      */       
/*  610 */       this.list[i - 1] = this.list[i - 1] + 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  630 */       if (this.len + 2 > this.list.length) {
/*  631 */         int[] arrayOfInt = new int[this.len + 2 + 16];
/*  632 */         if (i != 0) System.arraycopy(this.list, 0, arrayOfInt, 0, i); 
/*  633 */         System.arraycopy(this.list, i, arrayOfInt, i + 2, this.len - i);
/*  634 */         this.list = arrayOfInt;
/*      */       } else {
/*  636 */         System.arraycopy(this.list, i, this.list, i + 2, this.len - i);
/*      */       } 
/*      */       
/*  639 */       this.list[i] = paramInt;
/*  640 */       this.list[i + 1] = paramInt + 1;
/*  641 */       this.len += 2;
/*      */     } 
/*      */     
/*  644 */     this.pat = null;
/*  645 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final UnicodeSet add(String paramString) {
/*  659 */     int i = getSingleCP(paramString);
/*  660 */     if (i < 0) {
/*  661 */       this.strings.add(paramString);
/*  662 */       this.pat = null;
/*      */     } else {
/*  664 */       add_unchecked(i, i);
/*      */     } 
/*  666 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getSingleCP(String paramString) {
/*  675 */     if (paramString.length() < 1) {
/*  676 */       throw new IllegalArgumentException("Can't use zero-length strings in UnicodeSet");
/*      */     }
/*  678 */     if (paramString.length() > 2) return -1; 
/*  679 */     if (paramString.length() == 1) return paramString.charAt(0);
/*      */ 
/*      */     
/*  682 */     int i = UTF16.charAt(paramString, 0);
/*  683 */     if (i > 65535) {
/*  684 */       return i;
/*      */     }
/*  686 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public UnicodeSet complement(int paramInt1, int paramInt2) {
/*  702 */     if (paramInt1 < 0 || paramInt1 > 1114111) {
/*  703 */       throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(paramInt1, 6));
/*      */     }
/*  705 */     if (paramInt2 < 0 || paramInt2 > 1114111) {
/*  706 */       throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(paramInt2, 6));
/*      */     }
/*  708 */     if (paramInt1 <= paramInt2) {
/*  709 */       xor(range(paramInt1, paramInt2), 2, 0);
/*      */     }
/*  711 */     this.pat = null;
/*  712 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public UnicodeSet complement() {
/*  721 */     if (this.list[0] == 0) {
/*  722 */       System.arraycopy(this.list, 1, this.list, 0, this.len - 1);
/*  723 */       this.len--;
/*      */     } else {
/*  725 */       ensureCapacity(this.len + 1);
/*  726 */       System.arraycopy(this.list, 0, this.list, 1, this.len);
/*  727 */       this.list[0] = 0;
/*  728 */       this.len++;
/*      */     } 
/*  730 */     this.pat = null;
/*  731 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contains(int paramInt) {
/*  741 */     if (paramInt < 0 || paramInt > 1114111) {
/*  742 */       throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(paramInt, 6));
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
/*  754 */     int i = findCodePoint(paramInt);
/*      */     
/*  756 */     return ((i & 0x1) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int findCodePoint(int paramInt) {
/*  781 */     if (paramInt < this.list[0]) return 0;
/*      */ 
/*      */     
/*  784 */     if (this.len >= 2 && paramInt >= this.list[this.len - 2]) return this.len - 1; 
/*  785 */     int i = 0;
/*  786 */     int j = this.len - 1;
/*      */ 
/*      */     
/*      */     while (true) {
/*  790 */       int k = i + j >>> 1;
/*  791 */       if (k == i) return j; 
/*  792 */       if (paramInt < this.list[k]) {
/*  793 */         j = k; continue;
/*      */       } 
/*  795 */       i = k;
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
/*      */   public UnicodeSet addAll(UnicodeSet paramUnicodeSet) {
/*  811 */     add(paramUnicodeSet.list, paramUnicodeSet.len, 0);
/*  812 */     this.strings.addAll(paramUnicodeSet.strings);
/*  813 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public UnicodeSet retainAll(UnicodeSet paramUnicodeSet) {
/*  827 */     retain(paramUnicodeSet.list, paramUnicodeSet.len, 0);
/*  828 */     this.strings.retainAll(paramUnicodeSet.strings);
/*  829 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public UnicodeSet removeAll(UnicodeSet paramUnicodeSet) {
/*  843 */     retain(paramUnicodeSet.list, paramUnicodeSet.len, 2);
/*  844 */     this.strings.removeAll(paramUnicodeSet.strings);
/*  845 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public UnicodeSet clear() {
/*  854 */     this.list[0] = 1114112;
/*  855 */     this.len = 1;
/*  856 */     this.pat = null;
/*  857 */     this.strings.clear();
/*  858 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRangeCount() {
/*  869 */     return this.len / 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRangeStart(int paramInt) {
/*  882 */     return this.list[paramInt * 2];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRangeEnd(int paramInt) {
/*  895 */     return this.list[paramInt * 2 + 1] - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   UnicodeSet applyPattern(String paramString, ParsePosition paramParsePosition, SymbolTable paramSymbolTable, int paramInt) {
/*  931 */     boolean bool = (paramParsePosition == null) ? true : false;
/*  932 */     if (bool) {
/*  933 */       paramParsePosition = new ParsePosition(0);
/*      */     }
/*      */     
/*  936 */     StringBuffer stringBuffer = new StringBuffer();
/*  937 */     RuleCharacterIterator ruleCharacterIterator = new RuleCharacterIterator(paramString, paramSymbolTable, paramParsePosition);
/*      */     
/*  939 */     applyPattern(ruleCharacterIterator, paramSymbolTable, stringBuffer, paramInt);
/*  940 */     if (ruleCharacterIterator.inVariable()) {
/*  941 */       syntaxError(ruleCharacterIterator, "Extra chars in variable value");
/*      */     }
/*  943 */     this.pat = stringBuffer.toString();
/*  944 */     if (bool) {
/*  945 */       int i = paramParsePosition.getIndex();
/*      */ 
/*      */       
/*  948 */       if ((paramInt & 0x1) != 0) {
/*  949 */         i = Utility.skipWhitespace(paramString, i);
/*      */       }
/*      */       
/*  952 */       if (i != paramString.length()) {
/*  953 */         throw new IllegalArgumentException("Parse of \"" + paramString + "\" failed at " + i);
/*      */       }
/*      */     } 
/*      */     
/*  957 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void applyPattern(RuleCharacterIterator paramRuleCharacterIterator, SymbolTable paramSymbolTable, StringBuffer paramStringBuffer, int paramInt) {
/*  980 */     int i = 3;
/*      */     
/*  982 */     if ((paramInt & 0x1) != 0) {
/*  983 */       i |= 0x4;
/*      */     }
/*      */     
/*  986 */     StringBuffer stringBuffer1 = new StringBuffer(), stringBuffer2 = null;
/*  987 */     boolean bool1 = false;
/*  988 */     UnicodeSet unicodeSet = null;
/*  989 */     Object object = null;
/*      */ 
/*      */ 
/*      */     
/*  993 */     char c1 = Character.MIN_VALUE; int j = 0; byte b = 0;
/*  994 */     char c2 = Character.MIN_VALUE;
/*      */     
/*  996 */     boolean bool2 = false;
/*      */     
/*  998 */     clear();
/*      */     
/* 1000 */     while (b != 2 && !paramRuleCharacterIterator.atEnd()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1010 */       int k = 0;
/* 1011 */       boolean bool = false;
/* 1012 */       UnicodeSet unicodeSet1 = null;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1017 */       byte b1 = 0;
/* 1018 */       if (resemblesPropertyPattern(paramRuleCharacterIterator, i)) {
/* 1019 */         b1 = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1032 */         object = paramRuleCharacterIterator.getPos(object);
/* 1033 */         k = paramRuleCharacterIterator.next(i);
/* 1034 */         bool = paramRuleCharacterIterator.isEscaped();
/*      */         
/* 1036 */         if (k == 91 && !bool) {
/* 1037 */           if (b == 1) {
/* 1038 */             paramRuleCharacterIterator.setPos(object);
/* 1039 */             b1 = 1;
/*      */           } else {
/*      */             
/* 1042 */             b = 1;
/* 1043 */             stringBuffer1.append('[');
/* 1044 */             object = paramRuleCharacterIterator.getPos(object);
/* 1045 */             k = paramRuleCharacterIterator.next(i);
/* 1046 */             bool = paramRuleCharacterIterator.isEscaped();
/* 1047 */             if (k == 94 && !bool) {
/* 1048 */               bool2 = true;
/* 1049 */               stringBuffer1.append('^');
/* 1050 */               object = paramRuleCharacterIterator.getPos(object);
/* 1051 */               k = paramRuleCharacterIterator.next(i);
/* 1052 */               bool = paramRuleCharacterIterator.isEscaped();
/*      */             } 
/*      */ 
/*      */             
/* 1056 */             if (k == 45) {
/* 1057 */               bool = true;
/*      */             } else {
/*      */               
/* 1060 */               paramRuleCharacterIterator.setPos(object);
/*      */               continue;
/*      */             } 
/*      */           } 
/* 1064 */         } else if (paramSymbolTable != null) {
/* 1065 */           UnicodeMatcher unicodeMatcher = paramSymbolTable.lookupMatcher(k);
/* 1066 */           if (unicodeMatcher != null) {
/*      */             try {
/* 1068 */               unicodeSet1 = (UnicodeSet)unicodeMatcher;
/* 1069 */               b1 = 3;
/* 1070 */             } catch (ClassCastException classCastException) {
/* 1071 */               syntaxError(paramRuleCharacterIterator, "Syntax error");
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1082 */       if (b1 != 0) {
/* 1083 */         if (c1 == '\001') {
/* 1084 */           if (c2) {
/* 1085 */             syntaxError(paramRuleCharacterIterator, "Char expected after operator");
/*      */           }
/* 1087 */           add_unchecked(j, j);
/* 1088 */           _appendToPat(stringBuffer1, j, false);
/* 1089 */           c1 = c2 = Character.MIN_VALUE;
/*      */         } 
/*      */         
/* 1092 */         if (c2 == '-' || c2 == '&') {
/* 1093 */           stringBuffer1.append(c2);
/*      */         }
/*      */         
/* 1096 */         if (unicodeSet1 == null) {
/* 1097 */           if (unicodeSet == null) unicodeSet = new UnicodeSet(); 
/* 1098 */           unicodeSet1 = unicodeSet;
/*      */         } 
/* 1100 */         switch (b1) {
/*      */           case 1:
/* 1102 */             unicodeSet1.applyPattern(paramRuleCharacterIterator, paramSymbolTable, stringBuffer1, paramInt);
/*      */             break;
/*      */           case 2:
/* 1105 */             paramRuleCharacterIterator.skipIgnored(i);
/* 1106 */             unicodeSet1.applyPropertyPattern(paramRuleCharacterIterator, stringBuffer1, paramSymbolTable);
/*      */             break;
/*      */           case 3:
/* 1109 */             unicodeSet1._toPattern(stringBuffer1, false);
/*      */             break;
/*      */         } 
/*      */         
/* 1113 */         bool1 = true;
/*      */         
/* 1115 */         if (!b) {
/*      */           
/* 1117 */           set(unicodeSet1);
/* 1118 */           b = 2;
/*      */           
/*      */           break;
/*      */         } 
/* 1122 */         switch (c2) {
/*      */           case true:
/* 1124 */             removeAll(unicodeSet1);
/*      */             break;
/*      */           case true:
/* 1127 */             retainAll(unicodeSet1);
/*      */             break;
/*      */           case false:
/* 1130 */             addAll(unicodeSet1);
/*      */             break;
/*      */         } 
/*      */         
/* 1134 */         c2 = Character.MIN_VALUE;
/* 1135 */         c1 = '\002';
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 1140 */       if (b == 0) {
/* 1141 */         syntaxError(paramRuleCharacterIterator, "Missing '['");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1148 */       if (!bool) {
/* 1149 */         boolean bool3; boolean bool4; switch (k) {
/*      */           case 93:
/* 1151 */             if (c1 == '\001') {
/* 1152 */               add_unchecked(j, j);
/* 1153 */               _appendToPat(stringBuffer1, j, false);
/*      */             } 
/*      */             
/* 1156 */             if (c2 == '-') {
/* 1157 */               add_unchecked(c2, c2);
/* 1158 */               stringBuffer1.append(c2);
/* 1159 */             } else if (c2 == '&') {
/* 1160 */               syntaxError(paramRuleCharacterIterator, "Trailing '&'");
/*      */             } 
/* 1162 */             stringBuffer1.append(']');
/* 1163 */             b = 2;
/*      */             continue;
/*      */           case 45:
/* 1166 */             if (!c2) {
/* 1167 */               if (c1 != Character.MIN_VALUE) {
/* 1168 */                 c2 = (char)k;
/*      */                 
/*      */                 continue;
/*      */               } 
/* 1172 */               add_unchecked(k, k);
/* 1173 */               k = paramRuleCharacterIterator.next(i);
/* 1174 */               bool = paramRuleCharacterIterator.isEscaped();
/* 1175 */               if (k == 93 && !bool) {
/* 1176 */                 stringBuffer1.append("-]");
/* 1177 */                 b = 2;
/*      */                 
/*      */                 continue;
/*      */               } 
/*      */             } 
/* 1182 */             syntaxError(paramRuleCharacterIterator, "'-' not after char or set");
/*      */             break;
/*      */           case 38:
/* 1185 */             if (c1 == '\002' && c2 == '\000') {
/* 1186 */               c2 = (char)k;
/*      */               continue;
/*      */             } 
/* 1189 */             syntaxError(paramRuleCharacterIterator, "'&' not after set");
/*      */             break;
/*      */           case 94:
/* 1192 */             syntaxError(paramRuleCharacterIterator, "'^' not after '['");
/*      */             break;
/*      */           case 123:
/* 1195 */             if (c2 != '\000') {
/* 1196 */               syntaxError(paramRuleCharacterIterator, "Missing operand after operator");
/*      */             }
/* 1198 */             if (c1 == '\001') {
/* 1199 */               add_unchecked(j, j);
/* 1200 */               _appendToPat(stringBuffer1, j, false);
/*      */             } 
/* 1202 */             c1 = Character.MIN_VALUE;
/* 1203 */             if (stringBuffer2 == null) {
/* 1204 */               stringBuffer2 = new StringBuffer();
/*      */             } else {
/* 1206 */               stringBuffer2.setLength(0);
/*      */             } 
/* 1208 */             bool3 = false;
/* 1209 */             while (!paramRuleCharacterIterator.atEnd()) {
/* 1210 */               k = paramRuleCharacterIterator.next(i);
/* 1211 */               bool = paramRuleCharacterIterator.isEscaped();
/* 1212 */               if (k == 125 && !bool) {
/* 1213 */                 bool3 = true;
/*      */                 break;
/*      */               } 
/* 1216 */               UTF16.append(stringBuffer2, k);
/*      */             } 
/* 1218 */             if (stringBuffer2.length() < 1 || !bool3) {
/* 1219 */               syntaxError(paramRuleCharacterIterator, "Invalid multicharacter string");
/*      */             }
/*      */ 
/*      */ 
/*      */             
/* 1224 */             add(stringBuffer2.toString());
/* 1225 */             stringBuffer1.append('{');
/* 1226 */             _appendToPat(stringBuffer1, stringBuffer2.toString(), false);
/* 1227 */             stringBuffer1.append('}');
/*      */             continue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 36:
/* 1236 */             object = paramRuleCharacterIterator.getPos(object);
/* 1237 */             k = paramRuleCharacterIterator.next(i);
/* 1238 */             bool = paramRuleCharacterIterator.isEscaped();
/* 1239 */             bool4 = (k == 93 && !bool) ? true : false;
/* 1240 */             if (paramSymbolTable == null && !bool4) {
/* 1241 */               k = 36;
/* 1242 */               paramRuleCharacterIterator.setPos(object);
/*      */               break;
/*      */             } 
/* 1245 */             if (bool4 && c2 == '\000') {
/* 1246 */               if (c1 == '\001') {
/* 1247 */                 add_unchecked(j, j);
/* 1248 */                 _appendToPat(stringBuffer1, j, false);
/*      */               } 
/* 1250 */               add_unchecked(65535);
/* 1251 */               bool1 = true;
/* 1252 */               stringBuffer1.append('$').append(']');
/* 1253 */               b = 2;
/*      */               continue;
/*      */             } 
/* 1256 */             syntaxError(paramRuleCharacterIterator, "Unquoted '$'");
/*      */             break;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       } 
/* 1267 */       switch (c1) {
/*      */         case 0:
/* 1269 */           c1 = '\001';
/* 1270 */           j = k;
/*      */         
/*      */         case 1:
/* 1273 */           if (c2 == '-') {
/* 1274 */             if (j >= k)
/*      */             {
/*      */               
/* 1277 */               syntaxError(paramRuleCharacterIterator, "Invalid range");
/*      */             }
/* 1279 */             add_unchecked(j, k);
/* 1280 */             _appendToPat(stringBuffer1, j, false);
/* 1281 */             stringBuffer1.append(c2);
/* 1282 */             _appendToPat(stringBuffer1, k, false);
/* 1283 */             c1 = c2 = Character.MIN_VALUE; continue;
/*      */           } 
/* 1285 */           add_unchecked(j, j);
/* 1286 */           _appendToPat(stringBuffer1, j, false);
/* 1287 */           j = k;
/*      */ 
/*      */         
/*      */         case 2:
/* 1291 */           if (c2 != '\000') {
/* 1292 */             syntaxError(paramRuleCharacterIterator, "Set expected after operator");
/*      */           }
/* 1294 */           j = k;
/* 1295 */           c1 = '\001';
/*      */       } 
/*      */ 
/*      */     
/*      */     } 
/* 1300 */     if (b != 2) {
/* 1301 */       syntaxError(paramRuleCharacterIterator, "Missing ']'");
/*      */     }
/*      */     
/* 1304 */     paramRuleCharacterIterator.skipIgnored(i);
/*      */     
/* 1306 */     if (bool2) {
/* 1307 */       complement();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1312 */     if (bool1) {
/* 1313 */       paramStringBuffer.append(stringBuffer1.toString());
/*      */     } else {
/* 1315 */       _generatePattern(paramStringBuffer, false, true);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void syntaxError(RuleCharacterIterator paramRuleCharacterIterator, String paramString) {
/* 1320 */     throw new IllegalArgumentException("Error: " + paramString + " at \"" + 
/* 1321 */         Utility.escape(paramRuleCharacterIterator.toString()) + '"');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void ensureCapacity(int paramInt) {
/* 1330 */     if (paramInt <= this.list.length)
/* 1331 */       return;  int[] arrayOfInt = new int[paramInt + 16];
/* 1332 */     System.arraycopy(this.list, 0, arrayOfInt, 0, this.len);
/* 1333 */     this.list = arrayOfInt;
/*      */   }
/*      */   
/*      */   private void ensureBufferCapacity(int paramInt) {
/* 1337 */     if (this.buffer != null && paramInt <= this.buffer.length)
/* 1338 */       return;  this.buffer = new int[paramInt + 16];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] range(int paramInt1, int paramInt2) {
/* 1345 */     if (this.rangeList == null) {
/* 1346 */       this.rangeList = new int[] { paramInt1, paramInt2 + 1, 1114112 };
/*      */     } else {
/* 1348 */       this.rangeList[0] = paramInt1;
/* 1349 */       this.rangeList[1] = paramInt2 + 1;
/*      */     } 
/* 1351 */     return this.rangeList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private UnicodeSet xor(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/*      */     int j;
/* 1362 */     ensureBufferCapacity(this.len + paramInt1);
/* 1363 */     byte b1 = 0, b2 = 0, b3 = 0;
/* 1364 */     int i = this.list[b1++];
/*      */     
/* 1366 */     if (paramInt2 == 1 || paramInt2 == 2) {
/* 1367 */       j = 0;
/* 1368 */       if (paramArrayOfint[b2] == 0) {
/* 1369 */         b2++;
/* 1370 */         j = paramArrayOfint[b2];
/*      */       } 
/*      */     } else {
/* 1373 */       j = paramArrayOfint[b2++];
/*      */     } 
/*      */ 
/*      */     
/*      */     while (true) {
/* 1378 */       while (i < j) {
/* 1379 */         this.buffer[b3++] = i;
/* 1380 */         i = this.list[b1++];
/* 1381 */       }  if (j < i) {
/* 1382 */         this.buffer[b3++] = j;
/* 1383 */         j = paramArrayOfint[b2++]; continue;
/* 1384 */       }  if (i != 1114112) {
/*      */         
/* 1386 */         i = this.list[b1++];
/* 1387 */         j = paramArrayOfint[b2++]; continue;
/*      */       }  break;
/* 1389 */     }  this.buffer[b3++] = 1114112;
/* 1390 */     this.len = b3;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1395 */     int[] arrayOfInt = this.list;
/* 1396 */     this.list = this.buffer;
/* 1397 */     this.buffer = arrayOfInt;
/* 1398 */     this.pat = null;
/* 1399 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private UnicodeSet add(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 1408 */     ensureBufferCapacity(this.len + paramInt1);
/* 1409 */     byte b1 = 0, b2 = 0, b3 = 0;
/* 1410 */     int i = this.list[b1++];
/* 1411 */     int j = paramArrayOfint[b2++];
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/* 1416 */       switch (paramInt2) {
/*      */         case 0:
/* 1418 */           if (i < j) {
/*      */             
/* 1420 */             if (b3 && i <= this.buffer[b3 - 1]) {
/*      */               
/* 1422 */               i = max(this.list[b1], this.buffer[--b3]);
/*      */             } else {
/*      */               
/* 1425 */               this.buffer[b3++] = i;
/* 1426 */               i = this.list[b1];
/*      */             } 
/* 1428 */             b1++;
/* 1429 */             paramInt2 ^= 0x1; continue;
/* 1430 */           }  if (j < i) {
/* 1431 */             if (b3 > 0 && j <= this.buffer[b3 - 1]) {
/* 1432 */               j = max(paramArrayOfint[b2], this.buffer[--b3]);
/*      */             } else {
/* 1434 */               this.buffer[b3++] = j;
/* 1435 */               j = paramArrayOfint[b2];
/*      */             } 
/* 1437 */             b2++;
/* 1438 */             paramInt2 ^= 0x2; continue;
/*      */           } 
/* 1440 */           if (i == 1114112) {
/*      */             break;
/*      */           }
/* 1443 */           if (b3 > 0 && i <= this.buffer[b3 - 1]) {
/* 1444 */             i = max(this.list[b1], this.buffer[--b3]);
/*      */           } else {
/*      */             
/* 1447 */             this.buffer[b3++] = i;
/* 1448 */             i = this.list[b1];
/*      */           } 
/* 1450 */           b1++;
/* 1451 */           paramInt2 ^= 0x1;
/* 1452 */           j = paramArrayOfint[b2++]; paramInt2 ^= 0x2;
/*      */ 
/*      */         
/*      */         case 3:
/* 1456 */           if (j <= i) {
/* 1457 */             if (i == 1114112)
/* 1458 */               break;  this.buffer[b3++] = i;
/*      */           } else {
/* 1460 */             if (j == 1114112)
/* 1461 */               break;  this.buffer[b3++] = j;
/*      */           } 
/* 1463 */           i = this.list[b1++]; paramInt2 ^= 0x1;
/* 1464 */           j = paramArrayOfint[b2++]; paramInt2 ^= 0x2;
/*      */         
/*      */         case 1:
/* 1467 */           if (i < j) {
/* 1468 */             this.buffer[b3++] = i; i = this.list[b1++]; paramInt2 ^= 0x1; continue;
/* 1469 */           }  if (j < i) {
/* 1470 */             j = paramArrayOfint[b2++]; paramInt2 ^= 0x2; continue;
/*      */           } 
/* 1472 */           if (i == 1114112)
/* 1473 */             break;  i = this.list[b1++]; paramInt2 ^= 0x1;
/* 1474 */           j = paramArrayOfint[b2++]; paramInt2 ^= 0x2;
/*      */ 
/*      */         
/*      */         case 2:
/* 1478 */           if (j < i) {
/* 1479 */             this.buffer[b3++] = j; j = paramArrayOfint[b2++]; paramInt2 ^= 0x2; continue;
/* 1480 */           }  if (i < j) {
/* 1481 */             i = this.list[b1++]; paramInt2 ^= 0x1; continue;
/*      */           } 
/* 1483 */           if (i == 1114112)
/* 1484 */             break;  i = this.list[b1++]; paramInt2 ^= 0x1;
/* 1485 */           j = paramArrayOfint[b2++]; paramInt2 ^= 0x2;
/*      */       } 
/*      */ 
/*      */     
/*      */     } 
/* 1490 */     this.buffer[b3++] = 1114112;
/* 1491 */     this.len = b3;
/*      */     
/* 1493 */     int[] arrayOfInt = this.list;
/* 1494 */     this.list = this.buffer;
/* 1495 */     this.buffer = arrayOfInt;
/* 1496 */     this.pat = null;
/* 1497 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private UnicodeSet retain(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 1506 */     ensureBufferCapacity(this.len + paramInt1);
/* 1507 */     byte b1 = 0, b2 = 0, b3 = 0;
/* 1508 */     int i = this.list[b1++];
/* 1509 */     int j = paramArrayOfint[b2++];
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/* 1514 */       switch (paramInt2) {
/*      */         case 0:
/* 1516 */           if (i < j) {
/* 1517 */             i = this.list[b1++]; paramInt2 ^= 0x1; continue;
/* 1518 */           }  if (j < i) {
/* 1519 */             j = paramArrayOfint[b2++]; paramInt2 ^= 0x2; continue;
/*      */           } 
/* 1521 */           if (i == 1114112)
/* 1522 */             break;  this.buffer[b3++] = i; i = this.list[b1++]; paramInt2 ^= 0x1;
/* 1523 */           j = paramArrayOfint[b2++]; paramInt2 ^= 0x2;
/*      */ 
/*      */         
/*      */         case 3:
/* 1527 */           if (i < j) {
/* 1528 */             this.buffer[b3++] = i; i = this.list[b1++]; paramInt2 ^= 0x1; continue;
/* 1529 */           }  if (j < i) {
/* 1530 */             this.buffer[b3++] = j; j = paramArrayOfint[b2++]; paramInt2 ^= 0x2; continue;
/*      */           } 
/* 1532 */           if (i == 1114112)
/* 1533 */             break;  this.buffer[b3++] = i; i = this.list[b1++]; paramInt2 ^= 0x1;
/* 1534 */           j = paramArrayOfint[b2++]; paramInt2 ^= 0x2;
/*      */ 
/*      */         
/*      */         case 1:
/* 1538 */           if (i < j) {
/* 1539 */             i = this.list[b1++]; paramInt2 ^= 0x1; continue;
/* 1540 */           }  if (j < i) {
/* 1541 */             this.buffer[b3++] = j; j = paramArrayOfint[b2++]; paramInt2 ^= 0x2; continue;
/*      */           } 
/* 1543 */           if (i == 1114112)
/* 1544 */             break;  i = this.list[b1++]; paramInt2 ^= 0x1;
/* 1545 */           j = paramArrayOfint[b2++]; paramInt2 ^= 0x2;
/*      */ 
/*      */         
/*      */         case 2:
/* 1549 */           if (j < i) {
/* 1550 */             j = paramArrayOfint[b2++]; paramInt2 ^= 0x2; continue;
/* 1551 */           }  if (i < j) {
/* 1552 */             this.buffer[b3++] = i; i = this.list[b1++]; paramInt2 ^= 0x1; continue;
/*      */           } 
/* 1554 */           if (i == 1114112)
/* 1555 */             break;  i = this.list[b1++]; paramInt2 ^= 0x1;
/* 1556 */           j = paramArrayOfint[b2++]; paramInt2 ^= 0x2;
/*      */       } 
/*      */ 
/*      */     
/*      */     } 
/* 1561 */     this.buffer[b3++] = 1114112;
/* 1562 */     this.len = b3;
/*      */     
/* 1564 */     int[] arrayOfInt = this.list;
/* 1565 */     this.list = this.buffer;
/* 1566 */     this.buffer = arrayOfInt;
/* 1567 */     this.pat = null;
/* 1568 */     return this;
/*      */   }
/*      */   
/*      */   private static final int max(int paramInt1, int paramInt2) {
/* 1572 */     return (paramInt1 > paramInt2) ? paramInt1 : paramInt2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1584 */   static final VersionInfo NO_VERSION = VersionInfo.getInstance(0, 0, 0, 0); public static final int IGNORE_SPACE = 1;
/*      */   
/*      */   private static class VersionFilter implements Filter { VersionInfo version;
/*      */     
/*      */     VersionFilter(VersionInfo param1VersionInfo) {
/* 1589 */       this.version = param1VersionInfo;
/*      */     }
/*      */     public boolean contains(int param1Int) {
/* 1592 */       VersionInfo versionInfo = UCharacter.getAge(param1Int);
/*      */ 
/*      */       
/* 1595 */       return (versionInfo != UnicodeSet.NO_VERSION && versionInfo
/* 1596 */         .compareTo(this.version) <= 0);
/*      */     } }
/*      */ 
/*      */   
/*      */   private static synchronized UnicodeSet getInclusions(int paramInt) {
/* 1601 */     if (INCLUSIONS == null) {
/* 1602 */       INCLUSIONS = new UnicodeSet[9];
/*      */     }
/* 1604 */     if (INCLUSIONS[paramInt] == null) {
/* 1605 */       UnicodeSet unicodeSet = new UnicodeSet();
/* 1606 */       switch (paramInt) {
/*      */         case 2:
/* 1608 */           UCharacterProperty.getInstance().upropsvec_addPropertyStarts(unicodeSet);
/*      */           break;
/*      */         default:
/* 1611 */           throw new IllegalStateException("UnicodeSet.getInclusions(unknown src " + paramInt + ")");
/*      */       } 
/* 1613 */       INCLUSIONS[paramInt] = unicodeSet;
/*      */     } 
/* 1615 */     return INCLUSIONS[paramInt];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private UnicodeSet applyFilter(Filter paramFilter, int paramInt) {
/* 1636 */     clear();
/*      */     
/* 1638 */     int i = -1;
/* 1639 */     UnicodeSet unicodeSet = getInclusions(paramInt);
/* 1640 */     int j = unicodeSet.getRangeCount();
/*      */     
/* 1642 */     for (byte b = 0; b < j; b++) {
/*      */       
/* 1644 */       int k = unicodeSet.getRangeStart(b);
/* 1645 */       int m = unicodeSet.getRangeEnd(b);
/*      */ 
/*      */       
/* 1648 */       for (int n = k; n <= m; n++) {
/*      */ 
/*      */         
/* 1651 */         if (paramFilter.contains(n)) {
/* 1652 */           if (i < 0) {
/* 1653 */             i = n;
/*      */           }
/* 1655 */         } else if (i >= 0) {
/* 1656 */           add_unchecked(i, n - 1);
/* 1657 */           i = -1;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1661 */     if (i >= 0) {
/* 1662 */       add_unchecked(i, 1114111);
/*      */     }
/*      */     
/* 1665 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String mungeCharName(String paramString) {
/* 1675 */     StringBuffer stringBuffer = new StringBuffer();
/* 1676 */     for (int i = 0; i < paramString.length(); ) {
/* 1677 */       int j = UTF16.charAt(paramString, i);
/* 1678 */       i += UTF16.getCharCount(j);
/* 1679 */       if (UCharacterProperty.isRuleWhiteSpace(j)) {
/* 1680 */         if (stringBuffer.length() == 0 || stringBuffer
/* 1681 */           .charAt(stringBuffer.length() - 1) == ' ') {
/*      */           continue;
/*      */         }
/* 1684 */         j = 32;
/*      */       } 
/* 1686 */       UTF16.append(stringBuffer, j);
/*      */     } 
/* 1688 */     if (stringBuffer.length() != 0 && stringBuffer
/* 1689 */       .charAt(stringBuffer.length() - 1) == ' ') {
/* 1690 */       stringBuffer.setLength(stringBuffer.length() - 1);
/*      */     }
/* 1692 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public UnicodeSet applyPropertyAlias(String paramString1, String paramString2, SymbolTable paramSymbolTable) {
/* 1708 */     if (paramString2.length() > 0 && 
/* 1709 */       paramString1.equals("Age")) {
/*      */ 
/*      */ 
/*      */       
/* 1713 */       VersionInfo versionInfo = VersionInfo.getInstance(mungeCharName(paramString2));
/* 1714 */       applyFilter(new VersionFilter(versionInfo), 2);
/* 1715 */       return this;
/*      */     } 
/*      */     
/* 1718 */     throw new IllegalArgumentException("Unsupported property: " + paramString1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean resemblesPropertyPattern(RuleCharacterIterator paramRuleCharacterIterator, int paramInt) {
/* 1731 */     boolean bool = false;
/* 1732 */     paramInt &= 0xFFFFFFFD;
/* 1733 */     Object object = paramRuleCharacterIterator.getPos(null);
/* 1734 */     int i = paramRuleCharacterIterator.next(paramInt);
/* 1735 */     if (i == 91 || i == 92) {
/* 1736 */       int j = paramRuleCharacterIterator.next(paramInt & 0xFFFFFFFB);
/* 1737 */       bool = (i == 91) ? ((j == 58) ? true : false) : ((j == 78 || j == 112 || j == 80) ? true : false);
/*      */     } 
/*      */     
/* 1740 */     paramRuleCharacterIterator.setPos(object);
/* 1741 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private UnicodeSet applyPropertyPattern(String paramString, ParsePosition paramParsePosition, SymbolTable paramSymbolTable) {
/*      */     String str1, str2;
/* 1749 */     int i = paramParsePosition.getIndex();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1754 */     if (i + 5 > paramString.length()) {
/* 1755 */       return null;
/*      */     }
/*      */     
/* 1758 */     boolean bool1 = false;
/* 1759 */     boolean bool2 = false;
/* 1760 */     boolean bool3 = false;
/*      */ 
/*      */     
/* 1763 */     if (paramString.regionMatches(i, "[:", 0, 2)) {
/* 1764 */       bool1 = true;
/* 1765 */       i = Utility.skipWhitespace(paramString, i + 2);
/* 1766 */       if (i < paramString.length() && paramString.charAt(i) == '^') {
/* 1767 */         i++;
/* 1768 */         bool3 = true;
/*      */       } 
/* 1770 */     } else if (paramString.regionMatches(true, i, "\\p", 0, 2) || paramString
/* 1771 */       .regionMatches(i, "\\N", 0, 2)) {
/* 1772 */       char c = paramString.charAt(i + 1);
/* 1773 */       bool3 = (c == 'P') ? true : false;
/* 1774 */       bool2 = (c == 'N') ? true : false;
/* 1775 */       i = Utility.skipWhitespace(paramString, i + 2);
/* 1776 */       if (i == paramString.length() || paramString.charAt(i++) != '{')
/*      */       {
/* 1778 */         return null;
/*      */       }
/*      */     } else {
/*      */       
/* 1782 */       return null;
/*      */     } 
/*      */ 
/*      */     
/* 1786 */     int j = paramString.indexOf(bool1 ? ":]" : "}", i);
/* 1787 */     if (j < 0)
/*      */     {
/* 1789 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1795 */     int k = paramString.indexOf('=', i);
/*      */     
/* 1797 */     if (k >= 0 && k < j && !bool2) {
/*      */       
/* 1799 */       str1 = paramString.substring(i, k);
/* 1800 */       str2 = paramString.substring(k + 1, j);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1805 */       str1 = paramString.substring(i, j);
/* 1806 */       str2 = "";
/*      */ 
/*      */       
/* 1809 */       if (bool2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1815 */         str2 = str1;
/* 1816 */         str1 = "na";
/*      */       } 
/*      */     } 
/*      */     
/* 1820 */     applyPropertyAlias(str1, str2, paramSymbolTable);
/*      */     
/* 1822 */     if (bool3) {
/* 1823 */       complement();
/*      */     }
/*      */ 
/*      */     
/* 1827 */     paramParsePosition.setIndex(j + (bool1 ? 2 : 1));
/*      */     
/* 1829 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void applyPropertyPattern(RuleCharacterIterator paramRuleCharacterIterator, StringBuffer paramStringBuffer, SymbolTable paramSymbolTable) {
/* 1844 */     String str = paramRuleCharacterIterator.lookahead();
/* 1845 */     ParsePosition parsePosition = new ParsePosition(0);
/* 1846 */     applyPropertyPattern(str, parsePosition, paramSymbolTable);
/* 1847 */     if (parsePosition.getIndex() == 0) {
/* 1848 */       syntaxError(paramRuleCharacterIterator, "Invalid property pattern");
/*      */     }
/* 1850 */     paramRuleCharacterIterator.jumpahead(parsePosition.getIndex());
/* 1851 */     paramStringBuffer.append(str.substring(0, parsePosition.getIndex()));
/*      */   }
/*      */   
/*      */   private static interface Filter {
/*      */     boolean contains(int param1Int);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/normalizer/UnicodeSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */