/*      */ package sun.text.normalizer;
/*      */ 
/*      */ import java.text.CharacterIterator;
/*      */ import java.text.Normalizer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class NormalizerBase
/*      */   implements Cloneable
/*      */ {
/*  149 */   private char[] buffer = new char[100];
/*  150 */   private int bufferStart = 0;
/*  151 */   private int bufferPos = 0;
/*  152 */   private int bufferLimit = 0;
/*      */   
/*      */   private UCharacterIterator text;
/*      */   
/*  156 */   private Mode mode = NFC;
/*  157 */   private int options = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   private int currentIndex;
/*      */ 
/*      */ 
/*      */   
/*      */   private int nextIndex;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int UNICODE_3_2 = 32;
/*      */ 
/*      */   
/*      */   public static final int DONE = -1;
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Mode
/*      */   {
/*      */     private int modeValue;
/*      */ 
/*      */ 
/*      */     
/*      */     private Mode(int param1Int) {
/*  183 */       this.modeValue = param1Int;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected int normalize(char[] param1ArrayOfchar1, int param1Int1, int param1Int2, char[] param1ArrayOfchar2, int param1Int3, int param1Int4, UnicodeSet param1UnicodeSet) {
/*  193 */       int i = param1Int2 - param1Int1;
/*  194 */       int j = param1Int4 - param1Int3;
/*  195 */       if (i > j) {
/*  196 */         return i;
/*      */       }
/*  198 */       System.arraycopy(param1ArrayOfchar1, param1Int1, param1ArrayOfchar2, param1Int3, i);
/*  199 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected int normalize(char[] param1ArrayOfchar1, int param1Int1, int param1Int2, char[] param1ArrayOfchar2, int param1Int3, int param1Int4, int param1Int5) {
/*  209 */       return normalize(param1ArrayOfchar1, param1Int1, param1Int2, param1ArrayOfchar2, param1Int3, param1Int4, 
/*      */           
/*  211 */           NormalizerImpl.getNX(param1Int5));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected String normalize(String param1String, int param1Int) {
/*  220 */       return param1String;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected int getMinC() {
/*  228 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected int getMask() {
/*  236 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected NormalizerBase.IsPrevBoundary getPrevBoundary() {
/*  244 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected NormalizerBase.IsNextBoundary getNextBoundary() {
/*  252 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected NormalizerBase.QuickCheckResult quickCheck(char[] param1ArrayOfchar, int param1Int1, int param1Int2, boolean param1Boolean, UnicodeSet param1UnicodeSet) {
/*  261 */       if (param1Boolean) {
/*  262 */         return NormalizerBase.MAYBE;
/*      */       }
/*  264 */       return NormalizerBase.NO;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean isNFSkippable(int param1Int) {
/*  272 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  280 */   public static final Mode NONE = new Mode(1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  286 */   public static final Mode NFD = new NFDMode(2);
/*      */   
/*      */   private static final class NFDMode extends Mode {
/*      */     private NFDMode(int param1Int) {
/*  290 */       super(param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected int normalize(char[] param1ArrayOfchar1, int param1Int1, int param1Int2, char[] param1ArrayOfchar2, int param1Int3, int param1Int4, UnicodeSet param1UnicodeSet) {
/*  296 */       int[] arrayOfInt = new int[1];
/*  297 */       return NormalizerImpl.decompose(param1ArrayOfchar1, param1Int1, param1Int2, param1ArrayOfchar2, param1Int3, param1Int4, false, arrayOfInt, param1UnicodeSet);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected String normalize(String param1String, int param1Int) {
/*  303 */       return NormalizerBase.decompose(param1String, false, param1Int);
/*      */     }
/*      */     
/*      */     protected int getMinC() {
/*  307 */       return 768;
/*      */     }
/*      */     
/*      */     protected NormalizerBase.IsPrevBoundary getPrevBoundary() {
/*  311 */       return new NormalizerBase.IsPrevNFDSafe();
/*      */     }
/*      */     
/*      */     protected NormalizerBase.IsNextBoundary getNextBoundary() {
/*  315 */       return new NormalizerBase.IsNextNFDSafe();
/*      */     }
/*      */     
/*      */     protected int getMask() {
/*  319 */       return 65284;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected NormalizerBase.QuickCheckResult quickCheck(char[] param1ArrayOfchar, int param1Int1, int param1Int2, boolean param1Boolean, UnicodeSet param1UnicodeSet) {
/*  325 */       return NormalizerImpl.quickCheck(param1ArrayOfchar, param1Int1, param1Int2, 
/*      */           
/*  327 */           NormalizerImpl.getFromIndexesArr(8), 4, 0, param1Boolean, param1UnicodeSet);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean isNFSkippable(int param1Int) {
/*  338 */       return NormalizerImpl.isNFSkippable(param1Int, this, 65284L);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  348 */   public static final Mode NFKD = new NFKDMode(3);
/*      */   
/*      */   private static final class NFKDMode extends Mode {
/*      */     private NFKDMode(int param1Int) {
/*  352 */       super(param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected int normalize(char[] param1ArrayOfchar1, int param1Int1, int param1Int2, char[] param1ArrayOfchar2, int param1Int3, int param1Int4, UnicodeSet param1UnicodeSet) {
/*  358 */       int[] arrayOfInt = new int[1];
/*  359 */       return NormalizerImpl.decompose(param1ArrayOfchar1, param1Int1, param1Int2, param1ArrayOfchar2, param1Int3, param1Int4, true, arrayOfInt, param1UnicodeSet);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected String normalize(String param1String, int param1Int) {
/*  365 */       return NormalizerBase.decompose(param1String, true, param1Int);
/*      */     }
/*      */     
/*      */     protected int getMinC() {
/*  369 */       return 768;
/*      */     }
/*      */     
/*      */     protected NormalizerBase.IsPrevBoundary getPrevBoundary() {
/*  373 */       return new NormalizerBase.IsPrevNFDSafe();
/*      */     }
/*      */     
/*      */     protected NormalizerBase.IsNextBoundary getNextBoundary() {
/*  377 */       return new NormalizerBase.IsNextNFDSafe();
/*      */     }
/*      */     
/*      */     protected int getMask() {
/*  381 */       return 65288;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected NormalizerBase.QuickCheckResult quickCheck(char[] param1ArrayOfchar, int param1Int1, int param1Int2, boolean param1Boolean, UnicodeSet param1UnicodeSet) {
/*  387 */       return NormalizerImpl.quickCheck(param1ArrayOfchar, param1Int1, param1Int2, 
/*      */           
/*  389 */           NormalizerImpl.getFromIndexesArr(9), 8, 4096, param1Boolean, param1UnicodeSet);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean isNFSkippable(int param1Int) {
/*  400 */       return NormalizerImpl.isNFSkippable(param1Int, this, 65288L);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  410 */   public static final Mode NFC = new NFCMode(4);
/*      */   
/*      */   private static final class NFCMode extends Mode {
/*      */     private NFCMode(int param1Int) {
/*  414 */       super(param1Int);
/*      */     }
/*      */ 
/*      */     
/*      */     protected int normalize(char[] param1ArrayOfchar1, int param1Int1, int param1Int2, char[] param1ArrayOfchar2, int param1Int3, int param1Int4, UnicodeSet param1UnicodeSet) {
/*  419 */       return NormalizerImpl.compose(param1ArrayOfchar1, param1Int1, param1Int2, param1ArrayOfchar2, param1Int3, param1Int4, 0, param1UnicodeSet);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected String normalize(String param1String, int param1Int) {
/*  425 */       return NormalizerBase.compose(param1String, false, param1Int);
/*      */     }
/*      */     
/*      */     protected int getMinC() {
/*  429 */       return NormalizerImpl.getFromIndexesArr(6);
/*      */     }
/*      */ 
/*      */     
/*      */     protected NormalizerBase.IsPrevBoundary getPrevBoundary() {
/*  434 */       return new NormalizerBase.IsPrevTrueStarter();
/*      */     }
/*      */     protected NormalizerBase.IsNextBoundary getNextBoundary() {
/*  437 */       return new NormalizerBase.IsNextTrueStarter();
/*      */     }
/*      */     protected int getMask() {
/*  440 */       return 65297;
/*      */     }
/*      */ 
/*      */     
/*      */     protected NormalizerBase.QuickCheckResult quickCheck(char[] param1ArrayOfchar, int param1Int1, int param1Int2, boolean param1Boolean, UnicodeSet param1UnicodeSet) {
/*  445 */       return NormalizerImpl.quickCheck(param1ArrayOfchar, param1Int1, param1Int2, 
/*      */           
/*  447 */           NormalizerImpl.getFromIndexesArr(6), 17, 0, param1Boolean, param1UnicodeSet);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean isNFSkippable(int param1Int) {
/*  457 */       return NormalizerImpl.isNFSkippable(param1Int, this, 65473L);
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
/*  469 */   public static final Mode NFKC = new NFKCMode(5);
/*      */   
/*      */   private static final class NFKCMode extends Mode {
/*      */     private NFKCMode(int param1Int) {
/*  473 */       super(param1Int);
/*      */     }
/*      */ 
/*      */     
/*      */     protected int normalize(char[] param1ArrayOfchar1, int param1Int1, int param1Int2, char[] param1ArrayOfchar2, int param1Int3, int param1Int4, UnicodeSet param1UnicodeSet) {
/*  478 */       return NormalizerImpl.compose(param1ArrayOfchar1, param1Int1, param1Int2, param1ArrayOfchar2, param1Int3, param1Int4, 4096, param1UnicodeSet);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected String normalize(String param1String, int param1Int) {
/*  484 */       return NormalizerBase.compose(param1String, true, param1Int);
/*      */     }
/*      */     protected int getMinC() {
/*  487 */       return NormalizerImpl.getFromIndexesArr(7);
/*      */     }
/*      */ 
/*      */     
/*      */     protected NormalizerBase.IsPrevBoundary getPrevBoundary() {
/*  492 */       return new NormalizerBase.IsPrevTrueStarter();
/*      */     }
/*      */     protected NormalizerBase.IsNextBoundary getNextBoundary() {
/*  495 */       return new NormalizerBase.IsNextTrueStarter();
/*      */     }
/*      */     protected int getMask() {
/*  498 */       return 65314;
/*      */     }
/*      */ 
/*      */     
/*      */     protected NormalizerBase.QuickCheckResult quickCheck(char[] param1ArrayOfchar, int param1Int1, int param1Int2, boolean param1Boolean, UnicodeSet param1UnicodeSet) {
/*  503 */       return NormalizerImpl.quickCheck(param1ArrayOfchar, param1Int1, param1Int2, 
/*      */           
/*  505 */           NormalizerImpl.getFromIndexesArr(7), 34, 4096, param1Boolean, param1UnicodeSet);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean isNFSkippable(int param1Int) {
/*  515 */       return NormalizerImpl.isNFSkippable(param1Int, this, 65474L);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class QuickCheckResult
/*      */   {
/*      */     private int resultValue;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private QuickCheckResult(int param1Int) {
/*  531 */       this.resultValue = param1Int;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  538 */   public static final QuickCheckResult NO = new QuickCheckResult(0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  544 */   public static final QuickCheckResult YES = new QuickCheckResult(1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  551 */   public static final QuickCheckResult MAYBE = new QuickCheckResult(2);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MAX_BUF_SIZE_COMPOSE = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MAX_BUF_SIZE_DECOMPOSE = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int UNICODE_3_2_0_ORIGINAL = 262432;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int UNICODE_LATEST = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NormalizerBase(String paramString, Mode paramMode, int paramInt) {
/*  576 */     this.text = UCharacterIterator.getInstance(paramString);
/*  577 */     this.mode = paramMode;
/*  578 */     this.options = paramInt;
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
/*      */   public NormalizerBase(CharacterIterator paramCharacterIterator, Mode paramMode) {
/*  591 */     this(paramCharacterIterator, paramMode, 0);
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
/*      */   public NormalizerBase(CharacterIterator paramCharacterIterator, Mode paramMode, int paramInt) {
/*  610 */     this.text = UCharacterIterator.getInstance((CharacterIterator)paramCharacterIterator
/*  611 */         .clone());
/*      */     
/*  613 */     this.mode = paramMode;
/*  614 */     this.options = paramInt;
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
/*      */   public Object clone() {
/*      */     try {
/*  629 */       NormalizerBase normalizerBase = (NormalizerBase)super.clone();
/*  630 */       normalizerBase.text = (UCharacterIterator)this.text.clone();
/*      */       
/*  632 */       if (this.buffer != null) {
/*  633 */         normalizerBase.buffer = new char[this.buffer.length];
/*  634 */         System.arraycopy(this.buffer, 0, normalizerBase.buffer, 0, this.buffer.length);
/*      */       } 
/*  636 */       return normalizerBase;
/*      */     }
/*  638 */     catch (CloneNotSupportedException cloneNotSupportedException) {
/*  639 */       throw new InternalError(cloneNotSupportedException.toString(), cloneNotSupportedException);
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
/*      */   public static String compose(String paramString, boolean paramBoolean, int paramInt) {
/*      */     char[] arrayOfChar1, arrayOfChar2;
/*  661 */     if (paramInt == 262432) {
/*  662 */       String str = NormalizerImpl.convert(paramString);
/*  663 */       arrayOfChar1 = new char[str.length() * 2];
/*  664 */       arrayOfChar2 = str.toCharArray();
/*      */     } else {
/*  666 */       arrayOfChar1 = new char[paramString.length() * 2];
/*  667 */       arrayOfChar2 = paramString.toCharArray();
/*      */     } 
/*  669 */     int i = 0;
/*      */     
/*  671 */     UnicodeSet unicodeSet = NormalizerImpl.getNX(paramInt);
/*      */ 
/*      */     
/*  674 */     paramInt &= 0xFFFFCF00;
/*      */     
/*  676 */     if (paramBoolean) {
/*  677 */       paramInt |= 0x1000;
/*      */     }
/*      */     
/*      */     while (true) {
/*  681 */       i = NormalizerImpl.compose(arrayOfChar2, 0, arrayOfChar2.length, arrayOfChar1, 0, arrayOfChar1.length, paramInt, unicodeSet);
/*      */ 
/*      */       
/*  684 */       if (i <= arrayOfChar1.length) {
/*  685 */         return new String(arrayOfChar1, 0, i);
/*      */       }
/*  687 */       arrayOfChar1 = new char[i];
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
/*      */   public static String decompose(String paramString, boolean paramBoolean) {
/*  706 */     return decompose(paramString, paramBoolean, 0);
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
/*      */   public static String decompose(String paramString, boolean paramBoolean, int paramInt) {
/*  722 */     int[] arrayOfInt = new int[1];
/*  723 */     int i = 0;
/*  724 */     UnicodeSet unicodeSet = NormalizerImpl.getNX(paramInt);
/*      */ 
/*      */     
/*  727 */     if (paramInt == 262432) {
/*  728 */       String str = NormalizerImpl.convert(paramString);
/*  729 */       char[] arrayOfChar1 = new char[str.length() * 3];
/*      */       
/*      */       while (true) {
/*  732 */         i = NormalizerImpl.decompose(str.toCharArray(), 0, str.length(), arrayOfChar1, 0, arrayOfChar1.length, paramBoolean, arrayOfInt, unicodeSet);
/*      */ 
/*      */         
/*  735 */         if (i <= arrayOfChar1.length) {
/*  736 */           return new String(arrayOfChar1, 0, i);
/*      */         }
/*  738 */         arrayOfChar1 = new char[i];
/*      */       } 
/*      */     } 
/*      */     
/*  742 */     char[] arrayOfChar = new char[paramString.length() * 3];
/*      */     
/*      */     while (true) {
/*  745 */       i = NormalizerImpl.decompose(paramString.toCharArray(), 0, paramString.length(), arrayOfChar, 0, arrayOfChar.length, paramBoolean, arrayOfInt, unicodeSet);
/*      */ 
/*      */       
/*  748 */       if (i <= arrayOfChar.length) {
/*  749 */         return new String(arrayOfChar, 0, i);
/*      */       }
/*  751 */       arrayOfChar = new char[i];
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
/*      */   public static int normalize(char[] paramArrayOfchar1, int paramInt1, int paramInt2, char[] paramArrayOfchar2, int paramInt3, int paramInt4, Mode paramMode, int paramInt5) {
/*  780 */     int i = paramMode.normalize(paramArrayOfchar1, paramInt1, paramInt2, paramArrayOfchar2, paramInt3, paramInt4, paramInt5);
/*      */     
/*  782 */     if (i <= paramInt4 - paramInt3) {
/*  783 */       return i;
/*      */     }
/*  785 */     throw new IndexOutOfBoundsException(Integer.toString(i));
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
/*      */   public int current() {
/*  799 */     if (this.bufferPos < this.bufferLimit || nextNormalize()) {
/*  800 */       return getCodePointAt(this.bufferPos);
/*      */     }
/*  802 */     return -1;
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
/*      */   public int next() {
/*  814 */     if (this.bufferPos < this.bufferLimit || nextNormalize()) {
/*  815 */       int i = getCodePointAt(this.bufferPos);
/*  816 */       this.bufferPos += (i > 65535) ? 2 : 1;
/*  817 */       return i;
/*      */     } 
/*  819 */     return -1;
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
/*      */   public int previous() {
/*  832 */     if (this.bufferPos > 0 || previousNormalize()) {
/*  833 */       int i = getCodePointAt(this.bufferPos - 1);
/*  834 */       this.bufferPos -= (i > 65535) ? 2 : 1;
/*  835 */       return i;
/*      */     } 
/*  837 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reset() {
/*  847 */     this.text.setIndex(0);
/*  848 */     this.currentIndex = this.nextIndex = 0;
/*  849 */     clearBuffer();
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
/*      */   public void setIndexOnly(int paramInt) {
/*  862 */     this.text.setIndex(paramInt);
/*  863 */     this.currentIndex = this.nextIndex = paramInt;
/*  864 */     clearBuffer();
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
/*      */   @Deprecated
/*      */   public int setIndex(int paramInt) {
/*  891 */     setIndexOnly(paramInt);
/*  892 */     return current();
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
/*      */   @Deprecated
/*      */   public int getBeginIndex() {
/*  905 */     return 0;
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
/*      */   @Deprecated
/*      */   public int getEndIndex() {
/*  918 */     return endIndex();
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
/*      */   public int getIndex() {
/*  937 */     if (this.bufferPos < this.bufferLimit) {
/*  938 */       return this.currentIndex;
/*      */     }
/*  940 */     return this.nextIndex;
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
/*      */   public int endIndex() {
/*  952 */     return this.text.getLength();
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
/*      */   public void setMode(Mode paramMode) {
/*  985 */     this.mode = paramMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Mode getMode() {
/*  994 */     return this.mode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setText(String paramString) {
/* 1005 */     UCharacterIterator uCharacterIterator = UCharacterIterator.getInstance(paramString);
/* 1006 */     if (uCharacterIterator == null) {
/* 1007 */       throw new InternalError("Could not create a new UCharacterIterator");
/*      */     }
/* 1009 */     this.text = uCharacterIterator;
/* 1010 */     reset();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setText(CharacterIterator paramCharacterIterator) {
/* 1021 */     UCharacterIterator uCharacterIterator = UCharacterIterator.getInstance(paramCharacterIterator);
/* 1022 */     if (uCharacterIterator == null) {
/* 1023 */       throw new InternalError("Could not create a new UCharacterIterator");
/*      */     }
/* 1025 */     this.text = uCharacterIterator;
/* 1026 */     this.currentIndex = this.nextIndex = 0;
/* 1027 */     clearBuffer();
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
/*      */   private static long getPrevNorm32(UCharacterIterator paramUCharacterIterator, int paramInt1, int paramInt2, char[] paramArrayOfchar) {
/* 1049 */     int i = 0;
/*      */     
/* 1051 */     if ((i = paramUCharacterIterator.previous()) == -1) {
/* 1052 */       return 0L;
/*      */     }
/* 1054 */     paramArrayOfchar[0] = (char)i;
/* 1055 */     paramArrayOfchar[1] = Character.MIN_VALUE;
/*      */ 
/*      */ 
/*      */     
/* 1059 */     if (paramArrayOfchar[0] < paramInt1)
/* 1060 */       return 0L; 
/* 1061 */     if (!UTF16.isSurrogate(paramArrayOfchar[0]))
/* 1062 */       return NormalizerImpl.getNorm32(paramArrayOfchar[0]); 
/* 1063 */     if (UTF16.isLeadSurrogate(paramArrayOfchar[0]) || paramUCharacterIterator.getIndex() == 0) {
/*      */       
/* 1065 */       paramArrayOfchar[1] = (char)paramUCharacterIterator.current();
/* 1066 */       return 0L;
/* 1067 */     }  if (UTF16.isLeadSurrogate(paramArrayOfchar[1] = (char)paramUCharacterIterator.previous())) {
/* 1068 */       long l = NormalizerImpl.getNorm32(paramArrayOfchar[1]);
/* 1069 */       if ((l & paramInt2) == 0L)
/*      */       {
/*      */         
/* 1072 */         return 0L;
/*      */       }
/*      */       
/* 1075 */       return NormalizerImpl.getNorm32FromSurrogatePair(l, paramArrayOfchar[0]);
/*      */     } 
/*      */ 
/*      */     
/* 1079 */     paramUCharacterIterator.moveIndex(1);
/* 1080 */     return 0L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static interface IsPrevBoundary
/*      */   {
/*      */     boolean isPrevBoundary(UCharacterIterator param1UCharacterIterator, int param1Int1, int param1Int2, char[] param1ArrayOfchar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class IsPrevNFDSafe
/*      */     implements IsPrevBoundary
/*      */   {
/*      */     private IsPrevNFDSafe() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isPrevBoundary(UCharacterIterator param1UCharacterIterator, int param1Int1, int param1Int2, char[] param1ArrayOfchar) {
/* 1102 */       return NormalizerImpl.isNFDSafe(NormalizerBase.getPrevNorm32(param1UCharacterIterator, param1Int1, param1Int2, param1ArrayOfchar), param1Int2, param1Int2 & 0x3F);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class IsPrevTrueStarter
/*      */     implements IsPrevBoundary
/*      */   {
/*      */     private IsPrevTrueStarter() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isPrevBoundary(UCharacterIterator param1UCharacterIterator, int param1Int1, int param1Int2, char[] param1ArrayOfchar) {
/* 1123 */       int i = param1Int2 << 2 & 0xF;
/* 1124 */       long l = NormalizerBase.getPrevNorm32(param1UCharacterIterator, param1Int1, param1Int2 | i, param1ArrayOfchar);
/* 1125 */       return NormalizerImpl.isTrueStarter(l, param1Int2, i);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int findPreviousIterationBoundary(UCharacterIterator paramUCharacterIterator, IsPrevBoundary paramIsPrevBoundary, int paramInt1, int paramInt2, char[] paramArrayOfchar, int[] paramArrayOfint) {
/* 1135 */     char[] arrayOfChar = new char[2];
/*      */ 
/*      */ 
/*      */     
/* 1139 */     paramArrayOfint[0] = paramArrayOfchar.length;
/* 1140 */     arrayOfChar[0] = Character.MIN_VALUE;
/* 1141 */     while (paramUCharacterIterator.getIndex() > 0 && arrayOfChar[0] != -1) {
/* 1142 */       boolean bool = paramIsPrevBoundary.isPrevBoundary(paramUCharacterIterator, paramInt1, paramInt2, arrayOfChar);
/*      */ 
/*      */ 
/*      */       
/* 1146 */       if (paramArrayOfint[0] < ((arrayOfChar[1] == '\000') ? 1 : 2)) {
/*      */ 
/*      */         
/* 1149 */         char[] arrayOfChar1 = new char[paramArrayOfchar.length * 2];
/*      */         
/* 1151 */         System.arraycopy(paramArrayOfchar, paramArrayOfint[0], arrayOfChar1, arrayOfChar1.length - paramArrayOfchar.length - paramArrayOfint[0], paramArrayOfchar.length - paramArrayOfint[0]);
/*      */ 
/*      */ 
/*      */         
/* 1155 */         paramArrayOfint[0] = paramArrayOfint[0] + arrayOfChar1.length - paramArrayOfchar.length;
/*      */         
/* 1157 */         paramArrayOfchar = arrayOfChar1;
/* 1158 */         arrayOfChar1 = null;
/*      */       } 
/*      */ 
/*      */       
/* 1162 */       paramArrayOfint[0] = paramArrayOfint[0] - 1; paramArrayOfchar[paramArrayOfint[0] - 1] = arrayOfChar[0];
/* 1163 */       if (arrayOfChar[1] != '\000') {
/* 1164 */         paramArrayOfint[0] = paramArrayOfint[0] - 1; paramArrayOfchar[paramArrayOfint[0] - 1] = arrayOfChar[1];
/*      */       } 
/*      */ 
/*      */       
/* 1168 */       if (bool) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1174 */     return paramArrayOfchar.length - paramArrayOfint[0];
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
/*      */   private static int previous(UCharacterIterator paramUCharacterIterator, char[] paramArrayOfchar, int paramInt1, int paramInt2, Mode paramMode, boolean paramBoolean, boolean[] paramArrayOfboolean, int paramInt3) {
/* 1190 */     int m = paramInt2 - paramInt1;
/* 1191 */     int i = 0;
/*      */     
/* 1193 */     if (paramArrayOfboolean != null) {
/* 1194 */       paramArrayOfboolean[0] = false;
/*      */     }
/* 1196 */     char c = (char)paramMode.getMinC();
/* 1197 */     int k = paramMode.getMask();
/* 1198 */     IsPrevBoundary isPrevBoundary = paramMode.getPrevBoundary();
/*      */     
/* 1200 */     if (isPrevBoundary == null) {
/* 1201 */       i = 0; int n;
/* 1202 */       if ((n = paramUCharacterIterator.previous()) >= 0) {
/* 1203 */         i = 1;
/* 1204 */         if (UTF16.isTrailSurrogate((char)n)) {
/* 1205 */           int i1 = paramUCharacterIterator.previous();
/* 1206 */           if (i1 != -1) {
/* 1207 */             if (UTF16.isLeadSurrogate((char)i1)) {
/* 1208 */               if (m >= 2) {
/* 1209 */                 paramArrayOfchar[1] = (char)n;
/* 1210 */                 i = 2;
/*      */               } 
/*      */               
/* 1213 */               n = i1;
/*      */             } else {
/* 1215 */               paramUCharacterIterator.moveIndex(1);
/*      */             } 
/*      */           }
/*      */         } 
/*      */         
/* 1220 */         if (m > 0) {
/* 1221 */           paramArrayOfchar[0] = (char)n;
/*      */         }
/*      */       } 
/* 1224 */       return i;
/*      */     } 
/*      */     
/* 1227 */     char[] arrayOfChar = new char[100];
/* 1228 */     int[] arrayOfInt = new int[1];
/* 1229 */     int j = findPreviousIterationBoundary(paramUCharacterIterator, isPrevBoundary, c, k, arrayOfChar, arrayOfInt);
/*      */ 
/*      */ 
/*      */     
/* 1233 */     if (j > 0) {
/* 1234 */       if (paramBoolean) {
/* 1235 */         i = normalize(arrayOfChar, arrayOfInt[0], arrayOfInt[0] + j, paramArrayOfchar, paramInt1, paramInt2, paramMode, paramInt3);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1240 */         if (paramArrayOfboolean != null) {
/* 1241 */           paramArrayOfboolean[0] = (i != j || 
/* 1242 */             Utility.arrayRegionMatches(arrayOfChar, 0, paramArrayOfchar, paramInt1, paramInt2));
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/* 1249 */       else if (m > 0) {
/* 1250 */         System.arraycopy(arrayOfChar, arrayOfInt[0], paramArrayOfchar, 0, (j < m) ? j : m);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1259 */     return i;
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
/*      */   private static long getNextNorm32(UCharacterIterator paramUCharacterIterator, int paramInt1, int paramInt2, int[] paramArrayOfint) {
/* 1288 */     paramArrayOfint[0] = paramUCharacterIterator.next();
/* 1289 */     paramArrayOfint[1] = 0;
/*      */     
/* 1291 */     if (paramArrayOfint[0] < paramInt1) {
/* 1292 */       return 0L;
/*      */     }
/*      */     
/* 1295 */     long l = NormalizerImpl.getNorm32((char)paramArrayOfint[0]);
/* 1296 */     if (UTF16.isLeadSurrogate((char)paramArrayOfint[0])) {
/* 1297 */       paramArrayOfint[1] = paramUCharacterIterator
/* 1298 */         .current(); if (paramUCharacterIterator.current() != -1 && UTF16.isTrailSurrogate((char)paramUCharacterIterator.current())) {
/* 1299 */         paramUCharacterIterator.moveIndex(1);
/* 1300 */         if ((l & paramInt2) == 0L)
/*      */         {
/* 1302 */           return 0L;
/*      */         }
/*      */         
/* 1305 */         return NormalizerImpl.getNorm32FromSurrogatePair(l, (char)paramArrayOfint[1]);
/*      */       } 
/*      */ 
/*      */       
/* 1309 */       return 0L;
/*      */     } 
/*      */     
/* 1312 */     return l;
/*      */   }
/*      */   
/*      */   private static interface IsNextBoundary
/*      */   {
/*      */     boolean isNextBoundary(UCharacterIterator param1UCharacterIterator, int param1Int1, int param1Int2, int[] param1ArrayOfint);
/*      */   }
/*      */   
/*      */   private static final class IsNextNFDSafe
/*      */     implements IsNextBoundary
/*      */   {
/*      */     private IsNextNFDSafe() {}
/*      */     
/*      */     public boolean isNextBoundary(UCharacterIterator param1UCharacterIterator, int param1Int1, int param1Int2, int[] param1ArrayOfint) {
/* 1326 */       return NormalizerImpl.isNFDSafe(NormalizerBase.getNextNorm32(param1UCharacterIterator, param1Int1, param1Int2, param1ArrayOfint), param1Int2, param1Int2 & 0x3F);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class IsNextTrueStarter
/*      */     implements IsNextBoundary
/*      */   {
/*      */     private IsNextTrueStarter() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isNextBoundary(UCharacterIterator param1UCharacterIterator, int param1Int1, int param1Int2, int[] param1ArrayOfint) {
/* 1345 */       int i = param1Int2 << 2 & 0xF;
/* 1346 */       long l = NormalizerBase.getNextNorm32(param1UCharacterIterator, param1Int1, param1Int2 | i, param1ArrayOfint);
/* 1347 */       return NormalizerImpl.isTrueStarter(l, param1Int2, i);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int findNextIterationBoundary(UCharacterIterator paramUCharacterIterator, IsNextBoundary paramIsNextBoundary, int paramInt1, int paramInt2, char[] paramArrayOfchar) {
/* 1356 */     if (paramUCharacterIterator.current() == -1) {
/* 1357 */       return 0;
/*      */     }
/*      */ 
/*      */     
/* 1361 */     int[] arrayOfInt = new int[2];
/* 1362 */     arrayOfInt[0] = paramUCharacterIterator.next();
/* 1363 */     paramArrayOfchar[0] = (char)arrayOfInt[0];
/* 1364 */     byte b = 1;
/*      */     
/* 1366 */     if (UTF16.isLeadSurrogate((char)arrayOfInt[0]) && paramUCharacterIterator
/* 1367 */       .current() != -1) {
/* 1368 */       arrayOfInt[1] = paramUCharacterIterator.next(); if (UTF16.isTrailSurrogate((char)paramUCharacterIterator.next())) {
/* 1369 */         paramArrayOfchar[b++] = (char)arrayOfInt[1];
/*      */       } else {
/* 1371 */         paramUCharacterIterator.moveIndex(-1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1378 */     while (paramUCharacterIterator.current() != -1) {
/* 1379 */       if (paramIsNextBoundary.isNextBoundary(paramUCharacterIterator, paramInt1, paramInt2, arrayOfInt)) {
/*      */         
/* 1381 */         paramUCharacterIterator.moveIndex((arrayOfInt[1] == 0) ? -1 : -2);
/*      */         break;
/*      */       } 
/* 1384 */       if (b + ((arrayOfInt[1] == 0) ? 1 : 2) <= paramArrayOfchar.length) {
/* 1385 */         paramArrayOfchar[b++] = (char)arrayOfInt[0];
/* 1386 */         if (arrayOfInt[1] != 0)
/* 1387 */           paramArrayOfchar[b++] = (char)arrayOfInt[1]; 
/*      */         continue;
/*      */       } 
/* 1390 */       char[] arrayOfChar = new char[paramArrayOfchar.length * 2];
/* 1391 */       System.arraycopy(paramArrayOfchar, 0, arrayOfChar, 0, b);
/* 1392 */       paramArrayOfchar = arrayOfChar;
/* 1393 */       paramArrayOfchar[b++] = (char)arrayOfInt[0];
/* 1394 */       if (arrayOfInt[1] != 0) {
/* 1395 */         paramArrayOfchar[b++] = (char)arrayOfInt[1];
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1402 */     return b;
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
/*      */   private static int next(UCharacterIterator paramUCharacterIterator, char[] paramArrayOfchar, int paramInt1, int paramInt2, Mode paramMode, boolean paramBoolean, boolean[] paramArrayOfboolean, int paramInt3) {
/* 1417 */     int k = paramInt2 - paramInt1;
/* 1418 */     int m = 0;
/* 1419 */     if (paramArrayOfboolean != null) {
/* 1420 */       paramArrayOfboolean[0] = false;
/*      */     }
/*      */     
/* 1423 */     char c = (char)paramMode.getMinC();
/* 1424 */     int i = paramMode.getMask();
/* 1425 */     IsNextBoundary isNextBoundary = paramMode.getNextBoundary();
/*      */     
/* 1427 */     if (isNextBoundary == null) {
/* 1428 */       m = 0;
/* 1429 */       int n = paramUCharacterIterator.next();
/* 1430 */       if (n != -1) {
/* 1431 */         m = 1;
/* 1432 */         if (UTF16.isLeadSurrogate((char)n)) {
/* 1433 */           int i1 = paramUCharacterIterator.next();
/* 1434 */           if (i1 != -1) {
/* 1435 */             if (UTF16.isTrailSurrogate((char)i1)) {
/* 1436 */               if (k >= 2) {
/* 1437 */                 paramArrayOfchar[1] = (char)i1;
/* 1438 */                 m = 2;
/*      */               } 
/*      */             } else {
/*      */               
/* 1442 */               paramUCharacterIterator.moveIndex(-1);
/*      */             } 
/*      */           }
/*      */         } 
/*      */         
/* 1447 */         if (k > 0) {
/* 1448 */           paramArrayOfchar[0] = (char)n;
/*      */         }
/*      */       } 
/* 1451 */       return m;
/*      */     } 
/*      */     
/* 1454 */     char[] arrayOfChar = new char[100];
/* 1455 */     int[] arrayOfInt = new int[1];
/* 1456 */     int j = findNextIterationBoundary(paramUCharacterIterator, isNextBoundary, c, i, arrayOfChar);
/*      */     
/* 1458 */     if (j > 0) {
/* 1459 */       if (paramBoolean) {
/* 1460 */         m = paramMode.normalize(arrayOfChar, arrayOfInt[0], j, paramArrayOfchar, paramInt1, paramInt2, paramInt3);
/*      */ 
/*      */         
/* 1463 */         if (paramArrayOfboolean != null) {
/* 1464 */           paramArrayOfboolean[0] = (m != j || 
/* 1465 */             Utility.arrayRegionMatches(arrayOfChar, arrayOfInt[0], paramArrayOfchar, paramInt1, m));
/*      */ 
/*      */         
/*      */         }
/*      */       
/*      */       }
/* 1471 */       else if (k > 0) {
/* 1472 */         System.arraycopy(arrayOfChar, 0, paramArrayOfchar, paramInt1, 
/* 1473 */             Math.min(j, k));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1480 */     return m;
/*      */   }
/*      */   
/*      */   private void clearBuffer() {
/* 1484 */     this.bufferLimit = this.bufferStart = this.bufferPos = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean nextNormalize() {
/* 1489 */     clearBuffer();
/* 1490 */     this.currentIndex = this.nextIndex;
/* 1491 */     this.text.setIndex(this.nextIndex);
/*      */     
/* 1493 */     this.bufferLimit = next(this.text, this.buffer, this.bufferStart, this.buffer.length, this.mode, true, null, this.options);
/*      */     
/* 1495 */     this.nextIndex = this.text.getIndex();
/* 1496 */     return (this.bufferLimit > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean previousNormalize() {
/* 1501 */     clearBuffer();
/* 1502 */     this.nextIndex = this.currentIndex;
/* 1503 */     this.text.setIndex(this.currentIndex);
/* 1504 */     this.bufferLimit = previous(this.text, this.buffer, this.bufferStart, this.buffer.length, this.mode, true, null, this.options);
/*      */     
/* 1506 */     this.currentIndex = this.text.getIndex();
/* 1507 */     this.bufferPos = this.bufferLimit;
/* 1508 */     return (this.bufferLimit > 0);
/*      */   }
/*      */   
/*      */   private int getCodePointAt(int paramInt) {
/* 1512 */     if (UTF16.isSurrogate(this.buffer[paramInt])) {
/* 1513 */       if (UTF16.isLeadSurrogate(this.buffer[paramInt])) {
/* 1514 */         if (paramInt + 1 < this.bufferLimit && 
/* 1515 */           UTF16.isTrailSurrogate(this.buffer[paramInt + 1])) {
/* 1516 */           return UCharacterProperty.getRawSupplementary(this.buffer[paramInt], this.buffer[paramInt + 1]);
/*      */         
/*      */         }
/*      */       
/*      */       }
/* 1521 */       else if (UTF16.isTrailSurrogate(this.buffer[paramInt]) && 
/* 1522 */         paramInt > 0 && UTF16.isLeadSurrogate(this.buffer[paramInt - 1])) {
/* 1523 */         return UCharacterProperty.getRawSupplementary(this.buffer[paramInt - 1], this.buffer[paramInt]);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1530 */     return this.buffer[paramInt];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNFSkippable(int paramInt, Mode paramMode) {
/* 1539 */     return paramMode.isNFSkippable(paramInt);
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
/*      */   public NormalizerBase(String paramString, Mode paramMode) {
/* 1583 */     this(paramString, paramMode, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String normalize(String paramString, Normalizer.Form paramForm) {
/* 1593 */     return normalize(paramString, paramForm, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String normalize(String paramString, Normalizer.Form paramForm, int paramInt) {
/* 1604 */     int i = paramString.length();
/* 1605 */     boolean bool = true;
/* 1606 */     if (i < 80) {
/* 1607 */       for (byte b = 0; b < i; b++) {
/* 1608 */         if (paramString.charAt(b) > '') {
/* 1609 */           bool = false;
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } else {
/* 1614 */       char[] arrayOfChar = paramString.toCharArray();
/* 1615 */       for (byte b = 0; b < i; b++) {
/* 1616 */         if (arrayOfChar[b] > '') {
/* 1617 */           bool = false;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1623 */     switch (paramForm) {
/*      */       case NFC:
/* 1625 */         return bool ? paramString : NFC.normalize(paramString, paramInt);
/*      */       case NFD:
/* 1627 */         return bool ? paramString : NFD.normalize(paramString, paramInt);
/*      */       case NFKC:
/* 1629 */         return bool ? paramString : NFKC.normalize(paramString, paramInt);
/*      */       case NFKD:
/* 1631 */         return bool ? paramString : NFKD.normalize(paramString, paramInt);
/*      */     } 
/*      */     
/* 1634 */     throw new IllegalArgumentException("Unexpected normalization form: " + paramForm);
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
/*      */   public static boolean isNormalized(String paramString, Normalizer.Form paramForm) {
/* 1652 */     return isNormalized(paramString, paramForm, 0);
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
/*      */   public static boolean isNormalized(String paramString, Normalizer.Form paramForm, int paramInt) {
/* 1669 */     switch (paramForm) {
/*      */       case NFC:
/* 1671 */         return (NFC.quickCheck(paramString.toCharArray(), 0, paramString.length(), false, NormalizerImpl.getNX(paramInt)) == YES);
/*      */       case NFD:
/* 1673 */         return (NFD.quickCheck(paramString.toCharArray(), 0, paramString.length(), false, NormalizerImpl.getNX(paramInt)) == YES);
/*      */       case NFKC:
/* 1675 */         return (NFKC.quickCheck(paramString.toCharArray(), 0, paramString.length(), false, NormalizerImpl.getNX(paramInt)) == YES);
/*      */       case NFKD:
/* 1677 */         return (NFKD.quickCheck(paramString.toCharArray(), 0, paramString.length(), false, NormalizerImpl.getNX(paramInt)) == YES);
/*      */     } 
/*      */     
/* 1680 */     throw new IllegalArgumentException("Unexpected normalization form: " + paramForm);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/normalizer/NormalizerBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */