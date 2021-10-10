/*      */ package sun.util.locale.provider;
/*      */ 
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.IOException;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.text.BreakIterator;
/*      */ import java.text.CharacterIterator;
/*      */ import java.text.StringCharacterIterator;
/*      */ import java.util.MissingResourceException;
/*      */ import sun.text.CompactByteArray;
/*      */ import sun.text.SupplementaryCharacterData;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class RuleBasedBreakIterator
/*      */   extends BreakIterator
/*      */ {
/*      */   protected static final byte IGNORE = -1;
/*      */   private static final short START_STATE = 1;
/*      */   private static final short STOP_STATE = 0;
/*  239 */   static final byte[] LABEL = new byte[] { 66, 73, 100, 97, 116, 97, 0 };
/*      */ 
/*      */ 
/*      */   
/*  243 */   static final int LABEL_LENGTH = LABEL.length;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final byte supportedVersion = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int HEADER_LENGTH = 36;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int BMP_INDICES_LENGTH = 512;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  263 */   private CompactByteArray charCategoryTable = null;
/*  264 */   private SupplementaryCharacterData supplementaryCharCategoryTable = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  269 */   private short[] stateTable = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  275 */   private short[] backwardsStateTable = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  281 */   private boolean[] endStates = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  287 */   private boolean[] lookaheadStates = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  293 */   private byte[] additionalData = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int numCategories;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  304 */   private CharacterIterator text = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long checksum;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int cachedLastKnownBreak;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void readTables(String paramString) throws IOException, MissingResourceException {
/*  375 */     byte[] arrayOfByte1 = readFile(paramString);
/*      */ 
/*      */     
/*  378 */     int i = getInt(arrayOfByte1, 0);
/*  379 */     int j = getInt(arrayOfByte1, 4);
/*  380 */     int k = getInt(arrayOfByte1, 8);
/*  381 */     int m = getInt(arrayOfByte1, 12);
/*  382 */     int n = getInt(arrayOfByte1, 16);
/*  383 */     int i1 = getInt(arrayOfByte1, 20);
/*  384 */     int i2 = getInt(arrayOfByte1, 24);
/*  385 */     this.checksum = getLong(arrayOfByte1, 28);
/*      */ 
/*      */     
/*  388 */     this.stateTable = new short[i];
/*  389 */     int i3 = 36; byte b1;
/*  390 */     for (b1 = 0; b1 < i; b1++, i3 += 2) {
/*  391 */       this.stateTable[b1] = getShort(arrayOfByte1, i3);
/*      */     }
/*      */ 
/*      */     
/*  395 */     this.backwardsStateTable = new short[j];
/*  396 */     for (b1 = 0; b1 < j; b1++, i3 += 2) {
/*  397 */       this.backwardsStateTable[b1] = getShort(arrayOfByte1, i3);
/*      */     }
/*      */ 
/*      */     
/*  401 */     this.endStates = new boolean[k];
/*  402 */     for (b1 = 0; b1 < k; b1++, i3++) {
/*  403 */       this.endStates[b1] = (arrayOfByte1[i3] == 1);
/*      */     }
/*      */ 
/*      */     
/*  407 */     this.lookaheadStates = new boolean[m];
/*  408 */     for (b1 = 0; b1 < m; b1++, i3++) {
/*  409 */       this.lookaheadStates[b1] = (arrayOfByte1[i3] == 1);
/*      */     }
/*      */ 
/*      */     
/*  413 */     short[] arrayOfShort = new short[512];
/*  414 */     for (byte b2 = 0; b2 < 'Ȁ'; b2++, i3 += 2) {
/*  415 */       arrayOfShort[b2] = getShort(arrayOfByte1, i3);
/*      */     }
/*  417 */     byte[] arrayOfByte2 = new byte[n];
/*  418 */     System.arraycopy(arrayOfByte1, i3, arrayOfByte2, 0, n);
/*  419 */     i3 += n;
/*  420 */     this.charCategoryTable = new CompactByteArray(arrayOfShort, arrayOfByte2);
/*      */ 
/*      */     
/*  423 */     int[] arrayOfInt = new int[i1];
/*  424 */     for (byte b3 = 0; b3 < i1; b3++, i3 += 4) {
/*  425 */       arrayOfInt[b3] = getInt(arrayOfByte1, i3);
/*      */     }
/*  427 */     this.supplementaryCharCategoryTable = new SupplementaryCharacterData(arrayOfInt);
/*      */ 
/*      */     
/*  430 */     if (i2 > 0) {
/*  431 */       this.additionalData = new byte[i2];
/*  432 */       System.arraycopy(arrayOfByte1, i3, this.additionalData, 0, i2);
/*      */     } 
/*      */ 
/*      */     
/*  436 */     this.numCategories = this.stateTable.length / this.endStates.length;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected byte[] readFile(final String datafile) throws IOException, MissingResourceException {
/*      */     BufferedInputStream bufferedInputStream;
/*      */     try {
/*  444 */       bufferedInputStream = AccessController.<BufferedInputStream>doPrivileged(new PrivilegedExceptionAction<BufferedInputStream>()
/*      */           {
/*      */             public BufferedInputStream run() throws Exception
/*      */             {
/*  448 */               return new BufferedInputStream(getClass().getResourceAsStream("/sun/text/resources/" + datafile));
/*      */             }
/*      */           });
/*      */     
/*      */     }
/*  453 */     catch (PrivilegedActionException privilegedActionException) {
/*  454 */       throw new InternalError(privilegedActionException.toString(), privilegedActionException);
/*      */     } 
/*      */     
/*  457 */     byte b1 = 0;
/*      */ 
/*      */     
/*  460 */     int i = LABEL_LENGTH + 5;
/*  461 */     byte[] arrayOfByte = new byte[i];
/*  462 */     if (bufferedInputStream.read(arrayOfByte) != i) {
/*  463 */       throw new MissingResourceException("Wrong header length", datafile, "");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  468 */     for (byte b2 = 0; b2 < LABEL_LENGTH; b2++, b1++) {
/*  469 */       if (arrayOfByte[b1] != LABEL[b1]) {
/*  470 */         throw new MissingResourceException("Wrong magic number", datafile, "");
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  476 */     if (arrayOfByte[b1] != 1) {
/*  477 */       throw new MissingResourceException("Unsupported version(" + arrayOfByte[b1] + ")", datafile, "");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  482 */     i = getInt(arrayOfByte, ++b1);
/*  483 */     arrayOfByte = new byte[i];
/*  484 */     if (bufferedInputStream.read(arrayOfByte) != i) {
/*  485 */       throw new MissingResourceException("Wrong data length", datafile, "");
/*      */     }
/*      */ 
/*      */     
/*  489 */     bufferedInputStream.close();
/*      */     
/*  491 */     return arrayOfByte;
/*      */   }
/*      */   
/*      */   byte[] getAdditionalData() {
/*  495 */     return this.additionalData;
/*      */   }
/*      */   
/*      */   void setAdditionalData(byte[] paramArrayOfbyte) {
/*  499 */     this.additionalData = paramArrayOfbyte;
/*      */   }
/*      */ 
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
/*  512 */     RuleBasedBreakIterator ruleBasedBreakIterator = (RuleBasedBreakIterator)super.clone();
/*  513 */     if (this.text != null) {
/*  514 */       ruleBasedBreakIterator.text = (CharacterIterator)this.text.clone();
/*      */     }
/*  516 */     return ruleBasedBreakIterator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object paramObject) {
/*      */     try {
/*  526 */       if (paramObject == null) {
/*  527 */         return false;
/*      */       }
/*      */       
/*  530 */       RuleBasedBreakIterator ruleBasedBreakIterator = (RuleBasedBreakIterator)paramObject;
/*  531 */       if (this.checksum != ruleBasedBreakIterator.checksum) {
/*  532 */         return false;
/*      */       }
/*  534 */       if (this.text == null) {
/*  535 */         return (ruleBasedBreakIterator.text == null);
/*      */       }
/*  537 */       return this.text.equals(ruleBasedBreakIterator.text);
/*      */     
/*      */     }
/*  540 */     catch (ClassCastException classCastException) {
/*  541 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  550 */     StringBuilder stringBuilder = new StringBuilder();
/*  551 */     stringBuilder.append('[');
/*  552 */     stringBuilder.append("checksum=0x");
/*  553 */     stringBuilder.append(Long.toHexString(this.checksum));
/*  554 */     stringBuilder.append(']');
/*  555 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  564 */     return (int)this.checksum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int first() {
/*  578 */     CharacterIterator characterIterator = getText();
/*      */     
/*  580 */     characterIterator.first();
/*  581 */     return characterIterator.getIndex();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int last() {
/*  591 */     CharacterIterator characterIterator = getText();
/*      */ 
/*      */ 
/*      */     
/*  595 */     characterIterator.setIndex(characterIterator.getEndIndex());
/*  596 */     return characterIterator.getIndex();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int next(int paramInt) {
/*  610 */     int i = current();
/*  611 */     while (paramInt > 0) {
/*  612 */       i = handleNext();
/*  613 */       paramInt--;
/*      */     } 
/*  615 */     while (paramInt < 0) {
/*  616 */       i = previous();
/*  617 */       paramInt++;
/*      */     } 
/*  619 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int next() {
/*  628 */     return handleNext();
/*      */   }
/*      */   RuleBasedBreakIterator(String paramString) throws IOException, MissingResourceException {
/*  631 */     this.cachedLastKnownBreak = -1;
/*      */     readTables(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int previous() {
/*  640 */     CharacterIterator characterIterator = getText();
/*  641 */     if (current() == characterIterator.getBeginIndex()) {
/*  642 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  650 */     int i = current();
/*  651 */     int j = this.cachedLastKnownBreak;
/*  652 */     if (j >= i || j <= -1) {
/*  653 */       getPrevious();
/*  654 */       j = handlePrevious();
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  659 */       characterIterator.setIndex(j);
/*      */     } 
/*  661 */     int k = j;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  666 */     while (k != -1 && k < i) {
/*  667 */       j = k;
/*  668 */       k = handleNext();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  673 */     characterIterator.setIndex(j);
/*  674 */     this.cachedLastKnownBreak = j;
/*  675 */     return j;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getPrevious() {
/*  682 */     char c = this.text.previous();
/*  683 */     if (Character.isLowSurrogate(c) && this.text
/*  684 */       .getIndex() > this.text.getBeginIndex()) {
/*  685 */       char c1 = this.text.previous();
/*  686 */       if (Character.isHighSurrogate(c1)) {
/*  687 */         return Character.toCodePoint(c1, c);
/*      */       }
/*  689 */       this.text.next();
/*      */     } 
/*      */     
/*  692 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getCurrent() {
/*  699 */     char c = this.text.current();
/*  700 */     if (Character.isHighSurrogate(c) && this.text
/*  701 */       .getIndex() < this.text.getEndIndex()) {
/*  702 */       char c1 = this.text.next();
/*  703 */       this.text.previous();
/*  704 */       if (Character.isLowSurrogate(c1)) {
/*  705 */         return Character.toCodePoint(c, c1);
/*      */       }
/*      */     } 
/*  708 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getCurrentCodePointCount() {
/*  715 */     char c = this.text.current();
/*  716 */     if (Character.isHighSurrogate(c) && this.text
/*  717 */       .getIndex() < this.text.getEndIndex()) {
/*  718 */       char c1 = this.text.next();
/*  719 */       this.text.previous();
/*  720 */       if (Character.isLowSurrogate(c1)) {
/*  721 */         return 2;
/*      */       }
/*      */     } 
/*  724 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getNext() {
/*  731 */     int i = this.text.getIndex();
/*  732 */     int j = this.text.getEndIndex();
/*  733 */     if (i == j || (i += 
/*  734 */       getCurrentCodePointCount()) >= j) {
/*  735 */       return 65535;
/*      */     }
/*  737 */     this.text.setIndex(i);
/*  738 */     return getCurrent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getNextIndex() {
/*  745 */     int i = this.text.getIndex() + getCurrentCodePointCount();
/*  746 */     int j = this.text.getEndIndex();
/*  747 */     if (i > j) {
/*  748 */       return j;
/*      */     }
/*  750 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final void checkOffset(int paramInt, CharacterIterator paramCharacterIterator) {
/*  758 */     if (paramInt < paramCharacterIterator.getBeginIndex() || paramInt > paramCharacterIterator.getEndIndex()) {
/*  759 */       throw new IllegalArgumentException("offset out of bounds");
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
/*      */   public int following(int paramInt) {
/*  772 */     CharacterIterator characterIterator = getText();
/*  773 */     checkOffset(paramInt, characterIterator);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  778 */     characterIterator.setIndex(paramInt);
/*  779 */     if (paramInt == characterIterator.getBeginIndex()) {
/*  780 */       this.cachedLastKnownBreak = handleNext();
/*  781 */       return this.cachedLastKnownBreak;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  791 */     int i = this.cachedLastKnownBreak;
/*  792 */     if (i >= paramInt || i <= -1) {
/*  793 */       i = handlePrevious();
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  798 */       characterIterator.setIndex(i);
/*      */     } 
/*  800 */     while (i != -1 && i <= paramInt) {
/*  801 */       i = handleNext();
/*      */     }
/*  803 */     this.cachedLastKnownBreak = i;
/*  804 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int preceding(int paramInt) {
/*  818 */     CharacterIterator characterIterator = getText();
/*  819 */     checkOffset(paramInt, characterIterator);
/*  820 */     characterIterator.setIndex(paramInt);
/*  821 */     return previous();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBoundary(int paramInt) {
/*  833 */     CharacterIterator characterIterator = getText();
/*  834 */     checkOffset(paramInt, characterIterator);
/*  835 */     if (paramInt == characterIterator.getBeginIndex()) {
/*  836 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  843 */     return (following(paramInt - 1) == paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int current() {
/*  853 */     return getText().getIndex();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharacterIterator getText() {
/*  868 */     if (this.text == null) {
/*  869 */       this.text = new StringCharacterIterator("");
/*      */     }
/*  871 */     return this.text;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setText(CharacterIterator paramCharacterIterator) {
/*      */     boolean bool;
/*  886 */     int i = paramCharacterIterator.getEndIndex();
/*      */     
/*      */     try {
/*  889 */       paramCharacterIterator.setIndex(i);
/*  890 */       bool = (paramCharacterIterator.getIndex() == i) ? true : false;
/*      */     }
/*  892 */     catch (IllegalArgumentException illegalArgumentException) {
/*  893 */       bool = false;
/*      */     } 
/*      */     
/*  896 */     if (bool) {
/*  897 */       this.text = paramCharacterIterator;
/*      */     } else {
/*      */       
/*  900 */       this.text = new SafeCharIterator(paramCharacterIterator);
/*      */     } 
/*  902 */     this.text.first();
/*      */     
/*  904 */     this.cachedLastKnownBreak = -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int handleNext() {
/*  921 */     CharacterIterator characterIterator = getText();
/*  922 */     if (characterIterator.getIndex() == characterIterator.getEndIndex()) {
/*  923 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*  927 */     int i = getNextIndex();
/*  928 */     int j = 0;
/*      */ 
/*      */     
/*  931 */     int k = 1;
/*      */     
/*  933 */     int m = getCurrent();
/*      */ 
/*      */     
/*  936 */     while (m != 65535 && k) {
/*      */ 
/*      */ 
/*      */       
/*  940 */       int n = lookupCategory(m);
/*      */ 
/*      */ 
/*      */       
/*  944 */       if (n != -1) {
/*  945 */         k = lookupState(k, n);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  952 */       if (this.lookaheadStates[k]) {
/*  953 */         if (this.endStates[k]) {
/*  954 */           i = j;
/*      */         } else {
/*      */           
/*  957 */           j = getNextIndex();
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*  964 */       else if (this.endStates[k]) {
/*  965 */         i = getNextIndex();
/*      */       } 
/*      */ 
/*      */       
/*  969 */       m = getNext();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  976 */     if (m == 65535 && j == characterIterator.getEndIndex()) {
/*  977 */       i = j;
/*      */     }
/*      */     
/*  980 */     characterIterator.setIndex(i);
/*  981 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int handlePrevious() {
/*  992 */     CharacterIterator characterIterator = getText();
/*  993 */     int i = 1;
/*  994 */     int j = 0;
/*  995 */     int k = 0;
/*  996 */     int m = getCurrent();
/*      */ 
/*      */     
/*  999 */     while (m != 65535 && i) {
/*      */ 
/*      */ 
/*      */       
/* 1003 */       k = j;
/* 1004 */       j = lookupCategory(m);
/*      */ 
/*      */ 
/*      */       
/* 1008 */       if (j != -1) {
/* 1009 */         i = lookupBackwardState(i, j);
/*      */       }
/*      */ 
/*      */       
/* 1013 */       m = getPrevious();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1021 */     if (m != 65535) {
/* 1022 */       if (k != -1) {
/* 1023 */         getNext();
/* 1024 */         getNext();
/*      */       } else {
/*      */         
/* 1027 */         getNext();
/*      */       } 
/*      */     }
/* 1030 */     return characterIterator.getIndex();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int lookupCategory(int paramInt) {
/* 1038 */     if (paramInt < 65536) {
/* 1039 */       return this.charCategoryTable.elementAt((char)paramInt);
/*      */     }
/* 1041 */     return this.supplementaryCharCategoryTable.getValue(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int lookupState(int paramInt1, int paramInt2) {
/* 1050 */     return this.stateTable[paramInt1 * this.numCategories + paramInt2];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int lookupBackwardState(int paramInt1, int paramInt2) {
/* 1058 */     return this.backwardsStateTable[paramInt1 * this.numCategories + paramInt2];
/*      */   }
/*      */   
/*      */   static long getLong(byte[] paramArrayOfbyte, int paramInt) {
/* 1062 */     long l = (paramArrayOfbyte[paramInt] & 0xFF);
/* 1063 */     for (byte b = 1; b < 8; b++) {
/* 1064 */       l = l << 8L | (paramArrayOfbyte[paramInt + b] & 0xFF);
/*      */     }
/* 1066 */     return l;
/*      */   }
/*      */   
/*      */   static int getInt(byte[] paramArrayOfbyte, int paramInt) {
/* 1070 */     int i = paramArrayOfbyte[paramInt] & 0xFF;
/* 1071 */     for (byte b = 1; b < 4; b++) {
/* 1072 */       i = i << 8 | paramArrayOfbyte[paramInt + b] & 0xFF;
/*      */     }
/* 1074 */     return i;
/*      */   }
/*      */   
/*      */   static short getShort(byte[] paramArrayOfbyte, int paramInt) {
/* 1078 */     short s = (short)(paramArrayOfbyte[paramInt] & 0xFF);
/* 1079 */     s = (short)(s << 8 | paramArrayOfbyte[paramInt + 1] & 0xFF);
/* 1080 */     return s;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class SafeCharIterator
/*      */     implements CharacterIterator, Cloneable
/*      */   {
/*      */     private CharacterIterator base;
/*      */ 
/*      */     
/*      */     private int rangeStart;
/*      */ 
/*      */     
/*      */     private int rangeLimit;
/*      */ 
/*      */     
/*      */     private int currentIndex;
/*      */ 
/*      */ 
/*      */     
/*      */     SafeCharIterator(CharacterIterator param1CharacterIterator) {
/* 1102 */       this.base = param1CharacterIterator;
/* 1103 */       this.rangeStart = param1CharacterIterator.getBeginIndex();
/* 1104 */       this.rangeLimit = param1CharacterIterator.getEndIndex();
/* 1105 */       this.currentIndex = param1CharacterIterator.getIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public char first() {
/* 1110 */       return setIndex(this.rangeStart);
/*      */     }
/*      */ 
/*      */     
/*      */     public char last() {
/* 1115 */       return setIndex(this.rangeLimit - 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public char current() {
/* 1120 */       if (this.currentIndex < this.rangeStart || this.currentIndex >= this.rangeLimit) {
/* 1121 */         return Character.MAX_VALUE;
/*      */       }
/*      */       
/* 1124 */       return this.base.setIndex(this.currentIndex);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public char next() {
/* 1131 */       this.currentIndex++;
/* 1132 */       if (this.currentIndex >= this.rangeLimit) {
/* 1133 */         this.currentIndex = this.rangeLimit;
/* 1134 */         return Character.MAX_VALUE;
/*      */       } 
/*      */       
/* 1137 */       return this.base.setIndex(this.currentIndex);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public char previous() {
/* 1144 */       this.currentIndex--;
/* 1145 */       if (this.currentIndex < this.rangeStart) {
/* 1146 */         this.currentIndex = this.rangeStart;
/* 1147 */         return Character.MAX_VALUE;
/*      */       } 
/*      */       
/* 1150 */       return this.base.setIndex(this.currentIndex);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public char setIndex(int param1Int) {
/* 1157 */       if (param1Int < this.rangeStart || param1Int > this.rangeLimit) {
/* 1158 */         throw new IllegalArgumentException("Invalid position");
/*      */       }
/* 1160 */       this.currentIndex = param1Int;
/* 1161 */       return current();
/*      */     }
/*      */ 
/*      */     
/*      */     public int getBeginIndex() {
/* 1166 */       return this.rangeStart;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getEndIndex() {
/* 1171 */       return this.rangeLimit;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getIndex() {
/* 1176 */       return this.currentIndex;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Object clone() {
/* 1182 */       SafeCharIterator safeCharIterator = null;
/*      */       try {
/* 1184 */         safeCharIterator = (SafeCharIterator)super.clone();
/*      */       }
/* 1186 */       catch (CloneNotSupportedException cloneNotSupportedException) {
/* 1187 */         throw new Error("Clone not supported: " + cloneNotSupportedException);
/*      */       } 
/*      */       
/* 1190 */       CharacterIterator characterIterator = (CharacterIterator)this.base.clone();
/* 1191 */       safeCharIterator.base = characterIterator;
/* 1192 */       return safeCharIterator;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/provider/RuleBasedBreakIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */