/*      */ package sun.text.bidi;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.text.AttributedCharacterIterator;
/*      */ import java.text.Bidi;
/*      */ import java.util.Arrays;
/*      */ import java.util.MissingResourceException;
/*      */ import sun.text.normalizer.UBiDiProps;
/*      */ import sun.text.normalizer.UCharacter;
/*      */ import sun.text.normalizer.UTF16;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BidiBase
/*      */ {
/*      */   public static final byte INTERNAL_LEVEL_DEFAULT_LTR = 126;
/*      */   public static final byte INTERNAL_LEVEL_DEFAULT_RTL = 127;
/*      */   public static final byte MAX_EXPLICIT_LEVEL = 61;
/*      */   public static final byte INTERNAL_LEVEL_OVERRIDE = -128;
/*      */   public static final int MAP_NOWHERE = -1;
/*      */   public static final byte MIXED = 2;
/*      */   public static final short DO_MIRRORING = 2;
/*      */   private static final short REORDER_DEFAULT = 0;
/*      */   private static final short REORDER_NUMBERS_SPECIAL = 1;
/*      */   private static final short REORDER_GROUP_NUMBERS_WITH_R = 2;
/*      */   private static final short REORDER_RUNS_ONLY = 3;
/*      */   private static final short REORDER_INVERSE_NUMBERS_AS_L = 4;
/*      */   private static final short REORDER_INVERSE_LIKE_DIRECT = 5;
/*      */   private static final short REORDER_INVERSE_FOR_NUMBERS_SPECIAL = 6;
/*      */   private static final short REORDER_LAST_LOGICAL_TO_VISUAL = 1;
/*      */   private static final int OPTION_INSERT_MARKS = 1;
/*      */   private static final int OPTION_REMOVE_CONTROLS = 2;
/*      */   private static final int OPTION_STREAMING = 4;
/*      */   private static final byte L = 0;
/*      */   private static final byte R = 1;
/*      */   private static final byte EN = 2;
/*      */   private static final byte ES = 3;
/*      */   private static final byte ET = 4;
/*      */   private static final byte AN = 5;
/*      */   private static final byte CS = 6;
/*      */   static final byte B = 7;
/*      */   private static final byte S = 8;
/*      */   private static final byte WS = 9;
/*      */   private static final byte ON = 10;
/*      */   private static final byte LRE = 11;
/*      */   private static final byte LRO = 12;
/*      */   private static final byte AL = 13;
/*      */   private static final byte RLE = 14;
/*      */   private static final byte RLO = 15;
/*      */   private static final byte PDF = 16;
/*      */   private static final byte NSM = 17;
/*      */   private static final byte BN = 18;
/*      */   private static final int MASK_R_AL = 8194;
/*      */   private static final char CR = '\r';
/*      */   private static final char LF = '\n';
/*      */   static final int LRM_BEFORE = 1;
/*      */   static final int LRM_AFTER = 2;
/*      */   static final int RLM_BEFORE = 4;
/*      */   static final int RLM_AFTER = 8;
/*      */   BidiBase paraBidi;
/*      */   final UBiDiProps bdp;
/*      */   char[] text;
/*      */   int originalLength;
/*      */   public int length;
/*      */   int resultLength;
/*      */   boolean mayAllocateText;
/*      */   boolean mayAllocateRuns;
/*      */   
/*      */   class Point
/*      */   {
/*      */     int pos;
/*      */     int flag;
/*      */   }
/*      */   
/*      */   class InsertPoints
/*      */   {
/*      */     int size;
/*      */     int confirmed;
/*  468 */     BidiBase.Point[] points = new BidiBase.Point[0];
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  825 */   byte[] dirPropsMemory = new byte[1];
/*  826 */   byte[] levelsMemory = new byte[1];
/*      */ 
/*      */   
/*      */   byte[] dirProps;
/*      */ 
/*      */   
/*      */   byte[] levels;
/*      */ 
/*      */   
/*      */   boolean orderParagraphsLTR;
/*      */ 
/*      */   
/*      */   byte paraLevel;
/*      */ 
/*      */   
/*      */   byte defaultParaLevel;
/*      */ 
/*      */   
/*      */   ImpTabPair impTabPair;
/*      */ 
/*      */   
/*      */   byte direction;
/*      */ 
/*      */   
/*      */   int flags;
/*      */ 
/*      */   
/*      */   int lastArabicPos;
/*      */   
/*      */   int trailingWSStart;
/*      */   
/*      */   int paraCount;
/*      */   
/*  859 */   int[] parasMemory = new int[1];
/*      */ 
/*      */   
/*      */   int[] paras;
/*      */   
/*  864 */   int[] simpleParas = new int[] { 0 };
/*      */   
/*      */   int runCount;
/*      */   
/*  868 */   BidiRun[] runsMemory = new BidiRun[0];
/*      */   
/*      */   BidiRun[] runs;
/*      */   
/*  872 */   BidiRun[] simpleRuns = new BidiRun[] { new BidiRun() };
/*      */ 
/*      */   
/*      */   int[] logicalToVisualRunsMap;
/*      */ 
/*      */   
/*      */   boolean isGoodLogicalToVisualRunsMap;
/*      */ 
/*      */   
/*  881 */   InsertPoints insertPoints = new InsertPoints();
/*      */ 
/*      */   
/*      */   int controlCount;
/*      */ 
/*      */   
/*      */   static final byte CONTEXT_RTL_SHIFT = 6;
/*      */   
/*      */   static final byte CONTEXT_RTL = 64;
/*      */ 
/*      */   
/*      */   static int DirPropFlag(byte paramByte) {
/*  893 */     return 1 << paramByte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static byte NoContextRTL(byte paramByte) {
/*  904 */     return (byte)(paramByte & 0xFFFFFFBF);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int DirPropFlagNC(byte paramByte) {
/*  912 */     return 1 << (paramByte & 0xFFFFFFBF);
/*      */   }
/*      */   
/*  915 */   static final int DirPropFlagMultiRuns = DirPropFlag((byte)31);
/*      */ 
/*      */   
/*  918 */   static final int[] DirPropFlagLR = new int[] { DirPropFlag((byte)0), DirPropFlag((byte)1) };
/*  919 */   static final int[] DirPropFlagE = new int[] { DirPropFlag((byte)11), DirPropFlag((byte)14) };
/*  920 */   static final int[] DirPropFlagO = new int[] { DirPropFlag((byte)12), DirPropFlag((byte)15) };
/*      */   
/*  922 */   static final int DirPropFlagLR(byte paramByte) { return DirPropFlagLR[paramByte & 0x1]; }
/*  923 */   static final int DirPropFlagE(byte paramByte) { return DirPropFlagE[paramByte & 0x1]; } static final int DirPropFlagO(byte paramByte) {
/*  924 */     return DirPropFlagO[paramByte & 0x1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  930 */   static final int MASK_LTR = DirPropFlag((byte)0) | DirPropFlag((byte)2) | DirPropFlag((byte)5) | DirPropFlag((byte)11) | DirPropFlag((byte)12);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  935 */   static final int MASK_RTL = DirPropFlag((byte)1) | DirPropFlag((byte)13) | DirPropFlag((byte)14) | DirPropFlag((byte)15);
/*      */ 
/*      */   
/*  938 */   private static final int MASK_LRX = DirPropFlag((byte)11) | DirPropFlag((byte)12);
/*  939 */   private static final int MASK_RLX = DirPropFlag((byte)14) | DirPropFlag((byte)15);
/*  940 */   private static final int MASK_EXPLICIT = MASK_LRX | MASK_RLX | DirPropFlag((byte)16);
/*  941 */   private static final int MASK_BN_EXPLICIT = DirPropFlag((byte)18) | MASK_EXPLICIT;
/*      */ 
/*      */   
/*  944 */   private static final int MASK_B_S = DirPropFlag((byte)7) | DirPropFlag((byte)8);
/*      */ 
/*      */   
/*  947 */   static final int MASK_WS = MASK_B_S | DirPropFlag((byte)9) | MASK_BN_EXPLICIT;
/*  948 */   private static final int MASK_N = DirPropFlag((byte)10) | MASK_WS;
/*      */ 
/*      */   
/*  951 */   private static final int MASK_POSSIBLE_N = DirPropFlag((byte)6) | DirPropFlag((byte)3) | DirPropFlag((byte)4) | MASK_N;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  958 */   static final int MASK_EMBEDDING = DirPropFlag((byte)17) | MASK_POSSIBLE_N;
/*      */   
/*      */   private static final int IMPTABPROPS_COLUMNS = 14;
/*      */   
/*      */   private static final int IMPTABPROPS_RES = 13;
/*      */   
/*      */   private static byte GetLRFromLevel(byte paramByte) {
/*  965 */     return (byte)(paramByte & 0x1);
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean IsDefaultLevel(byte paramByte) {
/*  970 */     return ((paramByte & 0x7E) == 126);
/*      */   }
/*      */ 
/*      */   
/*      */   byte GetParaLevelAt(int paramInt) {
/*  975 */     return (this.defaultParaLevel != 0) ? (byte)(this.dirProps[paramInt] >> 6) : this.paraLevel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean IsBidiControlChar(int paramInt) {
/*  983 */     return ((paramInt & 0xFFFFFFFC) == 8204 || (paramInt >= 8234 && paramInt <= 8238));
/*      */   }
/*      */ 
/*      */   
/*      */   public void verifyValidPara() {
/*  988 */     if (this != this.paraBidi) {
/*  989 */       throw new IllegalStateException("");
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void verifyValidParaOrLine() {
/*  995 */     BidiBase bidiBase = this.paraBidi;
/*      */     
/*  997 */     if (this == bidiBase) {
/*      */       return;
/*      */     }
/*      */     
/* 1001 */     if (bidiBase == null || bidiBase != bidiBase.paraBidi) {
/* 1002 */       throw new IllegalStateException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void verifyRange(int paramInt1, int paramInt2, int paramInt3) {
/* 1008 */     if (paramInt1 < paramInt2 || paramInt1 >= paramInt3) {
/* 1009 */       throw new IllegalArgumentException("Value " + paramInt1 + " is out of range " + paramInt2 + " to " + paramInt3);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void verifyIndex(int paramInt1, int paramInt2, int paramInt3) {
/* 1016 */     if (paramInt1 < paramInt2 || paramInt1 >= paramInt3) {
/* 1017 */       throw new ArrayIndexOutOfBoundsException("Index " + paramInt1 + " is out of range " + paramInt2 + " to " + paramInt3);
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
/*      */   public BidiBase(int paramInt1, int paramInt2) {
/* 1055 */     if (paramInt1 < 0 || paramInt2 < 0) {
/* 1056 */       throw new IllegalArgumentException();
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
/*      */     try {
/* 1078 */       this.bdp = UBiDiProps.getSingleton();
/*      */     }
/* 1080 */     catch (IOException iOException) {
/* 1081 */       throw new MissingResourceException(iOException.getMessage(), "(BidiProps)", "");
/*      */     } 
/*      */ 
/*      */     
/* 1085 */     if (paramInt1 > 0) {
/* 1086 */       getInitialDirPropsMemory(paramInt1);
/* 1087 */       getInitialLevelsMemory(paramInt1);
/*      */     } else {
/* 1089 */       this.mayAllocateText = true;
/*      */     } 
/*      */     
/* 1092 */     if (paramInt2 > 0) {
/*      */       
/* 1094 */       if (paramInt2 > 1) {
/* 1095 */         getInitialRunsMemory(paramInt2);
/*      */       }
/*      */     } else {
/* 1098 */       this.mayAllocateRuns = true;
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
/*      */   private Object getMemory(String paramString, Object paramObject, Class<?> paramClass, boolean paramBoolean, int paramInt) {
/* 1112 */     int i = Array.getLength(paramObject);
/*      */ 
/*      */     
/* 1115 */     if (paramInt == i) {
/* 1116 */       return paramObject;
/*      */     }
/* 1118 */     if (!paramBoolean) {
/*      */       
/* 1120 */       if (paramInt <= i) {
/* 1121 */         return paramObject;
/*      */       }
/* 1123 */       throw new OutOfMemoryError("Failed to allocate memory for " + paramString);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1130 */       return Array.newInstance(paramClass, paramInt);
/* 1131 */     } catch (Exception exception) {
/* 1132 */       throw new OutOfMemoryError("Failed to allocate memory for " + paramString);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getDirPropsMemory(boolean paramBoolean, int paramInt) {
/* 1140 */     Object object = getMemory("DirProps", this.dirPropsMemory, byte.class, paramBoolean, paramInt);
/* 1141 */     this.dirPropsMemory = (byte[])object;
/*      */   }
/*      */ 
/*      */   
/*      */   void getDirPropsMemory(int paramInt) {
/* 1146 */     getDirPropsMemory(this.mayAllocateText, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   private void getLevelsMemory(boolean paramBoolean, int paramInt) {
/* 1151 */     Object object = getMemory("Levels", this.levelsMemory, byte.class, paramBoolean, paramInt);
/* 1152 */     this.levelsMemory = (byte[])object;
/*      */   }
/*      */ 
/*      */   
/*      */   void getLevelsMemory(int paramInt) {
/* 1157 */     getLevelsMemory(this.mayAllocateText, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   private void getRunsMemory(boolean paramBoolean, int paramInt) {
/* 1162 */     Object object = getMemory("Runs", this.runsMemory, BidiRun.class, paramBoolean, paramInt);
/* 1163 */     this.runsMemory = (BidiRun[])object;
/*      */   }
/*      */ 
/*      */   
/*      */   void getRunsMemory(int paramInt) {
/* 1168 */     getRunsMemory(this.mayAllocateRuns, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void getInitialDirPropsMemory(int paramInt) {
/* 1174 */     getDirPropsMemory(true, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   private void getInitialLevelsMemory(int paramInt) {
/* 1179 */     getLevelsMemory(true, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   private void getInitialParasMemory(int paramInt) {
/* 1184 */     Object object = getMemory("Paras", this.parasMemory, int.class, true, paramInt);
/* 1185 */     this.parasMemory = (int[])object;
/*      */   }
/*      */ 
/*      */   
/*      */   private void getInitialRunsMemory(int paramInt) {
/* 1190 */     getRunsMemory(true, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   private void getDirProps() {
/*      */     byte b2;
/*      */     byte b;
/* 1197 */     int i = 0;
/* 1198 */     this.flags = 0;
/*      */ 
/*      */     
/* 1201 */     byte b1 = 0;
/* 1202 */     boolean bool = IsDefaultLevel(this.paraLevel);
/*      */ 
/*      */     
/* 1205 */     this.lastArabicPos = -1;
/* 1206 */     this.controlCount = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1213 */     int j = 0;
/*      */ 
/*      */     
/* 1216 */     byte b3 = 0;
/* 1217 */     int k = 0;
/*      */     
/* 1219 */     if (bool) {
/* 1220 */       b1 = ((this.paraLevel & 0x1) != 0) ? 64 : 0;
/* 1221 */       b = b1;
/* 1222 */       b3 = b1;
/* 1223 */       b2 = 1;
/*      */     } else {
/* 1225 */       b2 = 0;
/* 1226 */       b = 0;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1235 */     for (i = 0; i < this.originalLength; ) {
/* 1236 */       int m = i;
/* 1237 */       int i1 = UTF16.charAt(this.text, 0, this.originalLength, i);
/* 1238 */       i += Character.charCount(i1);
/* 1239 */       int n = i - 1;
/*      */       
/* 1241 */       byte b4 = (byte)this.bdp.getClass(i1);
/*      */       
/* 1243 */       this.flags |= DirPropFlag(b4);
/* 1244 */       this.dirProps[n] = (byte)(b4 | b);
/* 1245 */       if (n > m) {
/* 1246 */         this.flags |= DirPropFlag((byte)18);
/*      */         do {
/* 1248 */           this.dirProps[--n] = (byte)(0x12 | b);
/* 1249 */         } while (n > m);
/*      */       } 
/* 1251 */       if (b2 == 1) {
/* 1252 */         if (b4 == 0) {
/* 1253 */           b2 = 2;
/* 1254 */           if (b != 0) {
/* 1255 */             b = 0;
/* 1256 */             for (n = j; n < i; n++) {
/* 1257 */               this.dirProps[n] = (byte)(this.dirProps[n] & 0xFFFFFFBF);
/*      */             }
/*      */           } 
/*      */           continue;
/*      */         } 
/* 1262 */         if (b4 == 1 || b4 == 13) {
/* 1263 */           b2 = 2;
/* 1264 */           if (b == 0) {
/* 1265 */             b = 64;
/* 1266 */             for (n = j; n < i; n++) {
/* 1267 */               this.dirProps[n] = (byte)(this.dirProps[n] | 0x40);
/*      */             }
/*      */           } 
/*      */           continue;
/*      */         } 
/*      */       } 
/* 1273 */       if (b4 == 0) {
/* 1274 */         b3 = 0;
/* 1275 */         k = i; continue;
/*      */       } 
/* 1277 */       if (b4 == 1) {
/* 1278 */         b3 = 64; continue;
/*      */       } 
/* 1280 */       if (b4 == 13) {
/* 1281 */         b3 = 64;
/* 1282 */         this.lastArabicPos = i - 1; continue;
/*      */       } 
/* 1284 */       if (b4 == 7 && 
/* 1285 */         i < this.originalLength) {
/* 1286 */         if (i1 != 13 || this.text[i] != '\n') {
/* 1287 */           this.paraCount++;
/*      */         }
/* 1289 */         if (bool) {
/* 1290 */           b2 = 1;
/* 1291 */           j = i;
/* 1292 */           b = b1;
/* 1293 */           b3 = b1;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1298 */     if (bool) {
/* 1299 */       this.paraLevel = GetParaLevelAt(0);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1304 */     this.flags |= DirPropFlagLR(this.paraLevel);
/*      */     
/* 1306 */     if (this.orderParagraphsLTR && (this.flags & DirPropFlag((byte)7)) != 0) {
/* 1307 */       this.flags |= DirPropFlag((byte)0);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte directionFromFlags() {
/* 1316 */     if ((this.flags & MASK_RTL) == 0 && ((this.flags & 
/* 1317 */       DirPropFlag((byte)5)) == 0 || (this.flags & MASK_POSSIBLE_N) == 0))
/*      */     {
/* 1319 */       return 0; } 
/* 1320 */     if ((this.flags & MASK_LTR) == 0) {
/* 1321 */       return 1;
/*      */     }
/* 1323 */     return 2;
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
/*      */   private byte resolveExplicitLevels() {
/* 1380 */     byte b1 = 0;
/*      */     
/* 1382 */     byte b2 = GetParaLevelAt(0);
/*      */ 
/*      */     
/* 1385 */     byte b4 = 0;
/*      */ 
/*      */     
/* 1388 */     byte b3 = directionFromFlags();
/*      */ 
/*      */ 
/*      */     
/* 1392 */     if (b3 == 2 || this.paraCount != 1)
/*      */     {
/* 1394 */       if (this.paraCount == 1 && (this.flags & MASK_EXPLICIT) == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1400 */         for (b1 = 0; b1 < this.length; b1++) {
/* 1401 */           this.levels[b1] = b2;
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1408 */         byte b5 = b2;
/*      */         
/* 1410 */         byte b6 = 0;
/*      */         
/* 1412 */         byte[] arrayOfByte = new byte[61];
/* 1413 */         byte b7 = 0;
/* 1414 */         byte b8 = 0;
/*      */ 
/*      */         
/* 1417 */         this.flags = 0;
/*      */         
/* 1419 */         for (b1 = 0; b1 < this.length; b1++) {
/* 1420 */           byte b10, b9 = NoContextRTL(this.dirProps[b1]);
/* 1421 */           switch (b9) {
/*      */             
/*      */             case 11:
/*      */             case 12:
/* 1425 */               b10 = (byte)(b5 + 2 & 0x7E);
/* 1426 */               if (b10 <= 61) {
/* 1427 */                 arrayOfByte[b6] = b5;
/* 1428 */                 b6 = (byte)(b6 + 1);
/* 1429 */                 b5 = b10;
/* 1430 */                 if (b9 == 12) {
/* 1431 */                   b5 = (byte)(b5 | 0xFFFFFF80);
/*      */ 
/*      */                 
/*      */                 }
/*      */               
/*      */               }
/* 1437 */               else if ((b5 & Byte.MAX_VALUE) == 61) {
/* 1438 */                 b8++;
/*      */               } else {
/* 1440 */                 b7++;
/*      */               } 
/* 1442 */               this.flags |= DirPropFlag((byte)18);
/*      */               break;
/*      */             
/*      */             case 14:
/*      */             case 15:
/* 1447 */               b10 = (byte)((b5 & Byte.MAX_VALUE) + 1 | 0x1);
/* 1448 */               if (b10 <= 61) {
/* 1449 */                 arrayOfByte[b6] = b5;
/* 1450 */                 b6 = (byte)(b6 + 1);
/* 1451 */                 b5 = b10;
/* 1452 */                 if (b9 == 15) {
/* 1453 */                   b5 = (byte)(b5 | 0xFFFFFF80);
/*      */                 
/*      */                 }
/*      */               
/*      */               }
/*      */               else {
/*      */                 
/* 1460 */                 b8++;
/*      */               } 
/* 1462 */               this.flags |= DirPropFlag((byte)18);
/*      */               break;
/*      */ 
/*      */             
/*      */             case 16:
/* 1467 */               if (b8 > 0) {
/* 1468 */                 b8--;
/* 1469 */               } else if (b7 > 0 && (b5 & Byte.MAX_VALUE) != 61) {
/*      */                 
/* 1471 */                 b7--;
/* 1472 */               } else if (b6 > 0) {
/*      */                 
/* 1474 */                 b6 = (byte)(b6 - 1);
/* 1475 */                 b5 = arrayOfByte[b6];
/*      */               } 
/*      */               
/* 1478 */               this.flags |= DirPropFlag((byte)18);
/*      */               break;
/*      */             case 7:
/* 1481 */               b6 = 0;
/* 1482 */               b7 = 0;
/* 1483 */               b8 = 0;
/* 1484 */               b2 = GetParaLevelAt(b1);
/* 1485 */               if (b1 + 1 < this.length) {
/* 1486 */                 b5 = GetParaLevelAt(b1 + 1);
/* 1487 */                 if (this.text[b1] != '\r' || this.text[b1 + 1] != '\n') {
/* 1488 */                   this.paras[b4++] = b1 + 1;
/*      */                 }
/*      */               } 
/* 1491 */               this.flags |= DirPropFlag((byte)7);
/*      */               break;
/*      */ 
/*      */             
/*      */             case 18:
/* 1496 */               this.flags |= DirPropFlag((byte)18);
/*      */               break;
/*      */             
/*      */             default:
/* 1500 */               if (b2 != b5) {
/* 1501 */                 b2 = b5;
/* 1502 */                 if ((b2 & Byte.MIN_VALUE) != 0) {
/* 1503 */                   this.flags |= DirPropFlagO(b2) | DirPropFlagMultiRuns;
/*      */                 } else {
/* 1505 */                   this.flags |= DirPropFlagE(b2) | DirPropFlagMultiRuns;
/*      */                 } 
/*      */               } 
/* 1508 */               if ((b2 & Byte.MIN_VALUE) == 0) {
/* 1509 */                 this.flags |= DirPropFlag(b9);
/*      */               }
/*      */               break;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1518 */           this.levels[b1] = b2;
/*      */         } 
/* 1520 */         if ((this.flags & MASK_EMBEDDING) != 0) {
/* 1521 */           this.flags |= DirPropFlagLR(this.paraLevel);
/*      */         }
/* 1523 */         if (this.orderParagraphsLTR && (this.flags & DirPropFlag((byte)7)) != 0) {
/* 1524 */           this.flags |= DirPropFlag((byte)0);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1530 */         b3 = directionFromFlags();
/*      */       } 
/*      */     }
/* 1533 */     return b3;
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
/*      */   private byte checkExplicitLevels() {
/* 1549 */     this.flags = 0;
/*      */     
/* 1551 */     byte b2 = 0;
/*      */     
/* 1553 */     for (byte b1 = 0; b1 < this.length; b1++) {
/* 1554 */       if (this.levels[b1] == 0) {
/* 1555 */         this.levels[b1] = this.paraLevel;
/*      */       }
/* 1557 */       if (61 < (this.levels[b1] & Byte.MAX_VALUE)) {
/* 1558 */         if ((this.levels[b1] & Byte.MIN_VALUE) != 0) {
/* 1559 */           this.levels[b1] = (byte)(this.paraLevel | Byte.MIN_VALUE);
/*      */         } else {
/* 1561 */           this.levels[b1] = this.paraLevel;
/*      */         } 
/*      */       }
/* 1564 */       byte b4 = this.levels[b1];
/* 1565 */       byte b3 = NoContextRTL(this.dirProps[b1]);
/* 1566 */       if ((b4 & Byte.MIN_VALUE) != 0) {
/*      */         
/* 1568 */         b4 = (byte)(b4 & Byte.MAX_VALUE);
/* 1569 */         this.flags |= DirPropFlagO(b4);
/*      */       } else {
/*      */         
/* 1572 */         this.flags |= DirPropFlagE(b4) | DirPropFlag(b3);
/*      */       } 
/*      */       
/* 1575 */       if ((b4 < GetParaLevelAt(b1) && (0 != b4 || b3 != 7)) || 61 < b4)
/*      */       {
/*      */ 
/*      */         
/* 1579 */         throw new IllegalArgumentException("level " + b4 + " out of bounds at index " + b1);
/*      */       }
/*      */       
/* 1582 */       if (b3 == 7 && b1 + 1 < this.length && (
/* 1583 */         this.text[b1] != '\r' || this.text[b1 + 1] != '\n')) {
/* 1584 */         this.paras[b2++] = b1 + 1;
/*      */       }
/*      */     } 
/*      */     
/* 1588 */     if ((this.flags & MASK_EMBEDDING) != 0) {
/* 1589 */       this.flags |= DirPropFlagLR(this.paraLevel);
/*      */     }
/*      */ 
/*      */     
/* 1593 */     return directionFromFlags();
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
/*      */   private static short GetStateProps(short paramShort) {
/* 1615 */     return (short)(paramShort & 0x1F);
/*      */   }
/*      */   private static short GetActionProps(short paramShort) {
/* 1618 */     return (short)(paramShort >> 5);
/*      */   }
/*      */   
/* 1621 */   private static final short[] groupProp = new short[] { 0, 1, 2, 7, 8, 3, 9, 6, 5, 4, 4, 10, 10, 12, 10, 10, 10, 11, 10 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final short _L = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final short _R = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final short _EN = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final short _AN = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final short _ON = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final short _S = 5;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final short _B = 6;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1668 */   private static final short[][] impTabProps = new short[][] { { 1, 2, 4, 5, 7, 15, 17, 7, 9, 7, 0, 7, 3, 4 }, { 1, 34, 36, 37, 39, 47, 49, 39, 41, 39, 1, 1, 35, 0 }, { 33, 2, 36, 37, 39, 47, 49, 39, 41, 39, 2, 2, 35, 1 }, { 33, 34, 38, 38, 40, 48, 49, 40, 40, 40, 3, 3, 3, 1 }, { 33, 34, 4, 37, 39, 47, 49, 74, 11, 74, 4, 4, 35, 2 }, { 33, 34, 36, 5, 39, 47, 49, 39, 41, 76, 5, 5, 35, 3 }, { 33, 34, 6, 6, 40, 48, 49, 40, 40, 77, 6, 6, 35, 3 }, { 33, 34, 36, 37, 7, 47, 49, 7, 78, 7, 7, 7, 35, 4 }, { 33, 34, 38, 38, 8, 48, 49, 8, 8, 8, 8, 8, 35, 4 }, { 33, 34, 4, 37, 7, 47, 49, 7, 9, 7, 9, 9, 35, 4 }, { 97, 98, 4, 101, 135, 111, 113, 135, 142, 135, 10, 135, 99, 2 }, { 33, 34, 4, 37, 39, 47, 49, 39, 11, 39, 11, 11, 35, 2 }, { 97, 98, 100, 5, 135, 111, 113, 135, 142, 135, 12, 135, 99, 3 }, { 97, 98, 6, 6, 136, 112, 113, 136, 136, 136, 13, 136, 99, 3 }, { 33, 34, 132, 37, 7, 47, 49, 7, 14, 7, 14, 14, 35, 4 }, { 33, 34, 36, 37, 39, 15, 49, 39, 41, 39, 15, 39, 35, 5 }, { 33, 34, 38, 38, 40, 16, 49, 40, 40, 40, 16, 40, 35, 5 }, { 33, 34, 36, 37, 39, 47, 17, 39, 41, 39, 17, 39, 35, 6 } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int IMPTABLEVELS_COLUMNS = 8;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int IMPTABLEVELS_RES = 7;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static short GetState(byte paramByte) {
/* 1711 */     return (short)(paramByte & 0xF); } private static short GetAction(byte paramByte) {
/* 1712 */     return (short)(paramByte >> 4);
/*      */   }
/*      */   
/*      */   private static class ImpTabPair {
/*      */     byte[][][] imptab;
/*      */     short[][] impact;
/*      */     
/*      */     ImpTabPair(byte[][] param1ArrayOfbyte1, byte[][] param1ArrayOfbyte2, short[] param1ArrayOfshort1, short[] param1ArrayOfshort2) {
/* 1720 */       this.imptab = new byte[][][] { param1ArrayOfbyte1, param1ArrayOfbyte2 };
/* 1721 */       this.impact = new short[][] { param1ArrayOfshort1, param1ArrayOfshort2 };
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
/* 1761 */   private static final byte[][] impTabL_DEFAULT = new byte[][] { { 0, 1, 0, 2, 0, 0, 0, 0 }, { 0, 1, 3, 3, 20, 20, 0, 1 }, { 0, 1, 0, 2, 21, 21, 0, 2 }, { 0, 1, 3, 3, 20, 20, 0, 2 }, { 32, 1, 3, 3, 4, 4, 32, 1 }, { 32, 1, 32, 2, 5, 5, 32, 1 } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1775 */   private static final byte[][] impTabR_DEFAULT = new byte[][] { { 1, 0, 2, 2, 0, 0, 0, 0 }, { 1, 0, 1, 3, 20, 20, 0, 1 }, { 1, 0, 2, 2, 0, 0, 0, 1 }, { 1, 0, 1, 3, 5, 5, 0, 1 }, { 33, 0, 33, 3, 4, 4, 0, 0 }, { 1, 0, 1, 3, 5, 5, 0, 0 } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1789 */   private static final short[] impAct0 = new short[] { 0, 1, 2, 3, 4, 5, 6 };
/*      */   
/* 1791 */   private static final ImpTabPair impTab_DEFAULT = new ImpTabPair(impTabL_DEFAULT, impTabR_DEFAULT, impAct0, impAct0);
/*      */ 
/*      */   
/* 1794 */   private static final byte[][] impTabL_NUMBERS_SPECIAL = new byte[][] { { 0, 2, 1, 1, 0, 0, 0, 0 }, { 0, 2, 1, 1, 0, 0, 0, 2 }, { 0, 2, 4, 4, 19, 0, 0, 1 }, { 32, 2, 4, 4, 3, 3, 32, 1 }, { 0, 2, 4, 4, 19, 19, 0, 2 } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1805 */   private static final ImpTabPair impTab_NUMBERS_SPECIAL = new ImpTabPair(impTabL_NUMBERS_SPECIAL, impTabR_DEFAULT, impAct0, impAct0);
/*      */ 
/*      */   
/* 1808 */   private static final byte[][] impTabL_GROUP_NUMBERS_WITH_R = new byte[][] { { 0, 3, 17, 17, 0, 0, 0, 0 }, { 32, 3, 1, 1, 2, 32, 32, 2 }, { 32, 3, 1, 1, 2, 32, 32, 1 }, { 0, 3, 5, 5, 20, 0, 0, 1 }, { 32, 3, 5, 5, 4, 32, 32, 1 }, { 0, 3, 5, 5, 20, 0, 0, 2 } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1820 */   private static final byte[][] impTabR_GROUP_NUMBERS_WITH_R = new byte[][] { { 2, 0, 1, 1, 0, 0, 0, 0 }, { 2, 0, 1, 1, 0, 0, 0, 1 }, { 2, 0, 20, 20, 19, 0, 0, 1 }, { 34, 0, 4, 4, 3, 0, 0, 0 }, { 34, 0, 4, 4, 3, 0, 0, 1 } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1831 */   private static final ImpTabPair impTab_GROUP_NUMBERS_WITH_R = new ImpTabPair(impTabL_GROUP_NUMBERS_WITH_R, impTabR_GROUP_NUMBERS_WITH_R, impAct0, impAct0);
/*      */ 
/*      */ 
/*      */   
/* 1835 */   private static final byte[][] impTabL_INVERSE_NUMBERS_AS_L = new byte[][] { { 0, 1, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 20, 20, 0, 1 }, { 0, 1, 0, 0, 21, 21, 0, 2 }, { 0, 1, 0, 0, 20, 20, 0, 2 }, { 32, 1, 32, 32, 4, 4, 32, 1 }, { 32, 1, 32, 32, 5, 5, 32, 1 } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1847 */   private static final byte[][] impTabR_INVERSE_NUMBERS_AS_L = new byte[][] { { 1, 0, 1, 1, 0, 0, 0, 0 }, { 1, 0, 1, 1, 20, 20, 0, 1 }, { 1, 0, 1, 1, 0, 0, 0, 1 }, { 1, 0, 1, 1, 5, 5, 0, 1 }, { 33, 0, 33, 33, 4, 4, 0, 0 }, { 1, 0, 1, 1, 5, 5, 0, 0 } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1859 */   private static final ImpTabPair impTab_INVERSE_NUMBERS_AS_L = new ImpTabPair(impTabL_INVERSE_NUMBERS_AS_L, impTabR_INVERSE_NUMBERS_AS_L, impAct0, impAct0);
/*      */ 
/*      */ 
/*      */   
/* 1863 */   private static final byte[][] impTabR_INVERSE_LIKE_DIRECT = new byte[][] { { 1, 0, 2, 2, 0, 0, 0, 0 }, { 1, 0, 1, 2, 19, 19, 0, 1 }, { 1, 0, 2, 2, 0, 0, 0, 1 }, { 33, 48, 6, 4, 3, 3, 48, 0 }, { 33, 48, 6, 4, 5, 5, 48, 3 }, { 33, 48, 6, 4, 5, 5, 48, 2 }, { 33, 48, 6, 4, 3, 3, 48, 1 } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1876 */   private static final short[] impAct1 = new short[] { 0, 1, 11, 12 };
/* 1877 */   private static final ImpTabPair impTab_INVERSE_LIKE_DIRECT = new ImpTabPair(impTabL_DEFAULT, impTabR_INVERSE_LIKE_DIRECT, impAct0, impAct1);
/*      */ 
/*      */   
/* 1880 */   private static final byte[][] impTabL_INVERSE_LIKE_DIRECT_WITH_MARKS = new byte[][] { { 0, 99, 0, 1, 0, 0, 0, 0 }, { 0, 99, 0, 1, 18, 48, 0, 4 }, { 32, 99, 32, 1, 2, 48, 32, 3 }, { 0, 99, 85, 86, 20, 48, 0, 3 }, { 48, 67, 85, 86, 4, 48, 48, 3 }, { 48, 67, 5, 86, 20, 48, 48, 4 }, { 48, 67, 85, 6, 20, 48, 48, 4 } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1892 */   private static final byte[][] impTabR_INVERSE_LIKE_DIRECT_WITH_MARKS = new byte[][] { { 19, 0, 1, 1, 0, 0, 0, 0 }, { 35, 0, 1, 1, 2, 64, 0, 1 }, { 35, 0, 1, 1, 2, 64, 0, 0 }, { 3, 0, 3, 54, 20, 64, 0, 1 }, { 83, 64, 5, 54, 4, 64, 64, 0 }, { 83, 64, 5, 54, 4, 64, 64, 1 }, { 83, 64, 6, 6, 4, 64, 64, 3 } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1905 */   private static final short[] impAct2 = new short[] { 0, 1, 7, 8, 9, 10 };
/* 1906 */   private static final ImpTabPair impTab_INVERSE_LIKE_DIRECT_WITH_MARKS = new ImpTabPair(impTabL_INVERSE_LIKE_DIRECT_WITH_MARKS, impTabR_INVERSE_LIKE_DIRECT_WITH_MARKS, impAct0, impAct2);
/*      */ 
/*      */ 
/*      */   
/* 1910 */   private static final ImpTabPair impTab_INVERSE_FOR_NUMBERS_SPECIAL = new ImpTabPair(impTabL_NUMBERS_SPECIAL, impTabR_INVERSE_LIKE_DIRECT, impAct0, impAct1);
/*      */ 
/*      */   
/* 1913 */   private static final byte[][] impTabL_INVERSE_FOR_NUMBERS_SPECIAL_WITH_MARKS = new byte[][] { { 0, 98, 1, 1, 0, 0, 0, 0 }, { 0, 98, 1, 1, 0, 48, 0, 4 }, { 0, 98, 84, 84, 19, 48, 0, 3 }, { 48, 66, 84, 84, 3, 48, 48, 3 }, { 48, 66, 4, 4, 19, 48, 48, 4 } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1923 */   private static final ImpTabPair impTab_INVERSE_FOR_NUMBERS_SPECIAL_WITH_MARKS = new ImpTabPair(impTabL_INVERSE_FOR_NUMBERS_SPECIAL_WITH_MARKS, impTabR_INVERSE_LIKE_DIRECT_WITH_MARKS, impAct0, impAct2);
/*      */   
/*      */   static final int FIRSTALLOC = 10;
/*      */   
/*      */   private static final int INTERNAL_DIRECTION_DEFAULT_LEFT_TO_RIGHT = 126;
/*      */   
/*      */   private static final int INTERMAL_DIRECTION_DEFAULT_RIGHT_TO_LEFT = 127;
/*      */ 
/*      */   
/*      */   private class LevState
/*      */   {
/*      */     byte[][] impTab;
/*      */     short[] impAct;
/*      */     int startON;
/*      */     int startL2EN;
/*      */     int lastStrongRTL;
/*      */     short state;
/*      */     byte runLevel;
/*      */     
/*      */     private LevState() {}
/*      */   }
/*      */   
/*      */   private void addPoint(int paramInt1, int paramInt2) {
/* 1946 */     Point point = new Point();
/*      */     
/* 1948 */     int i = this.insertPoints.points.length;
/* 1949 */     if (i == 0) {
/* 1950 */       this.insertPoints.points = new Point[10];
/* 1951 */       i = 10;
/*      */     } 
/* 1953 */     if (this.insertPoints.size >= i) {
/* 1954 */       Point[] arrayOfPoint = this.insertPoints.points;
/* 1955 */       this.insertPoints.points = new Point[i * 2];
/* 1956 */       System.arraycopy(arrayOfPoint, 0, this.insertPoints.points, 0, i);
/*      */     } 
/* 1958 */     point.pos = paramInt1;
/* 1959 */     point.flag = paramInt2;
/* 1960 */     this.insertPoints.points[this.insertPoints.size] = point;
/* 1961 */     this.insertPoints.size++;
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
/*      */   private void processPropertySeq(LevState paramLevState, short paramShort, int paramInt1, int paramInt2) {
/* 1982 */     byte[][] arrayOfByte = paramLevState.impTab;
/* 1983 */     short[] arrayOfShort = paramLevState.impAct;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1988 */     int i = paramInt1;
/* 1989 */     short s1 = paramLevState.state;
/* 1990 */     byte b1 = arrayOfByte[s1][paramShort];
/* 1991 */     paramLevState.state = GetState(b1);
/* 1992 */     short s2 = arrayOfShort[GetAction(b1)];
/* 1993 */     byte b2 = arrayOfByte[paramLevState.state][7];
/*      */     
/* 1995 */     if (s2 != 0) {
/* 1996 */       byte b; int j; switch (s2) {
/*      */         case 1:
/* 1998 */           paramLevState.startON = i;
/*      */           break;
/*      */         
/*      */         case 2:
/* 2002 */           paramInt1 = paramLevState.startON;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 3:
/* 2007 */           if (paramLevState.startL2EN >= 0) {
/* 2008 */             addPoint(paramLevState.startL2EN, 1);
/*      */           }
/* 2010 */           paramLevState.startL2EN = -1;
/*      */           
/* 2012 */           if (this.insertPoints.points.length == 0 || this.insertPoints.size <= this.insertPoints.confirmed) {
/*      */ 
/*      */             
/* 2015 */             paramLevState.lastStrongRTL = -1;
/*      */             
/* 2017 */             byte b3 = arrayOfByte[s1][7];
/* 2018 */             if ((b3 & 0x1) != 0 && paramLevState.startON > 0) {
/* 2019 */               paramInt1 = paramLevState.startON;
/*      */             }
/* 2021 */             if (paramShort == 5) {
/* 2022 */               addPoint(i, 1);
/* 2023 */               this.insertPoints.confirmed = this.insertPoints.size;
/*      */             } 
/*      */             
/*      */             break;
/*      */           } 
/* 2028 */           for (j = paramLevState.lastStrongRTL + 1; j < i; j++)
/*      */           {
/* 2030 */             this.levels[j] = (byte)(this.levels[j] - 2 & 0xFFFFFFFE);
/*      */           }
/*      */           
/* 2033 */           this.insertPoints.confirmed = this.insertPoints.size;
/* 2034 */           paramLevState.lastStrongRTL = -1;
/* 2035 */           if (paramShort == 5) {
/* 2036 */             addPoint(i, 1);
/* 2037 */             this.insertPoints.confirmed = this.insertPoints.size;
/*      */           } 
/*      */           break;
/*      */ 
/*      */         
/*      */         case 4:
/* 2043 */           if (this.insertPoints.points.length > 0)
/*      */           {
/* 2045 */             this.insertPoints.size = this.insertPoints.confirmed; } 
/* 2046 */           paramLevState.startON = -1;
/* 2047 */           paramLevState.startL2EN = -1;
/* 2048 */           paramLevState.lastStrongRTL = paramInt2 - 1;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 5:
/* 2053 */           if (paramShort == 3 && NoContextRTL(this.dirProps[i]) == 5) {
/*      */             
/* 2055 */             if (paramLevState.startL2EN == -1) {
/*      */               
/* 2057 */               paramLevState.lastStrongRTL = paramInt2 - 1;
/*      */               break;
/*      */             } 
/* 2060 */             if (paramLevState.startL2EN >= 0) {
/* 2061 */               addPoint(paramLevState.startL2EN, 1);
/* 2062 */               paramLevState.startL2EN = -2;
/*      */             } 
/*      */             
/* 2065 */             addPoint(i, 1);
/*      */             
/*      */             break;
/*      */           } 
/* 2069 */           if (paramLevState.startL2EN == -1) {
/* 2070 */             paramLevState.startL2EN = i;
/*      */           }
/*      */           break;
/*      */         
/*      */         case 6:
/* 2075 */           paramLevState.lastStrongRTL = paramInt2 - 1;
/* 2076 */           paramLevState.startON = -1;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 7:
/* 2081 */           for (j = i - 1; j >= 0 && (this.levels[j] & 0x1) == 0; j--);
/*      */           
/* 2083 */           if (j >= 0) {
/* 2084 */             addPoint(j, 4);
/* 2085 */             this.insertPoints.confirmed = this.insertPoints.size;
/*      */           } 
/* 2087 */           paramLevState.startON = i;
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case 8:
/* 2093 */           addPoint(i, 1);
/* 2094 */           addPoint(i, 2);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 9:
/* 2099 */           this.insertPoints.size = this.insertPoints.confirmed;
/* 2100 */           if (paramShort == 5) {
/* 2101 */             addPoint(i, 4);
/* 2102 */             this.insertPoints.confirmed = this.insertPoints.size;
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 10:
/* 2107 */           b = (byte)(paramLevState.runLevel + b2);
/* 2108 */           for (j = paramLevState.startON; j < i; j++) {
/* 2109 */             if (this.levels[j] < b) {
/* 2110 */               this.levels[j] = b;
/*      */             }
/*      */           } 
/* 2113 */           this.insertPoints.confirmed = this.insertPoints.size;
/* 2114 */           paramLevState.startON = i;
/*      */           break;
/*      */         
/*      */         case 11:
/* 2118 */           b = paramLevState.runLevel;
/* 2119 */           for (j = i - 1; j >= paramLevState.startON; j--) {
/* 2120 */             if (this.levels[j] == b + 3) {
/* 2121 */               while (this.levels[j] == b + 3) {
/* 2122 */                 this.levels[j--] = (byte)(this.levels[j--] - 2);
/*      */               }
/* 2124 */               while (this.levels[j] == b) {
/* 2125 */                 j--;
/*      */               }
/*      */             } 
/* 2128 */             if (this.levels[j] == b + 2) {
/* 2129 */               this.levels[j] = b;
/*      */             } else {
/*      */               
/* 2132 */               this.levels[j] = (byte)(b + 1);
/*      */             } 
/*      */           } 
/*      */           break;
/*      */         case 12:
/* 2137 */           b = (byte)(paramLevState.runLevel + 1);
/* 2138 */           for (j = i - 1; j >= paramLevState.startON; j--) {
/* 2139 */             if (this.levels[j] > b) {
/* 2140 */               this.levels[j] = (byte)(this.levels[j] - 2);
/*      */             }
/*      */           } 
/*      */           break;
/*      */         
/*      */         default:
/* 2146 */           throw new IllegalStateException("Internal ICU error in processPropertySeq");
/*      */       } 
/*      */     } 
/* 2149 */     if (b2 != 0 || paramInt1 < i) {
/* 2150 */       byte b = (byte)(paramLevState.runLevel + b2);
/* 2151 */       for (int j = paramInt1; j < paramInt2; j++) {
/* 2152 */         this.levels[j] = b;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void resolveImplicitLevels(int paramInt1, int paramInt2, short paramShort1, short paramShort2) {
/*      */     short s;
/* 2159 */     LevState levState = new LevState();
/*      */ 
/*      */ 
/*      */     
/* 2163 */     boolean bool = true;
/* 2164 */     byte b = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2175 */     levState.startL2EN = -1;
/* 2176 */     levState.lastStrongRTL = -1;
/* 2177 */     levState.state = 0;
/* 2178 */     levState.runLevel = this.levels[paramInt1];
/* 2179 */     levState.impTab = this.impTabPair.imptab[levState.runLevel & 0x1];
/* 2180 */     levState.impAct = this.impTabPair.impact[levState.runLevel & 0x1];
/* 2181 */     processPropertySeq(levState, paramShort1, paramInt1, paramInt1);
/*      */     
/* 2183 */     if (this.dirProps[paramInt1] == 17) {
/* 2184 */       s = (short)(1 + paramShort1);
/*      */     } else {
/* 2186 */       s = 0;
/*      */     } 
/* 2188 */     int j = paramInt1;
/* 2189 */     int k = 0;
/*      */     
/* 2191 */     for (int i = paramInt1; i <= paramInt2; i++) {
/* 2192 */       short s3; if (i >= paramInt2) {
/* 2193 */         s3 = paramShort2;
/*      */       } else {
/*      */         
/* 2196 */         short s5 = (short)NoContextRTL(this.dirProps[i]);
/* 2197 */         s3 = groupProp[s5];
/*      */       } 
/* 2199 */       short s1 = s;
/* 2200 */       short s4 = impTabProps[s1][s3];
/* 2201 */       s = GetStateProps(s4);
/* 2202 */       short s2 = GetActionProps(s4);
/* 2203 */       if (i == paramInt2 && s2 == 0)
/*      */       {
/* 2205 */         s2 = 1;
/*      */       }
/* 2207 */       if (s2 != 0) {
/* 2208 */         short s5 = impTabProps[s1][13];
/* 2209 */         switch (s2) {
/*      */           case 1:
/* 2211 */             processPropertySeq(levState, s5, j, i);
/* 2212 */             j = i;
/*      */             break;
/*      */           case 2:
/* 2215 */             k = i;
/*      */             break;
/*      */           case 3:
/* 2218 */             processPropertySeq(levState, s5, j, k);
/* 2219 */             processPropertySeq(levState, (short)4, k, i);
/* 2220 */             j = i;
/*      */             break;
/*      */           case 4:
/* 2223 */             processPropertySeq(levState, s5, j, k);
/* 2224 */             j = k;
/* 2225 */             k = i;
/*      */             break;
/*      */           default:
/* 2228 */             throw new IllegalStateException("Internal ICU error in resolveImplicitLevels");
/*      */         } 
/*      */       
/*      */       } 
/*      */     } 
/* 2233 */     processPropertySeq(levState, paramShort2, paramInt2, paramInt2);
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
/*      */   private void adjustWSLevels() {
/* 2247 */     if ((this.flags & MASK_WS) != 0) {
/*      */       
/* 2249 */       int i = this.trailingWSStart;
/* 2250 */       while (i > 0) {
/*      */         int j;
/* 2252 */         while (i > 0 && ((j = DirPropFlagNC(this.dirProps[--i])) & MASK_WS) != 0) {
/* 2253 */           if (this.orderParagraphsLTR && (j & DirPropFlag((byte)7)) != 0) {
/* 2254 */             this.levels[i] = 0; continue;
/*      */           } 
/* 2256 */           this.levels[i] = GetParaLevelAt(i);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2262 */         while (i > 0) {
/* 2263 */           j = DirPropFlagNC(this.dirProps[--i]);
/* 2264 */           if ((j & MASK_BN_EXPLICIT) != 0) {
/* 2265 */             this.levels[i] = this.levels[i + 1]; continue;
/* 2266 */           }  if (this.orderParagraphsLTR && (j & DirPropFlag((byte)7)) != 0) {
/* 2267 */             this.levels[i] = 0; break;
/*      */           } 
/* 2269 */           if ((j & MASK_B_S) != 0) {
/* 2270 */             this.levels[i] = GetParaLevelAt(i);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private int Bidi_Min(int paramInt1, int paramInt2) {
/* 2279 */     return (paramInt1 < paramInt2) ? paramInt1 : paramInt2;
/*      */   }
/*      */   
/*      */   private int Bidi_Abs(int paramInt) {
/* 2283 */     return (paramInt >= 0) ? paramInt : -paramInt;
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
/*      */   void setPara(String paramString, byte paramByte, byte[] paramArrayOfbyte) {
/* 2364 */     if (paramString == null) {
/* 2365 */       setPara(new char[0], paramByte, paramArrayOfbyte);
/*      */     } else {
/* 2367 */       setPara(paramString.toCharArray(), paramByte, paramArrayOfbyte);
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
/*      */   public void setPara(char[] paramArrayOfchar, byte paramByte, byte[] paramArrayOfbyte) {
/* 2449 */     if (paramByte < 126) {
/* 2450 */       verifyRange(paramByte, 0, 62);
/*      */     }
/* 2452 */     if (paramArrayOfchar == null) {
/* 2453 */       paramArrayOfchar = new char[0];
/*      */     }
/*      */ 
/*      */     
/* 2457 */     this.paraBidi = null;
/* 2458 */     this.text = paramArrayOfchar;
/* 2459 */     this.length = this.originalLength = this.resultLength = this.text.length;
/* 2460 */     this.paraLevel = paramByte;
/* 2461 */     this.direction = 0;
/* 2462 */     this.paraCount = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2467 */     this.dirProps = new byte[0];
/* 2468 */     this.levels = new byte[0];
/* 2469 */     this.runs = new BidiRun[0];
/* 2470 */     this.isGoodLogicalToVisualRunsMap = false;
/* 2471 */     this.insertPoints.size = 0;
/* 2472 */     this.insertPoints.confirmed = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2477 */     if (IsDefaultLevel(paramByte)) {
/* 2478 */       this.defaultParaLevel = paramByte;
/*      */     } else {
/* 2480 */       this.defaultParaLevel = 0;
/*      */     } 
/*      */     
/* 2483 */     if (this.length == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2489 */       if (IsDefaultLevel(paramByte)) {
/* 2490 */         this.paraLevel = (byte)(this.paraLevel & 0x1);
/* 2491 */         this.defaultParaLevel = 0;
/*      */       } 
/* 2493 */       if ((this.paraLevel & 0x1) != 0) {
/* 2494 */         this.flags = DirPropFlag((byte)1);
/* 2495 */         this.direction = 1;
/*      */       } else {
/* 2497 */         this.flags = DirPropFlag((byte)0);
/* 2498 */         this.direction = 0;
/*      */       } 
/*      */       
/* 2501 */       this.runCount = 0;
/* 2502 */       this.paraCount = 0;
/* 2503 */       this.paraBidi = this;
/*      */       
/*      */       return;
/*      */     } 
/* 2507 */     this.runCount = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2514 */     getDirPropsMemory(this.length);
/* 2515 */     this.dirProps = this.dirPropsMemory;
/* 2516 */     getDirProps();
/*      */ 
/*      */     
/* 2519 */     this.trailingWSStart = this.length;
/*      */ 
/*      */     
/* 2522 */     if (this.paraCount > 1) {
/* 2523 */       getInitialParasMemory(this.paraCount);
/* 2524 */       this.paras = this.parasMemory;
/* 2525 */       this.paras[this.paraCount - 1] = this.length;
/*      */     } else {
/*      */       
/* 2528 */       this.paras = this.simpleParas;
/* 2529 */       this.simpleParas[0] = this.length;
/*      */     } 
/*      */ 
/*      */     
/* 2533 */     if (paramArrayOfbyte == null) {
/*      */       
/* 2535 */       getLevelsMemory(this.length);
/* 2536 */       this.levels = this.levelsMemory;
/* 2537 */       this.direction = resolveExplicitLevels();
/*      */     } else {
/*      */       
/* 2540 */       this.levels = paramArrayOfbyte;
/* 2541 */       this.direction = checkExplicitLevels();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2548 */     switch (this.direction) {
/*      */       
/*      */       case 0:
/* 2551 */         paramByte = (byte)(paramByte + 1 & 0xFFFFFFFE);
/*      */ 
/*      */         
/* 2554 */         this.trailingWSStart = 0;
/*      */         break;
/*      */       
/*      */       case 1:
/* 2558 */         paramByte = (byte)(paramByte | 0x1);
/*      */ 
/*      */         
/* 2561 */         this.trailingWSStart = 0;
/*      */         break;
/*      */       default:
/* 2564 */         this.impTabPair = impTab_DEFAULT;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2577 */         if (paramArrayOfbyte == null && this.paraCount <= 1 && (this.flags & DirPropFlagMultiRuns) == 0) {
/*      */           
/* 2579 */           resolveImplicitLevels(0, this.length, 
/* 2580 */               (short)GetLRFromLevel(GetParaLevelAt(0)), 
/* 2581 */               (short)GetLRFromLevel(GetParaLevelAt(this.length - 1)));
/*      */         } else {
/*      */           short s;
/* 2584 */           byte b = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2589 */           byte b1 = GetParaLevelAt(0);
/* 2590 */           byte b2 = this.levels[0];
/* 2591 */           if (b1 < b2) {
/* 2592 */             s = (short)GetLRFromLevel(b2);
/*      */           } else {
/* 2594 */             s = (short)GetLRFromLevel(b1);
/*      */           } 
/*      */ 
/*      */           
/*      */           do {
/*      */             short s1;
/*      */             
/* 2601 */             byte b3 = b;
/* 2602 */             b1 = b2;
/* 2603 */             if (b3 && NoContextRTL(this.dirProps[b3 - 1]) == 7) {
/*      */               
/* 2605 */               s1 = (short)GetLRFromLevel(GetParaLevelAt(b3));
/*      */             } else {
/* 2607 */               s1 = s;
/*      */             } 
/*      */ 
/*      */             
/* 2611 */             while (++b < this.length && this.levels[b] == b1);
/*      */ 
/*      */             
/* 2614 */             if (b < this.length) {
/* 2615 */               b2 = this.levels[b];
/*      */             } else {
/* 2617 */               b2 = GetParaLevelAt(this.length - 1);
/*      */             } 
/*      */ 
/*      */             
/* 2621 */             if ((b1 & Byte.MAX_VALUE) < (b2 & Byte.MAX_VALUE)) {
/* 2622 */               s = (short)GetLRFromLevel(b2);
/*      */             } else {
/* 2624 */               s = (short)GetLRFromLevel(b1);
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/* 2629 */             if ((b1 & Byte.MIN_VALUE) == 0) {
/* 2630 */               resolveImplicitLevels(b3, b, s1, s);
/*      */             } else {
/*      */               
/*      */               do {
/* 2634 */                 this.levels[b3++] = (byte)(this.levels[b3++] & Byte.MAX_VALUE);
/* 2635 */               } while (b3 < b);
/*      */             } 
/* 2637 */           } while (b < this.length);
/*      */         } 
/*      */ 
/*      */         
/* 2641 */         adjustWSLevels();
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/* 2646 */     this.resultLength += this.insertPoints.size;
/* 2647 */     this.paraBidi = this;
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
/*      */   public void setPara(AttributedCharacterIterator paramAttributedCharacterIterator) {
/*      */     boolean bool;
/* 2693 */     char c = paramAttributedCharacterIterator.first();
/*      */     
/* 2695 */     Boolean bool1 = (Boolean)paramAttributedCharacterIterator.getAttribute(TextAttributeConstants.RUN_DIRECTION);
/* 2696 */     Object object = paramAttributedCharacterIterator.getAttribute(TextAttributeConstants.NUMERIC_SHAPING);
/* 2697 */     if (bool1 == null) {
/* 2698 */       bool = true;
/*      */     } else {
/* 2700 */       bool = bool1.equals(TextAttributeConstants.RUN_DIRECTION_LTR) ? false : true;
/*      */     } 
/*      */ 
/*      */     
/* 2704 */     byte[] arrayOfByte1 = null;
/* 2705 */     int i = paramAttributedCharacterIterator.getEndIndex() - paramAttributedCharacterIterator.getBeginIndex();
/* 2706 */     byte[] arrayOfByte2 = new byte[i];
/* 2707 */     char[] arrayOfChar = new char[i];
/* 2708 */     byte b = 0;
/* 2709 */     while (c != Character.MAX_VALUE) {
/* 2710 */       arrayOfChar[b] = c;
/*      */       
/* 2712 */       Integer integer = (Integer)paramAttributedCharacterIterator.getAttribute(TextAttributeConstants.BIDI_EMBEDDING);
/* 2713 */       if (integer != null) {
/* 2714 */         byte b1 = integer.byteValue();
/* 2715 */         if (b1 != 0)
/*      */         {
/* 2717 */           if (b1 < 0) {
/* 2718 */             arrayOfByte1 = arrayOfByte2;
/* 2719 */             arrayOfByte2[b] = (byte)(0 - b1 | 0xFFFFFF80);
/*      */           } else {
/* 2721 */             arrayOfByte1 = arrayOfByte2;
/* 2722 */             arrayOfByte2[b] = b1;
/*      */           }  } 
/*      */       } 
/* 2725 */       c = paramAttributedCharacterIterator.next();
/* 2726 */       b++;
/*      */     } 
/*      */     
/* 2729 */     if (object != null) {
/* 2730 */       NumericShapings.shape(object, arrayOfChar, 0, i);
/*      */     }
/* 2732 */     setPara(arrayOfChar, bool, arrayOfByte1);
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
/*      */   private void orderParagraphsLTR(boolean paramBoolean) {
/* 2754 */     this.orderParagraphsLTR = paramBoolean;
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
/*      */   private byte getDirection() {
/* 2775 */     verifyValidParaOrLine();
/* 2776 */     return this.direction;
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
/*      */   public int getLength() {
/* 2791 */     verifyValidParaOrLine();
/* 2792 */     return this.originalLength;
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
/*      */   public byte getParaLevel() {
/* 2816 */     verifyValidParaOrLine();
/* 2817 */     return this.paraLevel;
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
/*      */   public int getParagraphIndex(int paramInt) {
/* 2839 */     verifyValidParaOrLine();
/* 2840 */     BidiBase bidiBase = this.paraBidi;
/* 2841 */     verifyRange(paramInt, 0, bidiBase.length);
/*      */     byte b;
/* 2843 */     for (b = 0; paramInt >= bidiBase.paras[b]; b++);
/*      */     
/* 2845 */     return b;
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
/*      */   public Bidi setLine(Bidi paramBidi1, BidiBase paramBidiBase1, Bidi paramBidi2, BidiBase paramBidiBase2, int paramInt1, int paramInt2) {
/* 2889 */     verifyValidPara();
/* 2890 */     verifyRange(paramInt1, 0, paramInt2);
/* 2891 */     verifyRange(paramInt2, 0, this.length + 1);
/*      */     
/* 2893 */     return BidiLine.setLine(paramBidi1, this, paramBidi2, paramBidiBase2, paramInt1, paramInt2);
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
/*      */   public byte getLevelAt(int paramInt) {
/* 2913 */     if (paramInt < 0 || paramInt >= this.length) {
/* 2914 */       return (byte)getBaseLevel();
/*      */     }
/* 2916 */     verifyValidParaOrLine();
/* 2917 */     verifyRange(paramInt, 0, this.length);
/* 2918 */     return BidiLine.getLevelAt(this, paramInt);
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
/*      */   private byte[] getLevels() {
/* 2936 */     verifyValidParaOrLine();
/* 2937 */     if (this.length <= 0) {
/* 2938 */       return new byte[0];
/*      */     }
/* 2940 */     return BidiLine.getLevels(this);
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
/*      */   public int countRuns() {
/* 2959 */     verifyValidParaOrLine();
/* 2960 */     BidiLine.getRuns(this);
/* 2961 */     return this.runCount;
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
/*      */   private int[] getVisualMap() {
/* 2999 */     countRuns();
/* 3000 */     if (this.resultLength <= 0) {
/* 3001 */       return new int[0];
/*      */     }
/* 3003 */     return BidiLine.getVisualMap(this);
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
/*      */   private static int[] reorderVisual(byte[] paramArrayOfbyte) {
/* 3026 */     return BidiLine.reorderVisual(paramArrayOfbyte);
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
/*      */   public BidiBase(char[] paramArrayOfchar, int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3, int paramInt4) {
/* 3088 */     this(0, 0); byte b;
/*      */     byte[] arrayOfByte;
/* 3090 */     switch (paramInt4) {
/*      */       
/*      */       default:
/* 3093 */         b = 0;
/*      */         break;
/*      */       case 1:
/* 3096 */         b = 1;
/*      */         break;
/*      */       case -2:
/* 3099 */         b = 126;
/*      */         break;
/*      */       case -1:
/* 3102 */         b = 127;
/*      */         break;
/*      */     } 
/*      */     
/* 3106 */     if (paramArrayOfbyte == null) {
/* 3107 */       arrayOfByte = null;
/*      */     } else {
/* 3109 */       arrayOfByte = new byte[paramInt3];
/*      */       
/* 3111 */       for (byte b1 = 0; b1 < paramInt3; b1++) {
/* 3112 */         byte b2 = paramArrayOfbyte[b1 + paramInt2];
/* 3113 */         if (b2 < 0) {
/* 3114 */           b2 = (byte)(-b2 | Byte.MIN_VALUE);
/* 3115 */         } else if (b2 == 0) {
/* 3116 */           b2 = b;
/* 3117 */           if (b > 61) {
/* 3118 */             b2 = (byte)(b2 & 0x1);
/*      */           }
/*      */         } 
/* 3121 */         arrayOfByte[b1] = b2;
/*      */       } 
/*      */     } 
/* 3124 */     if (paramInt1 == 0 && paramInt2 == 0 && paramInt3 == paramArrayOfchar.length) {
/* 3125 */       setPara(paramArrayOfchar, b, arrayOfByte);
/*      */     } else {
/* 3127 */       char[] arrayOfChar = new char[paramInt3];
/* 3128 */       System.arraycopy(paramArrayOfchar, paramInt1, arrayOfChar, 0, paramInt3);
/* 3129 */       setPara(arrayOfChar, b, arrayOfByte);
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
/*      */   public boolean isMixed() {
/* 3146 */     return (!isLeftToRight() && !isRightToLeft());
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
/*      */   public boolean isLeftToRight() {
/* 3162 */     return (getDirection() == 0 && (this.paraLevel & 0x1) == 0);
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
/*      */   public boolean isRightToLeft() {
/* 3178 */     return (getDirection() == 1 && (this.paraLevel & 0x1) == 1);
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
/*      */   public boolean baseIsLeftToRight() {
/* 3193 */     return (getParaLevel() == 0);
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
/*      */   public int getBaseLevel() {
/* 3208 */     return getParaLevel();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getLogicalToVisualRunsMap() {
/* 3216 */     if (this.isGoodLogicalToVisualRunsMap) {
/*      */       return;
/*      */     }
/* 3219 */     int i = countRuns();
/* 3220 */     if (this.logicalToVisualRunsMap == null || this.logicalToVisualRunsMap.length < i)
/*      */     {
/* 3222 */       this.logicalToVisualRunsMap = new int[i];
/*      */     }
/*      */     
/* 3225 */     long[] arrayOfLong = new long[i]; byte b;
/* 3226 */     for (b = 0; b < i; b++) {
/* 3227 */       arrayOfLong[b] = ((this.runs[b]).start << 32L) + b;
/*      */     }
/* 3229 */     Arrays.sort(arrayOfLong);
/* 3230 */     for (b = 0; b < i; b++) {
/* 3231 */       this.logicalToVisualRunsMap[b] = (int)(arrayOfLong[b] & 0xFFFFFFFFFFFFFFFFL);
/*      */     }
/* 3233 */     arrayOfLong = null;
/* 3234 */     this.isGoodLogicalToVisualRunsMap = true;
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
/*      */   public int getRunLevel(int paramInt) {
/* 3252 */     verifyValidParaOrLine();
/* 3253 */     BidiLine.getRuns(this);
/* 3254 */     if (paramInt < 0 || paramInt >= this.runCount) {
/* 3255 */       return getParaLevel();
/*      */     }
/* 3257 */     getLogicalToVisualRunsMap();
/* 3258 */     return (this.runs[this.logicalToVisualRunsMap[paramInt]]).level;
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
/*      */   public int getRunStart(int paramInt) {
/* 3277 */     verifyValidParaOrLine();
/* 3278 */     BidiLine.getRuns(this);
/* 3279 */     if (this.runCount == 1)
/* 3280 */       return 0; 
/* 3281 */     if (paramInt == this.runCount) {
/* 3282 */       return this.length;
/*      */     }
/* 3284 */     verifyIndex(paramInt, 0, this.runCount);
/* 3285 */     getLogicalToVisualRunsMap();
/* 3286 */     return (this.runs[this.logicalToVisualRunsMap[paramInt]]).start;
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
/*      */   public int getRunLimit(int paramInt) {
/* 3306 */     verifyValidParaOrLine();
/* 3307 */     BidiLine.getRuns(this);
/* 3308 */     if (this.runCount == 1) {
/* 3309 */       return this.length;
/*      */     }
/* 3311 */     verifyIndex(paramInt, 0, this.runCount);
/* 3312 */     getLogicalToVisualRunsMap();
/* 3313 */     int i = this.logicalToVisualRunsMap[paramInt];
/* 3314 */     int j = (i == 0) ? (this.runs[i]).limit : ((this.runs[i]).limit - (this.runs[i - 1]).limit);
/*      */     
/* 3316 */     return (this.runs[i]).start + j;
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
/*      */   public static boolean requiresBidi(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 3344 */     if (0 > paramInt1 || paramInt1 > paramInt2 || paramInt2 > paramArrayOfchar.length) {
/* 3345 */       throw new IllegalArgumentException("Value start " + paramInt1 + " is out of range 0 to " + paramInt2);
/*      */     }
/*      */     
/* 3348 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 3349 */       if (Character.isHighSurrogate(paramArrayOfchar[i]) && i < paramInt2 - 1 && 
/* 3350 */         Character.isLowSurrogate(paramArrayOfchar[i + 1])) {
/* 3351 */         if ((1 << UCharacter.getDirection(Character.codePointAt(paramArrayOfchar, i)) & 0xE022) != 0) {
/* 3352 */           return true;
/*      */         }
/* 3354 */       } else if ((1 << UCharacter.getDirection(paramArrayOfchar[i]) & 0xE022) != 0) {
/* 3355 */         return true;
/*      */       } 
/*      */     } 
/* 3358 */     return false;
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
/*      */   public static void reorderVisually(byte[] paramArrayOfbyte, int paramInt1, Object[] paramArrayOfObject, int paramInt2, int paramInt3) {
/* 3384 */     if (0 > paramInt1 || paramArrayOfbyte.length <= paramInt1) {
/* 3385 */       throw new IllegalArgumentException("Value levelStart " + paramInt1 + " is out of range 0 to " + (paramArrayOfbyte.length - 1));
/*      */     }
/*      */ 
/*      */     
/* 3389 */     if (0 > paramInt2 || paramArrayOfObject.length <= paramInt2) {
/* 3390 */       throw new IllegalArgumentException("Value objectStart " + paramInt1 + " is out of range 0 to " + (paramArrayOfObject.length - 1));
/*      */     }
/*      */ 
/*      */     
/* 3394 */     if (0 > paramInt3 || paramArrayOfObject.length < paramInt2 + paramInt3) {
/* 3395 */       throw new IllegalArgumentException("Value count " + paramInt1 + " is out of range 0 to " + (paramArrayOfObject.length - paramInt2));
/*      */     }
/*      */ 
/*      */     
/* 3399 */     byte[] arrayOfByte = new byte[paramInt3];
/* 3400 */     System.arraycopy(paramArrayOfbyte, paramInt1, arrayOfByte, 0, paramInt3);
/* 3401 */     int[] arrayOfInt = reorderVisual(arrayOfByte);
/* 3402 */     Object[] arrayOfObject = new Object[paramInt3];
/* 3403 */     System.arraycopy(paramArrayOfObject, paramInt2, arrayOfObject, 0, paramInt3);
/* 3404 */     for (byte b = 0; b < paramInt3; b++) {
/* 3405 */       paramArrayOfObject[paramInt2 + b] = arrayOfObject[arrayOfInt[b]];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 3413 */     StringBuilder stringBuilder = new StringBuilder(getClass().getName());
/*      */     
/* 3415 */     stringBuilder.append("[dir: ");
/* 3416 */     stringBuilder.append(this.direction);
/* 3417 */     stringBuilder.append(" baselevel: ");
/* 3418 */     stringBuilder.append(this.paraLevel);
/* 3419 */     stringBuilder.append(" length: ");
/* 3420 */     stringBuilder.append(this.length);
/* 3421 */     stringBuilder.append(" runs: ");
/* 3422 */     if (this.levels == null) {
/* 3423 */       stringBuilder.append("none");
/*      */     } else {
/* 3425 */       stringBuilder.append('[');
/* 3426 */       stringBuilder.append(this.levels[0]);
/* 3427 */       for (byte b1 = 1; b1 < this.levels.length; b1++) {
/* 3428 */         stringBuilder.append(' ');
/* 3429 */         stringBuilder.append(this.levels[b1]);
/*      */       } 
/* 3431 */       stringBuilder.append(']');
/*      */     } 
/* 3433 */     stringBuilder.append(" text: [0x");
/* 3434 */     stringBuilder.append(Integer.toHexString(this.text[0]));
/* 3435 */     for (byte b = 1; b < this.text.length; b++) {
/* 3436 */       stringBuilder.append(" 0x");
/* 3437 */       stringBuilder.append(Integer.toHexString(this.text[b]));
/*      */     } 
/* 3439 */     stringBuilder.append("]]");
/*      */     
/* 3441 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class TextAttributeConstants
/*      */   {
/* 3449 */     private static final Class<?> clazz = getClass("java.awt.font.TextAttribute");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3456 */     static final AttributedCharacterIterator.Attribute RUN_DIRECTION = getTextAttribute("RUN_DIRECTION");
/*      */     
/* 3458 */     static final AttributedCharacterIterator.Attribute NUMERIC_SHAPING = getTextAttribute("NUMERIC_SHAPING");
/*      */     
/* 3460 */     static final AttributedCharacterIterator.Attribute BIDI_EMBEDDING = getTextAttribute("BIDI_EMBEDDING");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3465 */     static final Boolean RUN_DIRECTION_LTR = (clazz == null) ? Boolean.FALSE : 
/* 3466 */       (Boolean)getStaticField(clazz, "RUN_DIRECTION_LTR");
/*      */ 
/*      */     
/*      */     private static Class<?> getClass(String param1String) {
/*      */       try {
/* 3471 */         return Class.forName(param1String, true, null);
/* 3472 */       } catch (ClassNotFoundException classNotFoundException) {
/* 3473 */         return null;
/*      */       } 
/*      */     }
/*      */     
/*      */     private static Object getStaticField(Class<?> param1Class, String param1String) {
/*      */       try {
/* 3479 */         Field field = param1Class.getField(param1String);
/* 3480 */         return field.get(null);
/* 3481 */       } catch (NoSuchFieldException|IllegalAccessException noSuchFieldException) {
/* 3482 */         throw new AssertionError(noSuchFieldException);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static AttributedCharacterIterator.Attribute getTextAttribute(String param1String) {
/* 3490 */       if (clazz == null)
/*      */       {
/* 3492 */         return new AttributedCharacterIterator.Attribute(param1String) {  }
/*      */           ; } 
/* 3494 */       return (AttributedCharacterIterator.Attribute)getStaticField(clazz, param1String);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class NumericShapings
/*      */   {
/* 3505 */     private static final Class<?> clazz = getClass("java.awt.font.NumericShaper");
/*      */     
/* 3507 */     private static final Method shapeMethod = getMethod(clazz, "shape", new Class[] { char[].class, int.class, int.class });
/*      */     
/*      */     private static Class<?> getClass(String param1String) {
/*      */       try {
/* 3511 */         return Class.forName(param1String, true, null);
/* 3512 */       } catch (ClassNotFoundException classNotFoundException) {
/* 3513 */         return null;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static Method getMethod(Class<?> param1Class, String param1String, Class<?>... param1VarArgs) {
/* 3521 */       if (param1Class != null) {
/*      */         try {
/* 3523 */           return param1Class.getMethod(param1String, param1VarArgs);
/* 3524 */         } catch (NoSuchMethodException noSuchMethodException) {
/* 3525 */           throw new AssertionError(noSuchMethodException);
/*      */         } 
/*      */       }
/* 3528 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static void shape(Object param1Object, char[] param1ArrayOfchar, int param1Int1, int param1Int2) {
/* 3536 */       if (shapeMethod == null)
/* 3537 */         throw new AssertionError("Should not get here"); 
/*      */       try {
/* 3539 */         shapeMethod.invoke(param1Object, new Object[] { param1ArrayOfchar, Integer.valueOf(param1Int1), Integer.valueOf(param1Int2) });
/* 3540 */       } catch (InvocationTargetException invocationTargetException) {
/* 3541 */         Throwable throwable = invocationTargetException.getCause();
/* 3542 */         if (throwable instanceof RuntimeException)
/* 3543 */           throw (RuntimeException)throwable; 
/* 3544 */         throw new AssertionError(invocationTargetException);
/* 3545 */       } catch (IllegalAccessException illegalAccessException) {
/* 3546 */         throw new AssertionError(illegalAccessException);
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/bidi/BidiBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */