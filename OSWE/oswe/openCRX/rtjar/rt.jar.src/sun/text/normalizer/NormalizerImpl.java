/*      */ package sun.text.normalizer;
/*      */ 
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class NormalizerImpl
/*      */ {
/*      */   static final NormalizerImpl IMPL;
/*      */   static final int UNSIGNED_BYTE_MASK = 255;
/*      */   static final long UNSIGNED_INT_MASK = 4294967295L;
/*      */   private static final String DATA_FILE_NAME = "/sun/text/resources/unorm.icu";
/*      */   public static final int QC_NFC = 17;
/*      */   public static final int QC_NFKC = 34;
/*      */   public static final int QC_NFD = 4;
/*      */   public static final int QC_NFKD = 8;
/*      */   public static final int QC_ANY_NO = 15;
/*      */   public static final int QC_MAYBE = 16;
/*      */   public static final int QC_ANY_MAYBE = 48;
/*      */   public static final int QC_MASK = 63;
/*      */   private static final int COMBINES_FWD = 64;
/*      */   private static final int COMBINES_BACK = 128;
/*      */   public static final int COMBINES_ANY = 192;
/*      */   private static final int CC_SHIFT = 8;
/*      */   public static final int CC_MASK = 65280;
/*      */   private static final int EXTRA_SHIFT = 16;
/*      */   private static final long MIN_SPECIAL = 4227858432L;
/*      */   private static final long SURROGATES_TOP = 4293918720L;
/*      */   private static final long MIN_HANGUL = 4293918720L;
/*      */   private static final long JAMO_V_TOP = 4294115328L;
/*      */   static final int INDEX_TRIE_SIZE = 0;
/*      */   static final int INDEX_CHAR_COUNT = 1;
/*      */   static final int INDEX_COMBINE_DATA_COUNT = 2;
/*      */   public static final int INDEX_MIN_NFC_NO_MAYBE = 6;
/*      */   public static final int INDEX_MIN_NFKC_NO_MAYBE = 7;
/*      */   public static final int INDEX_MIN_NFD_NO_MAYBE = 8;
/*      */   public static final int INDEX_MIN_NFKD_NO_MAYBE = 9;
/*      */   static final int INDEX_FCD_TRIE_SIZE = 10;
/*      */   static final int INDEX_AUX_TRIE_SIZE = 11;
/*      */   static final int INDEX_TOP = 32;
/*      */   private static final int AUX_UNSAFE_SHIFT = 11;
/*      */   private static final int AUX_COMP_EX_SHIFT = 10;
/*      */   
/*      */   static {
/*      */     try {
/*   56 */       IMPL = new NormalizerImpl();
/*      */     }
/*   58 */     catch (Exception exception) {
/*      */       
/*   60 */       throw new RuntimeException(exception.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int AUX_NFC_SKIPPABLE_F_SHIFT = 12;
/*      */ 
/*      */   
/*      */   private static final int AUX_MAX_FNC = 1024;
/*      */ 
/*      */   
/*      */   private static final int AUX_UNSAFE_MASK = 2048;
/*      */ 
/*      */   
/*      */   private static final int AUX_FNC_MASK = 1023;
/*      */ 
/*      */   
/*      */   private static final int AUX_COMP_EX_MASK = 1024;
/*      */ 
/*      */   
/*      */   private static final long AUX_NFC_SKIP_F_MASK = 4096L;
/*      */ 
/*      */   
/*      */   private static final int MAX_BUFFER_SIZE = 20;
/*      */ 
/*      */   
/*      */   private static FCDTrieImpl fcdTrieImpl;
/*      */ 
/*      */   
/*      */   private static NormTrieImpl normTrieImpl;
/*      */ 
/*      */   
/*      */   private static AuxTrieImpl auxTrieImpl;
/*      */ 
/*      */   
/*      */   private static int[] indexes;
/*      */ 
/*      */   
/*      */   private static char[] combiningTable;
/*      */   
/*      */   private static char[] extraData;
/*      */   
/*      */   private static boolean isDataLoaded;
/*      */   
/*      */   private static boolean isFormatVersion_2_1;
/*      */   
/*      */   private static boolean isFormatVersion_2_2;
/*      */   
/*      */   private static byte[] unicodeVersion;
/*      */   
/*      */   private static final int DATA_BUFFER_SIZE = 25000;
/*      */   
/*      */   public static final int MIN_WITH_LEAD_CC = 768;
/*      */   
/*      */   private static final int DECOMP_FLAG_LENGTH_HAS_CC = 128;
/*      */   
/*      */   private static final int DECOMP_LENGTH_MASK = 127;
/*      */   
/*      */   private static final int BMP_INDEX_LENGTH = 2048;
/*      */   
/*      */   private static final int SURROGATE_BLOCK_BITS = 5;
/*      */   
/*      */   public static final int JAMO_L_BASE = 4352;
/*      */   
/*      */   public static final int JAMO_V_BASE = 4449;
/*      */   
/*      */   public static final int JAMO_T_BASE = 4519;
/*      */   
/*      */   public static final int HANGUL_BASE = 44032;
/*      */   
/*      */   public static final int JAMO_L_COUNT = 19;
/*      */   
/*      */   public static final int JAMO_V_COUNT = 21;
/*      */   
/*      */   public static final int JAMO_T_COUNT = 28;
/*      */   
/*      */   public static final int HANGUL_COUNT = 11172;
/*      */   
/*      */   private static final int OPTIONS_NX_MASK = 31;
/*      */   
/*      */   private static final int OPTIONS_UNICODE_MASK = 224;
/*      */   
/*      */   public static final int OPTIONS_SETS_MASK = 255;
/*      */ 
/*      */   
/*      */   static final class NormTrieImpl
/*      */     implements Trie.DataManipulate
/*      */   {
/*  149 */     static IntTrie normTrie = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getFoldingOffset(int param1Int) {
/*  159 */       return 2048 + (param1Int >> 11 & 0x7FE0);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class FCDTrieImpl
/*      */     implements Trie.DataManipulate
/*      */   {
/*  166 */     static CharTrie fcdTrie = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getFoldingOffset(int param1Int) {
/*  176 */       return param1Int;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class AuxTrieImpl implements Trie.DataManipulate {
/*  181 */     static CharTrie auxTrie = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getFoldingOffset(int param1Int) {
/*  191 */       return (param1Int & 0x3FF) << 5;
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
/*      */   public static int getFromIndexesArr(int paramInt) {
/*  245 */     return indexes[paramInt];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private NormalizerImpl() throws IOException {
/*  256 */     if (!isDataLoaded) {
/*      */ 
/*      */       
/*  259 */       InputStream inputStream = ICUData.getRequiredStream("/sun/text/resources/unorm.icu");
/*  260 */       BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 25000);
/*  261 */       NormalizerDataReader normalizerDataReader = new NormalizerDataReader(bufferedInputStream);
/*      */ 
/*      */       
/*  264 */       indexes = normalizerDataReader.readIndexes(32);
/*      */       
/*  266 */       byte[] arrayOfByte1 = new byte[indexes[0]];
/*      */       
/*  268 */       int i = indexes[2];
/*  269 */       combiningTable = new char[i];
/*      */       
/*  271 */       int j = indexes[1];
/*  272 */       extraData = new char[j];
/*      */       
/*  274 */       byte[] arrayOfByte2 = new byte[indexes[10]];
/*  275 */       byte[] arrayOfByte3 = new byte[indexes[11]];
/*      */       
/*  277 */       fcdTrieImpl = new FCDTrieImpl();
/*  278 */       normTrieImpl = new NormTrieImpl();
/*  279 */       auxTrieImpl = new AuxTrieImpl();
/*      */ 
/*      */       
/*  282 */       normalizerDataReader.read(arrayOfByte1, arrayOfByte2, arrayOfByte3, extraData, combiningTable);
/*      */       
/*  284 */       NormTrieImpl.normTrie = new IntTrie(new ByteArrayInputStream(arrayOfByte1), normTrieImpl);
/*  285 */       FCDTrieImpl.fcdTrie = new CharTrie(new ByteArrayInputStream(arrayOfByte2), fcdTrieImpl);
/*  286 */       AuxTrieImpl.auxTrie = new CharTrie(new ByteArrayInputStream(arrayOfByte3), auxTrieImpl);
/*      */ 
/*      */ 
/*      */       
/*  290 */       isDataLoaded = true;
/*      */ 
/*      */       
/*  293 */       byte[] arrayOfByte4 = normalizerDataReader.getDataFormatVersion();
/*      */       
/*  295 */       isFormatVersion_2_1 = (arrayOfByte4[0] > 2 || (arrayOfByte4[0] == 2 && arrayOfByte4[1] >= 1));
/*      */ 
/*      */ 
/*      */       
/*  299 */       isFormatVersion_2_2 = (arrayOfByte4[0] > 2 || (arrayOfByte4[0] == 2 && arrayOfByte4[1] >= 2));
/*      */ 
/*      */ 
/*      */       
/*  303 */       unicodeVersion = normalizerDataReader.getUnicodeVersion();
/*  304 */       bufferedInputStream.close();
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
/*      */   private static boolean isHangulWithoutJamoT(char paramChar) {
/*  324 */     paramChar = (char)(paramChar - 44032);
/*  325 */     return (paramChar < '⮤' && paramChar % 28 == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isNorm32Regular(long paramLong) {
/*  332 */     return (paramLong < 4227858432L);
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isNorm32LeadSurrogate(long paramLong) {
/*  337 */     return (4227858432L <= paramLong && paramLong < 4293918720L);
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isNorm32HangulOrJamo(long paramLong) {
/*  342 */     return (paramLong >= 4293918720L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isJamoVTNorm32JamoV(long paramLong) {
/*  350 */     return (paramLong < 4294115328L);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getNorm32(char paramChar) {
/*  356 */     return 0xFFFFFFFFL & NormTrieImpl.normTrie.getLeadValue(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getNorm32FromSurrogatePair(long paramLong, char paramChar) {
/*  365 */     return 0xFFFFFFFFL & NormTrieImpl.normTrie
/*  366 */       .getTrailValue((int)paramLong, paramChar);
/*      */   }
/*      */   
/*      */   private static long getNorm32(int paramInt) {
/*  370 */     return 0xFFFFFFFFL & NormTrieImpl.normTrie.getCodePointValue(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long getNorm32(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/*  379 */     long l = getNorm32(paramArrayOfchar[paramInt1]);
/*  380 */     if ((l & paramInt2) > 0L && isNorm32LeadSurrogate(l))
/*      */     {
/*  382 */       l = getNorm32FromSurrogatePair(l, paramArrayOfchar[paramInt1 + 1]);
/*      */     }
/*  384 */     return l;
/*      */   }
/*      */ 
/*      */   
/*      */   public static VersionInfo getUnicodeVersion() {
/*  389 */     return VersionInfo.getInstance(unicodeVersion[0], unicodeVersion[1], unicodeVersion[2], unicodeVersion[3]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static char getFCD16(char paramChar) {
/*  394 */     return FCDTrieImpl.fcdTrie.getLeadValue(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char getFCD16FromSurrogatePair(char paramChar1, char paramChar2) {
/*  401 */     return FCDTrieImpl.fcdTrie.getTrailValue(paramChar1, paramChar2);
/*      */   }
/*      */   public static int getFCD16(int paramInt) {
/*  404 */     return FCDTrieImpl.fcdTrie.getCodePointValue(paramInt);
/*      */   }
/*      */   
/*      */   private static int getExtraDataIndex(long paramLong) {
/*  408 */     return (int)(paramLong >> 16L);
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class DecomposeArgs
/*      */   {
/*      */     int cc;
/*      */     
/*      */     int trailCC;
/*      */     
/*      */     int length;
/*      */ 
/*      */     
/*      */     private DecomposeArgs() {}
/*      */   }
/*      */   
/*      */   private static int decompose(long paramLong, int paramInt, DecomposeArgs paramDecomposeArgs) {
/*  425 */     int i = getExtraDataIndex(paramLong);
/*  426 */     paramDecomposeArgs.length = extraData[i++];
/*      */     
/*  428 */     if ((paramLong & paramInt & 0x8L) != 0L && paramDecomposeArgs.length >= 256) {
/*      */       
/*  430 */       i += (paramDecomposeArgs.length >> 7 & 0x1) + (paramDecomposeArgs.length & 0x7F);
/*  431 */       paramDecomposeArgs.length >>= 8;
/*      */     } 
/*      */     
/*  434 */     if ((paramDecomposeArgs.length & 0x80) > 0) {
/*      */       
/*  436 */       char c = extraData[i++];
/*  437 */       paramDecomposeArgs.cc = 0xFF & c >> 8;
/*  438 */       paramDecomposeArgs.trailCC = 0xFF & c;
/*      */     } else {
/*      */       
/*  441 */       paramDecomposeArgs.cc = paramDecomposeArgs.trailCC = 0;
/*      */     } 
/*      */     
/*  444 */     paramDecomposeArgs.length &= 0x7F;
/*  445 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int decompose(long paramLong, DecomposeArgs paramDecomposeArgs) {
/*  456 */     int i = getExtraDataIndex(paramLong);
/*  457 */     paramDecomposeArgs.length = extraData[i++];
/*      */     
/*  459 */     if ((paramDecomposeArgs.length & 0x80) > 0) {
/*      */       
/*  461 */       char c = extraData[i++];
/*  462 */       paramDecomposeArgs.cc = 0xFF & c >> 8;
/*  463 */       paramDecomposeArgs.trailCC = 0xFF & c;
/*      */     } else {
/*      */       
/*  466 */       paramDecomposeArgs.cc = paramDecomposeArgs.trailCC = 0;
/*      */     } 
/*      */     
/*  469 */     paramDecomposeArgs.length &= 0x7F;
/*  470 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class NextCCArgs
/*      */   {
/*      */     char[] source;
/*      */     
/*      */     int next;
/*      */     
/*      */     int limit;
/*      */     
/*      */     char c;
/*      */     
/*      */     char c2;
/*      */     
/*      */     private NextCCArgs() {}
/*      */   }
/*      */   
/*      */   private static int getNextCC(NextCCArgs paramNextCCArgs) {
/*  490 */     paramNextCCArgs.c = paramNextCCArgs.source[paramNextCCArgs.next++];
/*      */     
/*  492 */     long l = getNorm32(paramNextCCArgs.c);
/*  493 */     if ((l & 0xFF00L) == 0L) {
/*  494 */       paramNextCCArgs.c2 = Character.MIN_VALUE;
/*  495 */       return 0;
/*      */     } 
/*  497 */     if (!isNorm32LeadSurrogate(l)) {
/*  498 */       paramNextCCArgs.c2 = Character.MIN_VALUE;
/*      */     
/*      */     }
/*  501 */     else if (paramNextCCArgs.next != paramNextCCArgs.limit && 
/*  502 */       UTF16.isTrailSurrogate(paramNextCCArgs.c2 = paramNextCCArgs.source[paramNextCCArgs.next])) {
/*  503 */       paramNextCCArgs.next++;
/*  504 */       l = getNorm32FromSurrogatePair(l, paramNextCCArgs.c2);
/*      */     } else {
/*  506 */       paramNextCCArgs.c2 = Character.MIN_VALUE;
/*  507 */       return 0;
/*      */     } 
/*      */ 
/*      */     
/*  511 */     return (int)(0xFFL & l >> 8L);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class PrevArgs
/*      */   {
/*      */     char[] src;
/*      */     
/*      */     int start;
/*      */     
/*      */     int current;
/*      */     
/*      */     char c;
/*      */     
/*      */     char c2;
/*      */ 
/*      */     
/*      */     private PrevArgs() {}
/*      */   }
/*      */ 
/*      */   
/*      */   private static long getPrevNorm32(PrevArgs paramPrevArgs, int paramInt1, int paramInt2) {
/*  534 */     paramPrevArgs.c = paramPrevArgs.src[--paramPrevArgs.current];
/*  535 */     paramPrevArgs.c2 = Character.MIN_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  540 */     if (paramPrevArgs.c < paramInt1)
/*  541 */       return 0L; 
/*  542 */     if (!UTF16.isSurrogate(paramPrevArgs.c))
/*  543 */       return getNorm32(paramPrevArgs.c); 
/*  544 */     if (UTF16.isLeadSurrogate(paramPrevArgs.c))
/*      */     {
/*  546 */       return 0L; } 
/*  547 */     if (paramPrevArgs.current != paramPrevArgs.start && 
/*  548 */       UTF16.isLeadSurrogate(paramPrevArgs.c2 = paramPrevArgs.src[paramPrevArgs.current - 1])) {
/*  549 */       paramPrevArgs.current--;
/*  550 */       long l = getNorm32(paramPrevArgs.c2);
/*      */       
/*  552 */       if ((l & paramInt2) == 0L)
/*      */       {
/*      */ 
/*      */         
/*  556 */         return 0L;
/*      */       }
/*      */       
/*  559 */       return getNorm32FromSurrogatePair(l, paramPrevArgs.c);
/*      */     } 
/*      */ 
/*      */     
/*  563 */     paramPrevArgs.c2 = Character.MIN_VALUE;
/*  564 */     return 0L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getPrevCC(PrevArgs paramPrevArgs) {
/*  574 */     return (int)(0xFFL & getPrevNorm32(paramPrevArgs, 768, 65280) >> 8L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNFDSafe(long paramLong, int paramInt1, int paramInt2) {
/*  585 */     if ((paramLong & paramInt1) == 0L) {
/*  586 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  590 */     if (isNorm32Regular(paramLong) && (paramLong & paramInt2) != 0L) {
/*  591 */       DecomposeArgs decomposeArgs = new DecomposeArgs();
/*      */       
/*  593 */       decompose(paramLong, paramInt2, decomposeArgs);
/*  594 */       return (decomposeArgs.cc == 0);
/*      */     } 
/*      */     
/*  597 */     return ((paramLong & 0xFF00L) == 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isTrueStarter(long paramLong, int paramInt1, int paramInt2) {
/*  608 */     if ((paramLong & paramInt1) == 0L) {
/*  609 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  613 */     if ((paramLong & paramInt2) != 0L) {
/*      */       
/*  615 */       DecomposeArgs decomposeArgs = new DecomposeArgs();
/*      */       
/*  617 */       int i = decompose(paramLong, paramInt2, decomposeArgs);
/*      */       
/*  619 */       if (decomposeArgs.cc == 0) {
/*  620 */         int j = paramInt1 & 0x3F;
/*      */ 
/*      */         
/*  623 */         if ((getNorm32(extraData, i, j) & j) == 0L)
/*      */         {
/*  625 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*  629 */     return false;
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
/*      */   private static int insertOrdered(char[] paramArrayOfchar, int paramInt1, int paramInt2, int paramInt3, char paramChar1, char paramChar2, int paramInt4) {
/*  656 */     int i = paramInt4;
/*      */     
/*  658 */     if (paramInt1 < paramInt2 && paramInt4 != 0) {
/*      */       
/*  660 */       int j = paramInt2, k = j;
/*  661 */       PrevArgs prevArgs = new PrevArgs();
/*  662 */       prevArgs.current = paramInt2;
/*  663 */       prevArgs.start = paramInt1;
/*  664 */       prevArgs.src = paramArrayOfchar;
/*      */       
/*  666 */       int m = getPrevCC(prevArgs);
/*  667 */       k = prevArgs.current;
/*      */       
/*  669 */       if (paramInt4 < m) {
/*      */         
/*  671 */         i = m;
/*  672 */         j = k;
/*  673 */         while (paramInt1 < k) {
/*  674 */           m = getPrevCC(prevArgs);
/*  675 */           k = prevArgs.current;
/*  676 */           if (paramInt4 >= m) {
/*      */             break;
/*      */           }
/*  679 */           j = k;
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
/*  690 */         int n = paramInt3;
/*      */         do {
/*  692 */           paramArrayOfchar[--n] = paramArrayOfchar[--paramInt2];
/*  693 */         } while (j != paramInt2);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  698 */     paramArrayOfchar[paramInt2] = paramChar1;
/*  699 */     if (paramChar2 != '\000') {
/*  700 */       paramArrayOfchar[paramInt2 + 1] = paramChar2;
/*      */     }
/*      */ 
/*      */     
/*  704 */     return i;
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
/*      */   private static int mergeOrdered(char[] paramArrayOfchar1, int paramInt1, int paramInt2, char[] paramArrayOfchar2, int paramInt3, int paramInt4, boolean paramBoolean) {
/*  738 */     int i = 0;
/*      */ 
/*      */     
/*  741 */     boolean bool = (paramInt2 == paramInt3) ? true : false;
/*  742 */     NextCCArgs nextCCArgs = new NextCCArgs();
/*  743 */     nextCCArgs.source = paramArrayOfchar2;
/*  744 */     nextCCArgs.next = paramInt3;
/*  745 */     nextCCArgs.limit = paramInt4;
/*      */     
/*  747 */     if (paramInt1 != paramInt2 || !paramBoolean)
/*      */     {
/*  749 */       while (nextCCArgs.next < nextCCArgs.limit) {
/*  750 */         int k = getNextCC(nextCCArgs);
/*  751 */         if (k == 0) {
/*      */           
/*  753 */           i = 0;
/*  754 */           if (bool) {
/*  755 */             paramInt2 = nextCCArgs.next;
/*      */           } else {
/*  757 */             paramArrayOfchar2[paramInt2++] = nextCCArgs.c;
/*  758 */             if (nextCCArgs.c2 != '\000') {
/*  759 */               paramArrayOfchar2[paramInt2++] = nextCCArgs.c2;
/*      */             }
/*      */           } 
/*  762 */           if (paramBoolean) {
/*      */             break;
/*      */           }
/*  765 */           paramInt1 = paramInt2;
/*      */           continue;
/*      */         } 
/*  768 */         int j = paramInt2 + ((nextCCArgs.c2 == '\000') ? 1 : 2);
/*  769 */         i = insertOrdered(paramArrayOfchar1, paramInt1, paramInt2, j, nextCCArgs.c, nextCCArgs.c2, k);
/*      */         
/*  771 */         paramInt2 = j;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  776 */     if (nextCCArgs.next == nextCCArgs.limit)
/*      */     {
/*  778 */       return i;
/*      */     }
/*  780 */     if (!bool)
/*      */       while (true) {
/*      */         
/*  783 */         paramArrayOfchar1[paramInt2++] = paramArrayOfchar2[nextCCArgs.next++];
/*  784 */         if (nextCCArgs.next == nextCCArgs.limit) {
/*  785 */           nextCCArgs.limit = paramInt2; break;
/*      */         } 
/*  787 */       }   PrevArgs prevArgs = new PrevArgs();
/*  788 */     prevArgs.src = paramArrayOfchar2;
/*  789 */     prevArgs.start = paramInt1;
/*  790 */     prevArgs.current = nextCCArgs.limit;
/*  791 */     return getPrevCC(prevArgs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int mergeOrdered(char[] paramArrayOfchar1, int paramInt1, int paramInt2, char[] paramArrayOfchar2, int paramInt3, int paramInt4) {
/*  801 */     return mergeOrdered(paramArrayOfchar1, paramInt1, paramInt2, paramArrayOfchar2, paramInt3, paramInt4, true);
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
/*      */   public static NormalizerBase.QuickCheckResult quickCheck(char[] paramArrayOfchar, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean, UnicodeSet paramUnicodeSet) {
/*  819 */     ComposePartArgs composePartArgs = new ComposePartArgs();
/*      */     
/*  821 */     int j = paramInt1;
/*      */     
/*  823 */     if (!isDataLoaded) {
/*  824 */       return NormalizerBase.MAYBE;
/*      */     }
/*      */     
/*  827 */     int i = 0xFF00 | paramInt4;
/*  828 */     NormalizerBase.QuickCheckResult quickCheckResult = NormalizerBase.YES;
/*  829 */     char c = Character.MIN_VALUE;
/*      */ 
/*      */     
/*      */     while (true) {
/*  833 */       if (paramInt1 == paramInt2)
/*  834 */         return quickCheckResult;  long l; char c1;
/*  835 */       if ((c1 = paramArrayOfchar[paramInt1++]) >= paramInt3 && ((
/*  836 */         l = getNorm32(c1)) & i) != 0L) {
/*      */         boolean bool;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  844 */         if (isNorm32LeadSurrogate(l)) {
/*      */           
/*  846 */           if (paramInt1 != paramInt2 && UTF16.isTrailSurrogate(bool = paramArrayOfchar[paramInt1])) {
/*  847 */             paramInt1++;
/*  848 */             l = getNorm32FromSurrogatePair(l, bool);
/*      */           } else {
/*  850 */             l = 0L;
/*  851 */             bool = false;
/*      */           } 
/*      */         } else {
/*  854 */           bool = false;
/*      */         } 
/*  856 */         if (nx_contains(paramUnicodeSet, c1, bool))
/*      */         {
/*  858 */           l = 0L;
/*      */         }
/*      */ 
/*      */         
/*  862 */         char c2 = (char)(int)(l >> 8L & 0xFFL);
/*  863 */         if (c2 != '\000' && c2 < c) {
/*  864 */           return NormalizerBase.NO;
/*      */         }
/*  866 */         c = c2;
/*      */ 
/*      */         
/*  869 */         long l1 = l & paramInt4;
/*  870 */         if ((l1 & 0xFL) >= 1L) {
/*  871 */           quickCheckResult = NormalizerBase.NO; break;
/*      */         } 
/*  873 */         if (l1 != 0L) {
/*      */           
/*  875 */           if (paramBoolean) {
/*  876 */             quickCheckResult = NormalizerBase.MAYBE;
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */ 
/*      */           
/*  883 */           int m = paramInt4 << 2 & 0xF;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  888 */           int k = paramInt1 - 1;
/*  889 */           if (UTF16.isTrailSurrogate(paramArrayOfchar[k]))
/*      */           {
/*      */             
/*  892 */             k--;
/*      */           }
/*  894 */           k = findPreviousStarter(paramArrayOfchar, j, k, i, m, (char)paramInt3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  900 */           paramInt1 = findNextStarter(paramArrayOfchar, paramInt1, paramInt2, paramInt4, m, (char)paramInt3);
/*      */ 
/*      */ 
/*      */           
/*  904 */           composePartArgs.prevCC = c;
/*      */ 
/*      */           
/*  907 */           char[] arrayOfChar = composePart(composePartArgs, k, paramArrayOfchar, paramInt1, paramInt2, paramInt5, paramUnicodeSet);
/*      */ 
/*      */           
/*  910 */           if (0 != strCompare(arrayOfChar, 0, composePartArgs.length, paramArrayOfchar, k, paramInt1, false)) {
/*  911 */             quickCheckResult = NormalizerBase.NO;
/*      */             break;
/*      */           } 
/*      */         } 
/*      */         continue;
/*      */       } 
/*      */       c = Character.MIN_VALUE;
/*      */     } 
/*  919 */     return quickCheckResult;
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
/*      */   public static int decompose(char[] paramArrayOfchar1, int paramInt1, int paramInt2, char[] paramArrayOfchar2, int paramInt3, int paramInt4, boolean paramBoolean, int[] paramArrayOfint, UnicodeSet paramUnicodeSet) {
/*      */     byte b;
/*  932 */     char c1, arrayOfChar[] = new char[3];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  941 */     int i2 = paramInt3;
/*  942 */     int i3 = paramInt1;
/*  943 */     if (!paramBoolean) {
/*  944 */       c1 = (char)indexes[8];
/*  945 */       b = 4;
/*      */     } else {
/*  947 */       c1 = (char)indexes[9];
/*  948 */       b = 8;
/*      */     } 
/*      */ 
/*      */     
/*  952 */     int i = 0xFF00 | b;
/*  953 */     int j = 0;
/*  954 */     int m = 0;
/*  955 */     long l = 0L;
/*  956 */     char c = Character.MIN_VALUE;
/*  957 */     int i1 = 0;
/*      */     
/*  959 */     int n = -1, k = n;
/*      */     
/*      */     while (true) {
/*      */       int i5;
/*      */       
/*      */       char c2, arrayOfChar1[];
/*  965 */       int i4 = i3;
/*      */       
/*  967 */       while (i3 != paramInt2 && ((c = paramArrayOfchar1[i3]) < c1 || ((
/*  968 */         l = getNorm32(c)) & i) == 0L)) {
/*  969 */         m = 0;
/*  970 */         i3++;
/*      */       } 
/*      */ 
/*      */       
/*  974 */       if (i3 != i4) {
/*  975 */         i5 = i3 - i4;
/*  976 */         if (i2 + i5 <= paramInt4) {
/*  977 */           System.arraycopy(paramArrayOfchar1, i4, paramArrayOfchar2, i2, i5);
/*      */         }
/*      */         
/*  980 */         i2 += i5;
/*  981 */         j = i2;
/*      */       } 
/*      */ 
/*      */       
/*  985 */       if (i3 == paramInt2) {
/*      */         break;
/*      */       }
/*      */ 
/*      */       
/*  990 */       i3++;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1009 */       if (isNorm32HangulOrJamo(l)) {
/* 1010 */         if (nx_contains(paramUnicodeSet, c)) {
/* 1011 */           c2 = Character.MIN_VALUE;
/* 1012 */           arrayOfChar1 = null;
/* 1013 */           i5 = 1;
/*      */         } else {
/*      */           
/* 1016 */           arrayOfChar1 = arrayOfChar;
/* 1017 */           i1 = 0;
/* 1018 */           k = n = 0;
/*      */           
/* 1020 */           c = (char)(c - 44032);
/*      */           
/* 1022 */           c2 = (char)(c % 28);
/* 1023 */           c = (char)(c / 28);
/* 1024 */           if (c2 > '\000') {
/* 1025 */             arrayOfChar[2] = (char)(4519 + c2);
/* 1026 */             i5 = 3;
/*      */           } else {
/* 1028 */             i5 = 2;
/*      */           } 
/*      */           
/* 1031 */           arrayOfChar[1] = (char)(4449 + c % 21);
/* 1032 */           arrayOfChar[0] = (char)(4352 + c / 21);
/*      */         } 
/*      */       } else {
/* 1035 */         if (isNorm32Regular(l)) {
/* 1036 */           c2 = Character.MIN_VALUE;
/* 1037 */           i5 = 1;
/*      */         
/*      */         }
/* 1040 */         else if (i3 != paramInt2 && 
/* 1041 */           UTF16.isTrailSurrogate(c2 = paramArrayOfchar1[i3])) {
/* 1042 */           i3++;
/* 1043 */           i5 = 2;
/* 1044 */           l = getNorm32FromSurrogatePair(l, c2);
/*      */         } else {
/* 1046 */           c2 = Character.MIN_VALUE;
/* 1047 */           i5 = 1;
/* 1048 */           l = 0L;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1053 */         if (nx_contains(paramUnicodeSet, c, c2)) {
/*      */           
/* 1055 */           k = n = 0;
/* 1056 */           arrayOfChar1 = null;
/* 1057 */         } else if ((l & b) == 0L) {
/*      */           
/* 1059 */           k = n = (int)(0xFFL & l >> 8L);
/* 1060 */           arrayOfChar1 = null;
/* 1061 */           i1 = -1;
/*      */         } else {
/* 1063 */           DecomposeArgs decomposeArgs = new DecomposeArgs();
/*      */ 
/*      */ 
/*      */           
/* 1067 */           i1 = decompose(l, b, decomposeArgs);
/* 1068 */           arrayOfChar1 = extraData;
/* 1069 */           i5 = decomposeArgs.length;
/* 1070 */           k = decomposeArgs.cc;
/* 1071 */           n = decomposeArgs.trailCC;
/* 1072 */           if (i5 == 1) {
/*      */             
/* 1074 */             c = arrayOfChar1[i1];
/* 1075 */             c2 = Character.MIN_VALUE;
/* 1076 */             arrayOfChar1 = null;
/* 1077 */             i1 = -1;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1085 */       if (i2 + i5 <= paramInt4) {
/* 1086 */         int i6 = i2;
/* 1087 */         if (arrayOfChar1 == null) {
/*      */           
/* 1089 */           if (k != 0 && k < m)
/*      */           {
/*      */ 
/*      */             
/* 1093 */             i2 += i5;
/* 1094 */             n = insertOrdered(paramArrayOfchar2, j, i6, i2, c, c2, k);
/*      */           }
/*      */           else
/*      */           {
/* 1098 */             paramArrayOfchar2[i2++] = c;
/* 1099 */             if (c2 != '\000') {
/* 1100 */               paramArrayOfchar2[i2++] = c2;
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         }
/* 1107 */         else if (k != 0 && k < m) {
/*      */ 
/*      */ 
/*      */           
/* 1111 */           i2 += i5;
/* 1112 */           n = mergeOrdered(paramArrayOfchar2, j, i6, arrayOfChar1, i1, i1 + i5);
/*      */         } else {
/*      */ 
/*      */           
/*      */           do {
/* 1117 */             paramArrayOfchar2[i2++] = arrayOfChar1[i1++];
/* 1118 */           } while (--i5 > 0);
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1124 */         i2 += i5;
/*      */       } 
/*      */       
/* 1127 */       m = n;
/* 1128 */       if (m == 0) {
/* 1129 */         j = i2;
/*      */       }
/*      */     } 
/*      */     
/* 1133 */     paramArrayOfint[0] = m;
/*      */     
/* 1135 */     return i2 - paramInt3;
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class NextCombiningArgs
/*      */   {
/*      */     char[] source;
/*      */     
/*      */     int start;
/*      */     
/*      */     char c;
/*      */     
/*      */     char c2;
/*      */     
/*      */     int combiningIndex;
/*      */     char cc;
/*      */     
/*      */     private NextCombiningArgs() {}
/*      */   }
/*      */   
/*      */   private static int getNextCombining(NextCombiningArgs paramNextCombiningArgs, int paramInt, UnicodeSet paramUnicodeSet) {
/* 1156 */     paramNextCombiningArgs.c = paramNextCombiningArgs.source[paramNextCombiningArgs.start++];
/* 1157 */     long l = getNorm32(paramNextCombiningArgs.c);
/*      */ 
/*      */     
/* 1160 */     paramNextCombiningArgs.c2 = Character.MIN_VALUE;
/* 1161 */     paramNextCombiningArgs.combiningIndex = 0;
/* 1162 */     paramNextCombiningArgs.cc = Character.MIN_VALUE;
/*      */     
/* 1164 */     if ((l & 0xFFC0L) == 0L) {
/* 1165 */       return 0;
/*      */     }
/* 1167 */     if (!isNorm32Regular(l)) {
/*      */       
/* 1169 */       if (isNorm32HangulOrJamo(l)) {
/*      */         
/* 1171 */         paramNextCombiningArgs.combiningIndex = (int)(0xFFFFFFFFL & (0xFFF0L | l >> 16L));
/*      */         
/* 1173 */         return (int)(l & 0xC0L);
/*      */       } 
/*      */       
/* 1176 */       if (paramNextCombiningArgs.start != paramInt && UTF16.isTrailSurrogate(paramNextCombiningArgs.c2 = paramNextCombiningArgs.source[paramNextCombiningArgs.start])) {
/*      */         
/* 1178 */         paramNextCombiningArgs.start++;
/* 1179 */         l = getNorm32FromSurrogatePair(l, paramNextCombiningArgs.c2);
/*      */       } else {
/* 1181 */         paramNextCombiningArgs.c2 = Character.MIN_VALUE;
/* 1182 */         return 0;
/*      */       } 
/*      */     } 
/*      */     
/* 1186 */     if (nx_contains(paramUnicodeSet, paramNextCombiningArgs.c, paramNextCombiningArgs.c2)) {
/* 1187 */       return 0;
/*      */     }
/*      */     
/* 1190 */     paramNextCombiningArgs.cc = (char)(int)(l >> 8L & 0xFFL);
/*      */     
/* 1192 */     int i = (int)(l & 0xC0L);
/* 1193 */     if (i != 0) {
/* 1194 */       int j = getExtraDataIndex(l);
/* 1195 */       paramNextCombiningArgs.combiningIndex = (j > 0) ? extraData[j - 1] : 0;
/*      */     } 
/*      */     
/* 1198 */     return i;
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
/*      */   private static int getCombiningIndexFromStarter(char paramChar1, char paramChar2) {
/* 1213 */     long l = getNorm32(paramChar1);
/* 1214 */     if (paramChar2 != '\000') {
/* 1215 */       l = getNorm32FromSurrogatePair(l, paramChar2);
/*      */     }
/* 1217 */     return extraData[getExtraDataIndex(l) - 1];
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
/*      */   private static int combine(char[] paramArrayOfchar, int paramInt1, int paramInt2, int[] paramArrayOfint) {
/*      */     char c;
/* 1243 */     if (paramArrayOfint.length < 2) {
/* 1244 */       throw new IllegalArgumentException();
/*      */     }
/*      */ 
/*      */     
/*      */     while (true) {
/* 1249 */       c = paramArrayOfchar[paramInt1++];
/* 1250 */       if (c >= paramInt2) {
/*      */         break;
/*      */       }
/* 1253 */       paramInt1 += ((paramArrayOfchar[paramInt1] & 0x8000) != 0) ? 2 : 1;
/*      */     } 
/*      */ 
/*      */     
/* 1257 */     if ((c & 0x7FFF) == paramInt2) {
/*      */       boolean bool;
/* 1259 */       int j = paramArrayOfchar[paramInt1];
/*      */ 
/*      */       
/* 1262 */       int i = (int)(0xFFFFFFFFL & ((j & 0x2000) + 1));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1267 */       if ((j & 0x8000) != 0) {
/* 1268 */         if ((j & 0x4000) != 0) {
/*      */           
/* 1270 */           int k = (int)(0xFFFFFFFFL & (j & 0x3FF | 0xD800));
/* 1271 */           bool = paramArrayOfchar[paramInt1 + 1];
/*      */         } else {
/*      */           
/* 1274 */           j = paramArrayOfchar[paramInt1 + 1];
/* 1275 */           bool = false;
/*      */         } 
/*      */       } else {
/*      */         
/* 1279 */         j = j & 0x1FFF;
/* 1280 */         bool = false;
/*      */       } 
/* 1282 */       paramArrayOfint[0] = j;
/* 1283 */       paramArrayOfint[1] = bool;
/* 1284 */       return i;
/*      */     } 
/*      */     
/* 1287 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class RecomposeArgs
/*      */   {
/*      */     char[] source;
/*      */ 
/*      */ 
/*      */     
/*      */     int start;
/*      */ 
/*      */ 
/*      */     
/*      */     int limit;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private RecomposeArgs() {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static char recompose(RecomposeArgs paramRecomposeArgs, int paramInt, UnicodeSet paramUnicodeSet) {
/* 1315 */     int j = 0, k = 0;
/*      */ 
/*      */ 
/*      */     
/* 1319 */     int[] arrayOfInt = new int[2];
/* 1320 */     int m = -1;
/* 1321 */     int i = 0;
/* 1322 */     boolean bool = false;
/* 1323 */     char c = Character.MIN_VALUE;
/*      */     
/* 1325 */     NextCombiningArgs nextCombiningArgs = new NextCombiningArgs();
/* 1326 */     nextCombiningArgs.source = paramRecomposeArgs.source;
/*      */     
/* 1328 */     nextCombiningArgs.cc = Character.MIN_VALUE;
/* 1329 */     nextCombiningArgs.c2 = Character.MIN_VALUE;
/*      */     
/*      */     while (true) {
/* 1332 */       nextCombiningArgs.start = paramRecomposeArgs.start;
/* 1333 */       int n = getNextCombining(nextCombiningArgs, paramRecomposeArgs.limit, paramUnicodeSet);
/* 1334 */       int i1 = nextCombiningArgs.combiningIndex;
/* 1335 */       paramRecomposeArgs.start = nextCombiningArgs.start;
/*      */       
/* 1337 */       if ((n & 0x80) != 0 && m != -1) {
/* 1338 */         if ((i1 & 0x8000) != 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1343 */           if ((paramInt & 0x100) != 0 || !c) {
/* 1344 */             int i2 = -1;
/* 1345 */             n = 0;
/* 1346 */             nextCombiningArgs.c2 = paramRecomposeArgs.source[m];
/* 1347 */             if (i1 == 65522) {
/*      */ 
/*      */ 
/*      */               
/* 1351 */               nextCombiningArgs.c2 = (char)(nextCombiningArgs.c2 - 4352);
/* 1352 */               if (nextCombiningArgs.c2 < '\023') {
/* 1353 */                 i2 = paramRecomposeArgs.start - 1;
/* 1354 */                 nextCombiningArgs.c = (char)(44032 + (nextCombiningArgs.c2 * 21 + nextCombiningArgs.c - 4449) * 28);
/*      */                 
/* 1356 */                 if (paramRecomposeArgs.start != paramRecomposeArgs.limit && (nextCombiningArgs.c2 = (char)(paramRecomposeArgs.source[paramRecomposeArgs.start] - 4519)) < '\034') {
/*      */ 
/*      */                   
/* 1359 */                   paramRecomposeArgs.start++;
/* 1360 */                   nextCombiningArgs.c = (char)(nextCombiningArgs.c + nextCombiningArgs.c2);
/*      */                 } else {
/*      */                   
/* 1363 */                   n = 64;
/*      */                 } 
/* 1365 */                 if (!nx_contains(paramUnicodeSet, nextCombiningArgs.c)) {
/* 1366 */                   paramRecomposeArgs.source[m] = nextCombiningArgs.c;
/*      */                 } else {
/*      */                   
/* 1369 */                   if (!isHangulWithoutJamoT(nextCombiningArgs.c)) {
/* 1370 */                     paramRecomposeArgs.start--;
/*      */                   }
/*      */                   
/* 1373 */                   i2 = paramRecomposeArgs.start;
/*      */ 
/*      */ 
/*      */                 
/*      */                 }
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             }
/* 1388 */             else if (isHangulWithoutJamoT(nextCombiningArgs.c2)) {
/* 1389 */               nextCombiningArgs.c2 = (char)(nextCombiningArgs.c2 + nextCombiningArgs.c - 4519);
/* 1390 */               if (!nx_contains(paramUnicodeSet, nextCombiningArgs.c2)) {
/* 1391 */                 i2 = paramRecomposeArgs.start - 1;
/* 1392 */                 paramRecomposeArgs.source[m] = nextCombiningArgs.c2;
/*      */               } 
/*      */             } 
/*      */ 
/*      */             
/* 1397 */             if (i2 != -1) {
/*      */               
/* 1399 */               int i3 = i2;
/* 1400 */               int i4 = paramRecomposeArgs.start;
/* 1401 */               while (i4 < paramRecomposeArgs.limit) {
/* 1402 */                 paramRecomposeArgs.source[i3++] = paramRecomposeArgs.source[i4++];
/*      */               }
/* 1404 */               paramRecomposeArgs.start = i2;
/* 1405 */               paramRecomposeArgs.limit = i3;
/*      */             } 
/*      */             
/* 1408 */             nextCombiningArgs.c2 = Character.MIN_VALUE;
/*      */             
/* 1410 */             if (n != 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1417 */               if (paramRecomposeArgs.start == paramRecomposeArgs.limit) {
/* 1418 */                 return (char)c;
/*      */               }
/*      */ 
/*      */               
/* 1422 */               i = 65520;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               continue;
/*      */             } 
/*      */           } 
/*      */         } else {
/*      */           int i2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1438 */           if ((i & 0x8000) == 0 && (((paramInt & 0x100) != 0) ? (c != nextCombiningArgs.cc || !c) : (c < nextCombiningArgs.cc || c == Character.MIN_VALUE)) && 0 != (
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1446 */             i2 = combine(combiningTable, i, i1, arrayOfInt)) && 
/*      */ 
/*      */             
/* 1449 */             !nx_contains(paramUnicodeSet, (char)j, (char)k)) {
/*      */             
/* 1451 */             j = arrayOfInt[0];
/* 1452 */             k = arrayOfInt[1];
/*      */ 
/*      */ 
/*      */             
/* 1456 */             int i3 = (nextCombiningArgs.c2 == '\000') ? (paramRecomposeArgs.start - 1) : (paramRecomposeArgs.start - 2);
/*      */ 
/*      */             
/* 1459 */             paramRecomposeArgs.source[m] = (char)j;
/* 1460 */             if (bool) {
/* 1461 */               if (k != 0) {
/*      */                 
/* 1463 */                 paramRecomposeArgs.source[m + 1] = (char)k;
/*      */               }
/*      */               else {
/*      */                 
/* 1467 */                 bool = false;
/* 1468 */                 int i4 = m + 1;
/* 1469 */                 int i5 = i4 + 1;
/* 1470 */                 while (i5 < i3) {
/* 1471 */                   paramRecomposeArgs.source[i4++] = paramRecomposeArgs.source[i5++];
/*      */                 }
/* 1473 */                 i3--;
/*      */               } 
/* 1475 */             } else if (k != 0) {
/* 1476 */               bool = true;
/* 1477 */               paramRecomposeArgs.source[m + 1] = (char)k;
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1483 */             if (i3 < paramRecomposeArgs.start) {
/* 1484 */               int i4 = i3;
/* 1485 */               int i5 = paramRecomposeArgs.start;
/* 1486 */               while (i5 < paramRecomposeArgs.limit) {
/* 1487 */                 paramRecomposeArgs.source[i4++] = paramRecomposeArgs.source[i5++];
/*      */               }
/* 1489 */               paramRecomposeArgs.start = i3;
/* 1490 */               paramRecomposeArgs.limit = i4;
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1496 */             if (paramRecomposeArgs.start == paramRecomposeArgs.limit) {
/* 1497 */               return (char)c;
/*      */             }
/*      */ 
/*      */             
/* 1501 */             if (i2 > 1) {
/* 1502 */               i = getCombiningIndexFromStarter((char)j, (char)k);
/*      */               continue;
/*      */             } 
/* 1505 */             m = -1;
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/* 1514 */       c = nextCombiningArgs.cc;
/* 1515 */       if (paramRecomposeArgs.start == paramRecomposeArgs.limit) {
/* 1516 */         return (char)c;
/*      */       }
/*      */ 
/*      */       
/* 1520 */       if (nextCombiningArgs.cc == '\000') {
/*      */         
/* 1522 */         if ((n & 0x40) != 0) {
/*      */           
/* 1524 */           if (nextCombiningArgs.c2 == '\000') {
/* 1525 */             bool = false;
/* 1526 */             m = paramRecomposeArgs.start - 1;
/*      */           } else {
/* 1528 */             bool = false;
/* 1529 */             m = paramRecomposeArgs.start - 2;
/*      */           } 
/* 1531 */           i = i1;
/*      */           continue;
/*      */         } 
/* 1534 */         m = -1; continue;
/*      */       } 
/* 1536 */       if ((paramInt & 0x2000) != 0)
/*      */       {
/* 1538 */         m = -1;
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
/*      */   private static int findPreviousStarter(char[] paramArrayOfchar, int paramInt1, int paramInt2, int paramInt3, int paramInt4, char paramChar) {
/* 1550 */     PrevArgs prevArgs = new PrevArgs();
/* 1551 */     prevArgs.src = paramArrayOfchar;
/* 1552 */     prevArgs.start = paramInt1;
/* 1553 */     prevArgs.current = paramInt2;
/*      */     
/* 1555 */     while (prevArgs.start < prevArgs.current) {
/* 1556 */       long l = getPrevNorm32(prevArgs, paramChar, paramInt3 | paramInt4);
/* 1557 */       if (isTrueStarter(l, paramInt3, paramInt4)) {
/*      */         break;
/*      */       }
/*      */     } 
/* 1561 */     return prevArgs.current;
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
/*      */   private static int findNextStarter(char[] paramArrayOfchar, int paramInt1, int paramInt2, int paramInt3, int paramInt4, char paramChar) {
/* 1576 */     int i = 0xFF00 | paramInt3;
/*      */     
/* 1578 */     DecomposeArgs decomposeArgs = new DecomposeArgs();
/*      */ 
/*      */     
/* 1581 */     while (paramInt1 != paramInt2) {
/*      */       boolean bool;
/*      */       
/* 1584 */       char c = paramArrayOfchar[paramInt1];
/* 1585 */       if (c < paramChar) {
/*      */         break;
/*      */       }
/*      */       
/* 1589 */       long l = getNorm32(c);
/* 1590 */       if ((l & i) == 0L) {
/*      */         break;
/*      */       }
/*      */       
/* 1594 */       if (isNorm32LeadSurrogate(l)) {
/*      */         
/* 1596 */         if (paramInt1 + 1 == paramInt2 || 
/* 1597 */           !UTF16.isTrailSurrogate(bool = paramArrayOfchar[paramInt1 + 1])) {
/*      */           break;
/*      */         }
/*      */         
/* 1601 */         l = getNorm32FromSurrogatePair(l, bool);
/*      */         
/* 1603 */         if ((l & i) == 0L) {
/*      */           break;
/*      */         }
/*      */       } else {
/* 1607 */         bool = false;
/*      */       } 
/*      */ 
/*      */       
/* 1611 */       if ((l & paramInt4) != 0L) {
/*      */ 
/*      */         
/* 1614 */         int j = decompose(l, paramInt4, decomposeArgs);
/*      */ 
/*      */ 
/*      */         
/* 1618 */         if (decomposeArgs.cc == 0 && (getNorm32(extraData, j, paramInt3) & paramInt3) == 0L) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */       
/* 1623 */       paramInt1 += !bool ? 1 : 2;
/*      */     } 
/*      */     
/* 1626 */     return paramInt1;
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class ComposePartArgs
/*      */   {
/*      */     int prevCC;
/*      */     
/*      */     int length;
/*      */ 
/*      */     
/*      */     private ComposePartArgs() {}
/*      */   }
/*      */ 
/*      */   
/*      */   private static char[] composePart(ComposePartArgs paramComposePartArgs, int paramInt1, char[] paramArrayOfchar, int paramInt2, int paramInt3, int paramInt4, UnicodeSet paramUnicodeSet) {
/* 1642 */     boolean bool = ((paramInt4 & 0x1000) != 0) ? true : false;
/*      */ 
/*      */     
/* 1645 */     int[] arrayOfInt = new int[1];
/* 1646 */     char[] arrayOfChar = new char[(paramInt3 - paramInt1) * 20];
/*      */     
/*      */     while (true) {
/* 1649 */       paramComposePartArgs.length = decompose(paramArrayOfchar, paramInt1, paramInt2, arrayOfChar, 0, arrayOfChar.length, bool, arrayOfInt, paramUnicodeSet);
/*      */ 
/*      */       
/* 1652 */       if (paramComposePartArgs.length <= arrayOfChar.length) {
/*      */         break;
/*      */       }
/* 1655 */       arrayOfChar = new char[paramComposePartArgs.length];
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1660 */     int i = paramComposePartArgs.length;
/*      */     
/* 1662 */     if (paramComposePartArgs.length >= 2) {
/* 1663 */       RecomposeArgs recomposeArgs = new RecomposeArgs();
/* 1664 */       recomposeArgs.source = arrayOfChar;
/* 1665 */       recomposeArgs.start = 0;
/* 1666 */       recomposeArgs.limit = i;
/* 1667 */       paramComposePartArgs.prevCC = recompose(recomposeArgs, paramInt4, paramUnicodeSet);
/* 1668 */       i = recomposeArgs.limit;
/*      */     } 
/*      */ 
/*      */     
/* 1672 */     paramComposePartArgs.length = i;
/* 1673 */     return arrayOfChar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean composeHangul(char paramChar1, char paramChar2, long paramLong, char[] paramArrayOfchar1, int[] paramArrayOfint, int paramInt1, boolean paramBoolean, char[] paramArrayOfchar2, int paramInt2, UnicodeSet paramUnicodeSet) {
/* 1682 */     int i = paramArrayOfint[0];
/* 1683 */     if (isJamoVTNorm32JamoV(paramLong)) {
/*      */ 
/*      */       
/* 1686 */       paramChar1 = (char)(paramChar1 - 4352);
/* 1687 */       if (paramChar1 < '\023') {
/* 1688 */         paramChar2 = (char)(44032 + (paramChar1 * 21 + paramChar2 - 4449) * 28);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1693 */         if (i != paramInt1) {
/*      */ 
/*      */           
/* 1696 */           char c1 = paramArrayOfchar1[i]; char c2;
/* 1697 */           if ((c2 = (char)(c1 - 4519)) < '\034') {
/*      */             
/* 1699 */             i++;
/* 1700 */             paramChar2 = (char)(paramChar2 + c2);
/* 1701 */           } else if (paramBoolean) {
/*      */ 
/*      */             
/* 1704 */             paramLong = getNorm32(c1);
/* 1705 */             if (isNorm32Regular(paramLong) && (paramLong & 0x8L) != 0L) {
/*      */               
/* 1707 */               DecomposeArgs decomposeArgs = new DecomposeArgs();
/* 1708 */               int j = decompose(paramLong, 8, decomposeArgs);
/* 1709 */               if (decomposeArgs.length == 1 && (c2 = (char)(extraData[j] - 4519)) < '\034') {
/*      */ 
/*      */ 
/*      */                 
/* 1713 */                 i++;
/* 1714 */                 paramChar2 = (char)(paramChar2 + c2);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/* 1719 */         if (nx_contains(paramUnicodeSet, paramChar2)) {
/* 1720 */           if (!isHangulWithoutJamoT(paramChar2)) {
/* 1721 */             i--;
/*      */           }
/* 1723 */           return false;
/*      */         } 
/* 1725 */         paramArrayOfchar2[paramInt2] = paramChar2;
/* 1726 */         paramArrayOfint[0] = i;
/* 1727 */         return true;
/*      */       } 
/* 1729 */     } else if (isHangulWithoutJamoT(paramChar1)) {
/*      */ 
/*      */       
/* 1732 */       paramChar2 = (char)(paramChar1 + paramChar2 - 4519);
/* 1733 */       if (nx_contains(paramUnicodeSet, paramChar2)) {
/* 1734 */         return false;
/*      */       }
/* 1736 */       paramArrayOfchar2[paramInt2] = paramChar2;
/* 1737 */       paramArrayOfint[0] = i;
/* 1738 */       return true;
/*      */     } 
/* 1740 */     return false;
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
/*      */   public static int compose(char[] paramArrayOfchar1, int paramInt1, int paramInt2, char[] paramArrayOfchar2, int paramInt3, int paramInt4, int paramInt5, UnicodeSet paramUnicodeSet) {
/*      */     byte b;
/*      */     char c1;
/* 1758 */     int[] arrayOfInt = new int[1];
/* 1759 */     int n = paramInt3;
/* 1760 */     int i1 = paramInt1;
/*      */     
/* 1762 */     if ((paramInt5 & 0x1000) != 0) {
/* 1763 */       c1 = (char)indexes[7];
/* 1764 */       b = 34;
/*      */     } else {
/* 1766 */       c1 = (char)indexes[6];
/* 1767 */       b = 17;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1793 */     int i = i1;
/*      */     
/* 1795 */     int j = 0xFF00 | b;
/* 1796 */     int k = 0;
/* 1797 */     int m = 0;
/*      */ 
/*      */     
/* 1800 */     long l = 0L;
/* 1801 */     char c = Character.MIN_VALUE;
/*      */     
/*      */     while (true) {
/*      */       int i3;
/*      */       char c2;
/* 1806 */       int i4, i2 = i1;
/*      */       
/* 1808 */       while (i1 != paramInt2 && ((c = paramArrayOfchar1[i1]) < c1 || ((
/* 1809 */         l = getNorm32(c)) & j) == 0L)) {
/* 1810 */         m = 0;
/* 1811 */         i1++;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1816 */       if (i1 != i2) {
/* 1817 */         i3 = i1 - i2;
/* 1818 */         if (n + i3 <= paramInt4) {
/* 1819 */           System.arraycopy(paramArrayOfchar1, i2, paramArrayOfchar2, n, i3);
/*      */         }
/* 1821 */         n += i3;
/* 1822 */         k = n;
/*      */ 
/*      */ 
/*      */         
/* 1826 */         i = i1 - 1;
/* 1827 */         if (UTF16.isTrailSurrogate(paramArrayOfchar1[i]) && i2 < i && 
/*      */           
/* 1829 */           UTF16.isLeadSurrogate(paramArrayOfchar1[i - 1])) {
/* 1830 */           i--;
/*      */         }
/*      */         
/* 1833 */         i2 = i1;
/*      */       } 
/*      */ 
/*      */       
/* 1837 */       if (i1 == paramInt2) {
/*      */         break;
/*      */       }
/*      */ 
/*      */       
/* 1842 */       i1++;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1874 */       if (isNorm32HangulOrJamo(l)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1881 */         m = i4 = 0;
/* 1882 */         k = n;
/* 1883 */         arrayOfInt[0] = i1;
/* 1884 */         if (n > 0)
/*      */         {
/* 1886 */           if (composeHangul(paramArrayOfchar1[i2 - 1], c, l, paramArrayOfchar1, arrayOfInt, paramInt2, ((paramInt5 & 0x1000) != 0), paramArrayOfchar2, (n <= paramInt4) ? (n - 1) : 0, paramUnicodeSet)) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1891 */             i1 = arrayOfInt[0];
/* 1892 */             i = i1;
/*      */             continue;
/*      */           } 
/*      */         }
/* 1896 */         i1 = arrayOfInt[0];
/*      */ 
/*      */ 
/*      */         
/* 1900 */         c2 = Character.MIN_VALUE;
/* 1901 */         i3 = 1;
/* 1902 */         i = i2;
/*      */       } else {
/* 1904 */         if (isNorm32Regular(l)) {
/* 1905 */           c2 = Character.MIN_VALUE;
/* 1906 */           i3 = 1;
/*      */         
/*      */         }
/* 1909 */         else if (i1 != paramInt2 && 
/* 1910 */           UTF16.isTrailSurrogate(c2 = paramArrayOfchar1[i1])) {
/* 1911 */           i1++;
/* 1912 */           i3 = 2;
/* 1913 */           l = getNorm32FromSurrogatePair(l, c2);
/*      */         } else {
/*      */           
/* 1916 */           c2 = Character.MIN_VALUE;
/* 1917 */           i3 = 1;
/* 1918 */           l = 0L;
/*      */         } 
/*      */         
/* 1921 */         ComposePartArgs composePartArgs = new ComposePartArgs();
/*      */ 
/*      */         
/* 1924 */         if (nx_contains(paramUnicodeSet, c, c2)) {
/*      */           
/* 1926 */           i4 = 0;
/* 1927 */         } else if ((l & b) == 0L) {
/* 1928 */           i4 = (int)(0xFFL & l >> 8L);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1945 */           int i5 = b << 2 & 0xF;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1951 */           if (isTrueStarter(l, 0xFF00 | b, i5)) {
/* 1952 */             i = i2;
/*      */           } else {
/*      */             
/* 1955 */             n -= i2 - i;
/*      */           } 
/*      */ 
/*      */           
/* 1959 */           i1 = findNextStarter(paramArrayOfchar1, i1, paramInt2, b, i5, c1);
/*      */ 
/*      */           
/* 1962 */           composePartArgs.prevCC = m;
/*      */           
/* 1964 */           composePartArgs.length = i3;
/* 1965 */           char[] arrayOfChar = composePart(composePartArgs, i, paramArrayOfchar1, i1, paramInt2, paramInt5, paramUnicodeSet);
/*      */           
/* 1967 */           if (arrayOfChar == null) {
/*      */             break;
/*      */           }
/*      */ 
/*      */           
/* 1972 */           m = composePartArgs.prevCC;
/* 1973 */           i3 = composePartArgs.length;
/*      */ 
/*      */ 
/*      */           
/* 1977 */           if (n + composePartArgs.length <= paramInt4) {
/* 1978 */             byte b1 = 0;
/* 1979 */             while (b1 < composePartArgs.length) {
/* 1980 */               paramArrayOfchar2[n++] = arrayOfChar[b1++];
/* 1981 */               i3--;
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/* 1986 */             n += i3;
/*      */           } 
/*      */           
/* 1989 */           i = i1;
/*      */           
/*      */           continue;
/*      */         } 
/*      */       } 
/*      */       
/* 1995 */       if (n + i3 <= paramInt4) {
/* 1996 */         if (i4 != 0 && i4 < m) {
/*      */ 
/*      */           
/* 1999 */           int i5 = n;
/* 2000 */           n += i3;
/* 2001 */           m = insertOrdered(paramArrayOfchar2, k, i5, n, c, c2, i4);
/*      */           
/*      */           continue;
/*      */         } 
/* 2005 */         paramArrayOfchar2[n++] = c;
/* 2006 */         if (c2 != '\000') {
/* 2007 */           paramArrayOfchar2[n++] = c2;
/*      */         }
/* 2009 */         m = i4;
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 2014 */       n += i3;
/* 2015 */       m = i4;
/*      */     } 
/*      */ 
/*      */     
/* 2019 */     return n - paramInt3;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getCombiningClass(int paramInt) {
/* 2024 */     long l = getNorm32(paramInt);
/* 2025 */     return (int)(l >> 8L & 0xFFL);
/*      */   }
/*      */   
/*      */   public static boolean isFullCompositionExclusion(int paramInt) {
/* 2029 */     if (isFormatVersion_2_1) {
/* 2030 */       char c = AuxTrieImpl.auxTrie.getCodePointValue(paramInt);
/* 2031 */       return ((c & 0x400) != 0);
/*      */     } 
/* 2033 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isCanonSafeStart(int paramInt) {
/* 2038 */     if (isFormatVersion_2_1) {
/* 2039 */       char c = AuxTrieImpl.auxTrie.getCodePointValue(paramInt);
/* 2040 */       return ((c & 0x800) == 0);
/*      */     } 
/* 2042 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNFSkippable(int paramInt, NormalizerBase.Mode paramMode, long paramLong) {
/* 2049 */     paramLong &= 0xFFFFFFFFL;
/*      */ 
/*      */ 
/*      */     
/* 2053 */     long l = getNorm32(paramInt);
/*      */     
/* 2055 */     if ((l & paramLong) != 0L) {
/* 2056 */       return false;
/*      */     }
/*      */     
/* 2059 */     if (paramMode == NormalizerBase.NFD || paramMode == NormalizerBase.NFKD || paramMode == NormalizerBase.NONE) {
/* 2060 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2065 */     if ((l & 0x4L) == 0L) {
/* 2066 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 2070 */     if (isNorm32HangulOrJamo(l))
/*      */     {
/* 2072 */       return !isHangulWithoutJamoT((char)paramInt);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2077 */     if (!isFormatVersion_2_2) {
/* 2078 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2082 */     char c = AuxTrieImpl.auxTrie.getCodePointValue(paramInt);
/* 2083 */     return ((c & 0x1000L) == 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static UnicodeSet addPropertyStarts(UnicodeSet paramUnicodeSet) {
/* 2093 */     TrieIterator trieIterator1 = new TrieIterator(NormTrieImpl.normTrie);
/* 2094 */     RangeValueIterator.Element element1 = new RangeValueIterator.Element();
/*      */     
/* 2096 */     while (trieIterator1.next(element1)) {
/* 2097 */       paramUnicodeSet.add(element1.start);
/*      */     }
/*      */ 
/*      */     
/* 2101 */     TrieIterator trieIterator2 = new TrieIterator(FCDTrieImpl.fcdTrie);
/* 2102 */     RangeValueIterator.Element element2 = new RangeValueIterator.Element();
/*      */     
/* 2104 */     while (trieIterator2.next(element2)) {
/* 2105 */       paramUnicodeSet.add(element2.start);
/*      */     }
/*      */     
/* 2108 */     if (isFormatVersion_2_1) {
/*      */       
/* 2110 */       TrieIterator trieIterator = new TrieIterator(AuxTrieImpl.auxTrie);
/* 2111 */       RangeValueIterator.Element element = new RangeValueIterator.Element();
/* 2112 */       while (trieIterator.next(element)) {
/* 2113 */         paramUnicodeSet.add(element.start);
/*      */       }
/*      */     } 
/*      */     
/* 2117 */     for (char c = '가'; c < '힤'; c += '\034') {
/* 2118 */       paramUnicodeSet.add(c);
/* 2119 */       paramUnicodeSet.add(c + 1);
/*      */     } 
/* 2121 */     paramUnicodeSet.add(55204);
/* 2122 */     return paramUnicodeSet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int quickCheck(int paramInt1, int paramInt2) {
/* 2133 */     int[] arrayOfInt = { 0, 0, 4, 8, 17, 34 };
/*      */ 
/*      */ 
/*      */     
/* 2137 */     int i = (int)getNorm32(paramInt1) & arrayOfInt[paramInt2];
/*      */     
/* 2139 */     if (i == 0)
/* 2140 */       return 1; 
/* 2141 */     if ((i & 0xF) != 0) {
/* 2142 */       return 0;
/*      */     }
/* 2144 */     return 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int strCompare(char[] paramArrayOfchar1, int paramInt1, int paramInt2, char[] paramArrayOfchar2, int paramInt3, int paramInt4, boolean paramBoolean) {
/*      */     char c1, c2;
/*      */     boolean bool;
/* 2157 */     int i = paramInt1;
/* 2158 */     int j = paramInt3;
/*      */ 
/*      */ 
/*      */     
/* 2162 */     int n = paramInt2 - paramInt1;
/* 2163 */     int i1 = paramInt4 - paramInt3;
/*      */ 
/*      */ 
/*      */     
/* 2167 */     if (n < i1) {
/* 2168 */       bool = true;
/* 2169 */       k = i + n;
/* 2170 */     } else if (n == i1) {
/* 2171 */       bool = false;
/* 2172 */       k = i + n;
/*      */     } else {
/* 2174 */       bool = true;
/* 2175 */       k = i + i1;
/*      */     } 
/*      */     
/* 2178 */     if (paramArrayOfchar1 == paramArrayOfchar2) {
/* 2179 */       return bool;
/*      */     }
/*      */ 
/*      */     
/*      */     while (true) {
/* 2184 */       if (paramInt1 == k) {
/* 2185 */         return bool;
/*      */       }
/*      */       
/* 2188 */       c1 = paramArrayOfchar1[paramInt1];
/* 2189 */       c2 = paramArrayOfchar2[paramInt3];
/* 2190 */       if (c1 != c2) {
/*      */         break;
/*      */       }
/* 2193 */       paramInt1++;
/* 2194 */       paramInt3++;
/*      */     } 
/*      */ 
/*      */     
/* 2198 */     int k = i + n;
/* 2199 */     int m = j + i1;
/*      */ 
/*      */ 
/*      */     
/* 2203 */     if (c1 >= '?' && c2 >= '?' && paramBoolean) {
/*      */ 
/*      */       
/* 2206 */       if ((c1 > '?' || paramInt1 + 1 == k || 
/*      */         
/* 2208 */         !UTF16.isTrailSurrogate(paramArrayOfchar1[paramInt1 + 1])) && (
/*      */         
/* 2210 */         !UTF16.isTrailSurrogate(c1) || i == paramInt1 || 
/* 2211 */         !UTF16.isLeadSurrogate(paramArrayOfchar1[paramInt1 - 1])))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2217 */         c1 = (char)(c1 - 10240);
/*      */       }
/*      */       
/* 2220 */       if ((c2 > '?' || paramInt3 + 1 == m || 
/*      */         
/* 2222 */         !UTF16.isTrailSurrogate(paramArrayOfchar2[paramInt3 + 1])) && (
/*      */         
/* 2224 */         !UTF16.isTrailSurrogate(c2) || j == paramInt3 || 
/* 2225 */         !UTF16.isLeadSurrogate(paramArrayOfchar2[paramInt3 - 1])))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2231 */         c2 = (char)(c2 - 10240);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 2236 */     return c1 - c2;
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
/* 2284 */   private static final UnicodeSet[] nxCache = new UnicodeSet[256];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int NX_HANGUL = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int NX_CJK_COMPAT = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int BEFORE_PRI_29 = 256;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int OPTIONS_COMPAT = 4096;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int OPTIONS_COMPOSE_CONTIGUOUS = 8192;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int WITHOUT_CORRIGENDUM4_CORRECTIONS = 262144;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final synchronized UnicodeSet internalGetNXHangul() {
/* 2336 */     if (nxCache[1] == null) {
/* 2337 */       nxCache[1] = new UnicodeSet(44032, 55203);
/*      */     }
/* 2339 */     return nxCache[1];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final synchronized UnicodeSet internalGetNXCJKCompat() {
/* 2345 */     if (nxCache[2] == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2350 */       UnicodeSet unicodeSet1 = new UnicodeSet("[:Ideographic:]");
/*      */ 
/*      */       
/* 2353 */       UnicodeSet unicodeSet2 = new UnicodeSet();
/*      */ 
/*      */       
/* 2356 */       UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(unicodeSet1);
/*      */ 
/*      */ 
/*      */       
/* 2360 */       while (unicodeSetIterator.nextRange() && unicodeSetIterator.codepoint != UnicodeSetIterator.IS_STRING) {
/* 2361 */         int i = unicodeSetIterator.codepoint;
/* 2362 */         int j = unicodeSetIterator.codepointEnd;
/* 2363 */         while (i <= j) {
/* 2364 */           long l = getNorm32(i);
/* 2365 */           if ((l & 0x4L) > 0L) {
/* 2366 */             unicodeSet2.add(i);
/*      */           }
/* 2368 */           i++;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2373 */       nxCache[2] = unicodeSet2;
/*      */     } 
/*      */ 
/*      */     
/* 2377 */     return nxCache[2];
/*      */   }
/*      */   
/*      */   private static final synchronized UnicodeSet internalGetNXUnicode(int paramInt) {
/* 2381 */     paramInt &= 0xE0;
/* 2382 */     if (paramInt == 0) {
/* 2383 */       return null;
/*      */     }
/*      */     
/* 2386 */     if (nxCache[paramInt] == null) {
/*      */       
/* 2388 */       UnicodeSet unicodeSet = new UnicodeSet();
/*      */       
/* 2390 */       switch (paramInt) {
/*      */         case 32:
/* 2392 */           unicodeSet.applyPattern("[:^Age=3.2:]");
/*      */           break;
/*      */         default:
/* 2395 */           return null;
/*      */       } 
/*      */       
/* 2398 */       nxCache[paramInt] = unicodeSet;
/*      */     } 
/*      */     
/* 2401 */     return nxCache[paramInt];
/*      */   }
/*      */ 
/*      */   
/*      */   private static final synchronized UnicodeSet internalGetNX(int paramInt) {
/* 2406 */     paramInt &= 0xFF;
/*      */     
/* 2408 */     if (nxCache[paramInt] == null) {
/*      */       
/* 2410 */       if (paramInt == 1) {
/* 2411 */         return internalGetNXHangul();
/*      */       }
/* 2413 */       if (paramInt == 2) {
/* 2414 */         return internalGetNXCJKCompat();
/*      */       }
/* 2416 */       if ((paramInt & 0xE0) != 0 && (paramInt & 0x1F) == 0) {
/* 2417 */         return internalGetNXUnicode(paramInt);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2424 */       UnicodeSet unicodeSet1 = new UnicodeSet();
/*      */       
/*      */       UnicodeSet unicodeSet2;
/* 2427 */       if ((paramInt & 0x1) != 0 && null != (unicodeSet2 = internalGetNXHangul())) {
/* 2428 */         unicodeSet1.addAll(unicodeSet2);
/*      */       }
/* 2430 */       if ((paramInt & 0x2) != 0 && null != (unicodeSet2 = internalGetNXCJKCompat())) {
/* 2431 */         unicodeSet1.addAll(unicodeSet2);
/*      */       }
/* 2433 */       if ((paramInt & 0xE0) != 0 && null != (unicodeSet2 = internalGetNXUnicode(paramInt))) {
/* 2434 */         unicodeSet1.addAll(unicodeSet2);
/*      */       }
/*      */       
/* 2437 */       nxCache[paramInt] = unicodeSet1;
/*      */     } 
/* 2439 */     return nxCache[paramInt];
/*      */   }
/*      */   
/*      */   public static final UnicodeSet getNX(int paramInt) {
/* 2443 */     if ((paramInt &= 0xFF) == 0)
/*      */     {
/* 2445 */       return null;
/*      */     }
/* 2447 */     return internalGetNX(paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   private static final boolean nx_contains(UnicodeSet paramUnicodeSet, int paramInt) {
/* 2452 */     return (paramUnicodeSet != null && paramUnicodeSet.contains(paramInt));
/*      */   }
/*      */   
/*      */   private static final boolean nx_contains(UnicodeSet paramUnicodeSet, char paramChar1, char paramChar2) {
/* 2456 */     return (paramUnicodeSet != null && paramUnicodeSet.contains((paramChar2 == '\000') ? paramChar1 : UCharacterProperty.getRawSupplementary(paramChar1, paramChar2)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getDecompose(int[] paramArrayOfint, String[] paramArrayOfString) {
/* 2467 */     DecomposeArgs decomposeArgs = new DecomposeArgs();
/* 2468 */     boolean bool = false;
/* 2469 */     long l = 0L;
/* 2470 */     int i = -1;
/* 2471 */     int j = 0;
/* 2472 */     byte b = 0;
/*      */     
/* 2474 */     while (++i < 195102) {
/*      */ 
/*      */       
/* 2477 */       if (i == 12543) {
/* 2478 */         i = 63744;
/* 2479 */       } else if (i == 65536) {
/* 2480 */         i = 119134;
/* 2481 */       } else if (i == 119233) {
/* 2482 */         i = 194560;
/*      */       } 
/* 2484 */       l = getNorm32(i);
/* 2485 */       if ((l & 0x4L) != 0L && b < paramArrayOfint.length) {
/* 2486 */         paramArrayOfint[b] = i;
/* 2487 */         j = decompose(l, decomposeArgs);
/* 2488 */         paramArrayOfString[b++] = new String(extraData, j, decomposeArgs.length);
/*      */       } 
/*      */     } 
/* 2491 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean needSingleQuotation(char paramChar) {
/* 2498 */     return ((paramChar >= '\t' && paramChar <= '\r') || (paramChar >= ' ' && paramChar <= '/') || (paramChar >= ':' && paramChar <= '@') || (paramChar >= '[' && paramChar <= '`') || (paramChar >= '{' && paramChar <= '~'));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String canonicalDecomposeWithSingleQuotation(String paramString) {
/* 2506 */     char[] arrayOfChar1 = paramString.toCharArray();
/* 2507 */     int i = 0;
/* 2508 */     int j = arrayOfChar1.length;
/* 2509 */     char[] arrayOfChar2 = new char[arrayOfChar1.length * 3];
/* 2510 */     int k = 0;
/* 2511 */     int m = arrayOfChar2.length;
/*      */     
/* 2513 */     char[] arrayOfChar3 = new char[3];
/*      */ 
/*      */ 
/*      */     
/* 2517 */     byte b = 4;
/*      */ 
/*      */     
/* 2520 */     char c1 = (char)indexes[8];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2527 */     int n = 0xFF00 | b;
/* 2528 */     int i1 = 0;
/* 2529 */     int i3 = 0;
/* 2530 */     long l = 0L;
/* 2531 */     char c = Character.MIN_VALUE;
/* 2532 */     int i5 = 0;
/*      */     
/* 2534 */     int i4 = -1, i2 = i4; while (true) {
/*      */       int i7; boolean bool; char[] arrayOfChar;
/* 2536 */       int i6 = i;
/*      */       
/* 2538 */       while (i != j && ((c = arrayOfChar1[i]) < c1 || ((
/*      */         
/* 2540 */         l = getNorm32(c)) & n) == 0L || (c >= '가' && c <= '힣'))) {
/*      */ 
/*      */         
/* 2543 */         i3 = 0;
/* 2544 */         i++;
/*      */       } 
/*      */ 
/*      */       
/* 2548 */       if (i != i6) {
/* 2549 */         i7 = i - i6;
/* 2550 */         if (k + i7 <= m) {
/* 2551 */           System.arraycopy(arrayOfChar1, i6, arrayOfChar2, k, i7);
/*      */         }
/*      */         
/* 2554 */         k += i7;
/* 2555 */         i1 = k;
/*      */       } 
/*      */ 
/*      */       
/* 2559 */       if (i == j) {
/*      */         break;
/*      */       }
/*      */       
/* 2563 */       i++;
/*      */       
/* 2565 */       if (isNorm32Regular(l)) {
/* 2566 */         bool = false;
/* 2567 */         i7 = 1;
/*      */       
/*      */       }
/* 2570 */       else if (i != j && 
/* 2571 */         Character.isLowSurrogate(bool = arrayOfChar1[i])) {
/* 2572 */         i++;
/* 2573 */         i7 = 2;
/* 2574 */         l = getNorm32FromSurrogatePair(l, bool);
/*      */       } else {
/* 2576 */         bool = false;
/* 2577 */         i7 = 1;
/* 2578 */         l = 0L;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2583 */       if ((l & b) == 0L) {
/*      */         
/* 2585 */         i2 = i4 = (int)(0xFFL & l >> 8L);
/* 2586 */         arrayOfChar = null;
/* 2587 */         i5 = -1;
/*      */       } else {
/* 2589 */         DecomposeArgs decomposeArgs = new DecomposeArgs();
/*      */ 
/*      */         
/* 2592 */         i5 = decompose(l, b, decomposeArgs);
/* 2593 */         arrayOfChar = extraData;
/* 2594 */         i7 = decomposeArgs.length;
/* 2595 */         i2 = decomposeArgs.cc;
/* 2596 */         i4 = decomposeArgs.trailCC;
/* 2597 */         if (i7 == 1) {
/*      */           
/* 2599 */           c = arrayOfChar[i5];
/* 2600 */           bool = false;
/* 2601 */           arrayOfChar = null;
/* 2602 */           i5 = -1;
/*      */         } 
/*      */       } 
/*      */       
/* 2606 */       if (k + i7 * 3 >= m) {
/*      */         
/* 2608 */         char[] arrayOfChar4 = new char[m * 2];
/* 2609 */         System.arraycopy(arrayOfChar2, 0, arrayOfChar4, 0, k);
/* 2610 */         arrayOfChar2 = arrayOfChar4;
/* 2611 */         m = arrayOfChar2.length;
/*      */       } 
/*      */ 
/*      */       
/* 2615 */       int i8 = k;
/* 2616 */       if (arrayOfChar == null) {
/*      */         
/* 2618 */         if (needSingleQuotation(c)) {
/*      */ 
/*      */           
/* 2621 */           arrayOfChar2[k++] = '\'';
/* 2622 */           arrayOfChar2[k++] = c;
/* 2623 */           arrayOfChar2[k++] = '\'';
/* 2624 */           i4 = 0;
/* 2625 */         } else if (i2 != 0 && i2 < i3) {
/*      */ 
/*      */           
/* 2628 */           k += i7;
/* 2629 */           i4 = insertOrdered(arrayOfChar2, i1, i8, k, c, bool, i2);
/*      */         }
/*      */         else {
/*      */           
/* 2633 */           arrayOfChar2[k++] = c;
/* 2634 */           if (bool) {
/* 2635 */             arrayOfChar2[k++] = bool;
/*      */           
/*      */           }
/*      */         }
/*      */       
/*      */       }
/* 2641 */       else if (needSingleQuotation(arrayOfChar[i5])) {
/* 2642 */         arrayOfChar2[k++] = '\'';
/* 2643 */         arrayOfChar2[k++] = arrayOfChar[i5++];
/* 2644 */         arrayOfChar2[k++] = '\'';
/* 2645 */         i7--;
/*      */         do {
/* 2647 */           arrayOfChar2[k++] = arrayOfChar[i5++];
/* 2648 */         } while (--i7 > 0);
/*      */       }
/* 2650 */       else if (i2 != 0 && i2 < i3) {
/* 2651 */         k += i7;
/* 2652 */         i4 = mergeOrdered(arrayOfChar2, i1, i8, arrayOfChar, i5, i5 + i7);
/*      */       } else {
/*      */ 
/*      */         
/*      */         do {
/* 2657 */           arrayOfChar2[k++] = arrayOfChar[i5++];
/* 2658 */         } while (--i7 > 0);
/*      */       } 
/*      */ 
/*      */       
/* 2662 */       i3 = i4;
/* 2663 */       if (i3 == 0) {
/* 2664 */         i1 = k;
/*      */       }
/*      */     } 
/* 2667 */     return new String(arrayOfChar2, 0, k);
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
/* 2691 */   private static final char[][] corrigendum4MappingTable = new char[][] { { '?', '?' }, { '弳' }, { '䎫' }, { '窮' }, { '䵗' } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String convert(String paramString) {
/* 2703 */     if (paramString == null) {
/* 2704 */       return null;
/*      */     }
/*      */     
/* 2707 */     int i = -1;
/* 2708 */     StringBuffer stringBuffer = new StringBuffer();
/* 2709 */     UCharacterIterator uCharacterIterator = UCharacterIterator.getInstance(paramString);
/*      */     
/* 2711 */     while ((i = uCharacterIterator.nextCodePoint()) != -1) {
/* 2712 */       switch (i) {
/*      */         case 194664:
/* 2714 */           stringBuffer.append(corrigendum4MappingTable[0]);
/*      */           continue;
/*      */         case 194676:
/* 2717 */           stringBuffer.append(corrigendum4MappingTable[1]);
/*      */           continue;
/*      */         case 194847:
/* 2720 */           stringBuffer.append(corrigendum4MappingTable[2]);
/*      */           continue;
/*      */         case 194911:
/* 2723 */           stringBuffer.append(corrigendum4MappingTable[3]);
/*      */           continue;
/*      */         case 195007:
/* 2726 */           stringBuffer.append(corrigendum4MappingTable[4]);
/*      */           continue;
/*      */       } 
/* 2729 */       UTF16.append(stringBuffer, i);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2734 */     return stringBuffer.toString();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/normalizer/NormalizerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */