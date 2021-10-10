/*     */ package com.sun.org.apache.xerces.internal.impl.xs.identity;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
/*     */ import com.sun.org.apache.xerces.internal.util.IntStack;
/*     */ import com.sun.org.apache.xerces.internal.xni.QName;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLAttributes;
/*     */ import com.sun.org.apache.xerces.internal.xs.ShortList;
/*     */ import com.sun.org.apache.xerces.internal.xs.XSTypeDefinition;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XPathMatcher
/*     */ {
/*     */   protected static final boolean DEBUG_ALL = false;
/*     */   protected static final boolean DEBUG_METHODS = false;
/*     */   protected static final boolean DEBUG_METHODS2 = false;
/*     */   protected static final boolean DEBUG_METHODS3 = false;
/*     */   protected static final boolean DEBUG_MATCH = false;
/*     */   protected static final boolean DEBUG_STACK = false;
/*     */   protected static final boolean DEBUG_ANY = false;
/*     */   protected static final int MATCHED = 1;
/*     */   protected static final int MATCHED_ATTRIBUTE = 3;
/*     */   protected static final int MATCHED_DESCENDANT = 5;
/*     */   protected static final int MATCHED_DESCENDANT_PREVIOUS = 13;
/*     */   private XPath.LocationPath[] fLocationPaths;
/*     */   private int[] fMatched;
/*     */   protected Object fMatchedString;
/*     */   private IntStack[] fStepIndexes;
/*     */   private int[] fCurrentStep;
/*     */   private int[] fNoMatchDepth;
/* 111 */   final QName fQName = new QName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XPathMatcher(XPath xpath) {
/* 125 */     this.fLocationPaths = xpath.getLocationPaths();
/* 126 */     this.fStepIndexes = new IntStack[this.fLocationPaths.length];
/* 127 */     for (int i = 0; i < this.fStepIndexes.length; ) { this.fStepIndexes[i] = new IntStack(); i++; }
/* 128 */      this.fCurrentStep = new int[this.fLocationPaths.length];
/* 129 */     this.fNoMatchDepth = new int[this.fLocationPaths.length];
/* 130 */     this.fMatched = new int[this.fLocationPaths.length];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMatched() {
/* 143 */     for (int i = 0; i < this.fLocationPaths.length; i++) {
/* 144 */       if ((this.fMatched[i] & 0x1) == 1 && (this.fMatched[i] & 0xD) != 13 && (this.fNoMatchDepth[i] == 0 || (this.fMatched[i] & 0x5) == 5))
/*     */       {
/*     */ 
/*     */         
/* 148 */         return true; } 
/*     */     } 
/* 150 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleContent(XSTypeDefinition type, boolean nillable, Object value, short valueType, ShortList itemValueType) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void matched(Object actualValue, short valueType, ShortList itemValueType, boolean isNil) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDocumentFragment() {
/* 187 */     this.fMatchedString = null;
/* 188 */     for (int i = 0; i < this.fLocationPaths.length; i++) {
/* 189 */       this.fStepIndexes[i].clear();
/* 190 */       this.fCurrentStep[i] = 0;
/* 191 */       this.fNoMatchDepth[i] = 0;
/* 192 */       this.fMatched[i] = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(QName element, XMLAttributes attributes) {
/*     */     // Byte code:
/*     */     //   0: iconst_0
/*     */     //   1: istore_3
/*     */     //   2: iload_3
/*     */     //   3: aload_0
/*     */     //   4: getfield fLocationPaths : [Lcom/sun/org/apache/xerces/internal/impl/xpath/XPath$LocationPath;
/*     */     //   7: arraylength
/*     */     //   8: if_icmpge -> 709
/*     */     //   11: aload_0
/*     */     //   12: getfield fCurrentStep : [I
/*     */     //   15: iload_3
/*     */     //   16: iaload
/*     */     //   17: istore #4
/*     */     //   19: aload_0
/*     */     //   20: getfield fStepIndexes : [Lcom/sun/org/apache/xerces/internal/util/IntStack;
/*     */     //   23: iload_3
/*     */     //   24: aaload
/*     */     //   25: iload #4
/*     */     //   27: invokevirtual push : (I)V
/*     */     //   30: aload_0
/*     */     //   31: getfield fMatched : [I
/*     */     //   34: iload_3
/*     */     //   35: iaload
/*     */     //   36: iconst_5
/*     */     //   37: iand
/*     */     //   38: iconst_1
/*     */     //   39: if_icmpeq -> 51
/*     */     //   42: aload_0
/*     */     //   43: getfield fNoMatchDepth : [I
/*     */     //   46: iload_3
/*     */     //   47: iaload
/*     */     //   48: ifle -> 64
/*     */     //   51: aload_0
/*     */     //   52: getfield fNoMatchDepth : [I
/*     */     //   55: iload_3
/*     */     //   56: dup2
/*     */     //   57: iaload
/*     */     //   58: iconst_1
/*     */     //   59: iadd
/*     */     //   60: iastore
/*     */     //   61: goto -> 703
/*     */     //   64: aload_0
/*     */     //   65: getfield fMatched : [I
/*     */     //   68: iload_3
/*     */     //   69: iaload
/*     */     //   70: iconst_5
/*     */     //   71: iand
/*     */     //   72: iconst_5
/*     */     //   73: if_icmpne -> 84
/*     */     //   76: aload_0
/*     */     //   77: getfield fMatched : [I
/*     */     //   80: iload_3
/*     */     //   81: bipush #13
/*     */     //   83: iastore
/*     */     //   84: aload_0
/*     */     //   85: getfield fLocationPaths : [Lcom/sun/org/apache/xerces/internal/impl/xpath/XPath$LocationPath;
/*     */     //   88: iload_3
/*     */     //   89: aaload
/*     */     //   90: getfield steps : [Lcom/sun/org/apache/xerces/internal/impl/xpath/XPath$Step;
/*     */     //   93: astore #5
/*     */     //   95: aload_0
/*     */     //   96: getfield fCurrentStep : [I
/*     */     //   99: iload_3
/*     */     //   100: iaload
/*     */     //   101: aload #5
/*     */     //   103: arraylength
/*     */     //   104: if_icmpge -> 139
/*     */     //   107: aload #5
/*     */     //   109: aload_0
/*     */     //   110: getfield fCurrentStep : [I
/*     */     //   113: iload_3
/*     */     //   114: iaload
/*     */     //   115: aaload
/*     */     //   116: getfield axis : Lcom/sun/org/apache/xerces/internal/impl/xpath/XPath$Axis;
/*     */     //   119: getfield type : S
/*     */     //   122: iconst_3
/*     */     //   123: if_icmpne -> 139
/*     */     //   126: aload_0
/*     */     //   127: getfield fCurrentStep : [I
/*     */     //   130: iload_3
/*     */     //   131: dup2
/*     */     //   132: iaload
/*     */     //   133: iconst_1
/*     */     //   134: iadd
/*     */     //   135: iastore
/*     */     //   136: goto -> 95
/*     */     //   139: aload_0
/*     */     //   140: getfield fCurrentStep : [I
/*     */     //   143: iload_3
/*     */     //   144: iaload
/*     */     //   145: aload #5
/*     */     //   147: arraylength
/*     */     //   148: if_icmpne -> 161
/*     */     //   151: aload_0
/*     */     //   152: getfield fMatched : [I
/*     */     //   155: iload_3
/*     */     //   156: iconst_1
/*     */     //   157: iastore
/*     */     //   158: goto -> 703
/*     */     //   161: aload_0
/*     */     //   162: getfield fCurrentStep : [I
/*     */     //   165: iload_3
/*     */     //   166: iaload
/*     */     //   167: istore #6
/*     */     //   169: aload_0
/*     */     //   170: getfield fCurrentStep : [I
/*     */     //   173: iload_3
/*     */     //   174: iaload
/*     */     //   175: aload #5
/*     */     //   177: arraylength
/*     */     //   178: if_icmpge -> 213
/*     */     //   181: aload #5
/*     */     //   183: aload_0
/*     */     //   184: getfield fCurrentStep : [I
/*     */     //   187: iload_3
/*     */     //   188: iaload
/*     */     //   189: aaload
/*     */     //   190: getfield axis : Lcom/sun/org/apache/xerces/internal/impl/xpath/XPath$Axis;
/*     */     //   193: getfield type : S
/*     */     //   196: iconst_4
/*     */     //   197: if_icmpne -> 213
/*     */     //   200: aload_0
/*     */     //   201: getfield fCurrentStep : [I
/*     */     //   204: iload_3
/*     */     //   205: dup2
/*     */     //   206: iaload
/*     */     //   207: iconst_1
/*     */     //   208: iadd
/*     */     //   209: iastore
/*     */     //   210: goto -> 169
/*     */     //   213: aload_0
/*     */     //   214: getfield fCurrentStep : [I
/*     */     //   217: iload_3
/*     */     //   218: iaload
/*     */     //   219: iload #6
/*     */     //   221: if_icmple -> 228
/*     */     //   224: iconst_1
/*     */     //   225: goto -> 229
/*     */     //   228: iconst_0
/*     */     //   229: istore #7
/*     */     //   231: aload_0
/*     */     //   232: getfield fCurrentStep : [I
/*     */     //   235: iload_3
/*     */     //   236: iaload
/*     */     //   237: aload #5
/*     */     //   239: arraylength
/*     */     //   240: if_icmpne -> 256
/*     */     //   243: aload_0
/*     */     //   244: getfield fNoMatchDepth : [I
/*     */     //   247: iload_3
/*     */     //   248: dup2
/*     */     //   249: iaload
/*     */     //   250: iconst_1
/*     */     //   251: iadd
/*     */     //   252: iastore
/*     */     //   253: goto -> 703
/*     */     //   256: aload_0
/*     */     //   257: getfield fCurrentStep : [I
/*     */     //   260: iload_3
/*     */     //   261: iaload
/*     */     //   262: iload #4
/*     */     //   264: if_icmpeq -> 278
/*     */     //   267: aload_0
/*     */     //   268: getfield fCurrentStep : [I
/*     */     //   271: iload_3
/*     */     //   272: iaload
/*     */     //   273: iload #6
/*     */     //   275: if_icmple -> 381
/*     */     //   278: aload #5
/*     */     //   280: aload_0
/*     */     //   281: getfield fCurrentStep : [I
/*     */     //   284: iload_3
/*     */     //   285: iaload
/*     */     //   286: aaload
/*     */     //   287: getfield axis : Lcom/sun/org/apache/xerces/internal/impl/xpath/XPath$Axis;
/*     */     //   290: getfield type : S
/*     */     //   293: iconst_1
/*     */     //   294: if_icmpne -> 381
/*     */     //   297: aload #5
/*     */     //   299: aload_0
/*     */     //   300: getfield fCurrentStep : [I
/*     */     //   303: iload_3
/*     */     //   304: iaload
/*     */     //   305: aaload
/*     */     //   306: astore #8
/*     */     //   308: aload #8
/*     */     //   310: getfield nodeTest : Lcom/sun/org/apache/xerces/internal/impl/xpath/XPath$NodeTest;
/*     */     //   313: astore #9
/*     */     //   315: aload #9
/*     */     //   317: getfield type : S
/*     */     //   320: iconst_1
/*     */     //   321: if_icmpne -> 371
/*     */     //   324: aload #9
/*     */     //   326: getfield name : Lcom/sun/org/apache/xerces/internal/xni/QName;
/*     */     //   329: aload_1
/*     */     //   330: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   333: ifne -> 371
/*     */     //   336: aload_0
/*     */     //   337: getfield fCurrentStep : [I
/*     */     //   340: iload_3
/*     */     //   341: iaload
/*     */     //   342: iload #6
/*     */     //   344: if_icmple -> 358
/*     */     //   347: aload_0
/*     */     //   348: getfield fCurrentStep : [I
/*     */     //   351: iload_3
/*     */     //   352: iload #6
/*     */     //   354: iastore
/*     */     //   355: goto -> 703
/*     */     //   358: aload_0
/*     */     //   359: getfield fNoMatchDepth : [I
/*     */     //   362: iload_3
/*     */     //   363: dup2
/*     */     //   364: iaload
/*     */     //   365: iconst_1
/*     */     //   366: iadd
/*     */     //   367: iastore
/*     */     //   368: goto -> 703
/*     */     //   371: aload_0
/*     */     //   372: getfield fCurrentStep : [I
/*     */     //   375: iload_3
/*     */     //   376: dup2
/*     */     //   377: iaload
/*     */     //   378: iconst_1
/*     */     //   379: iadd
/*     */     //   380: iastore
/*     */     //   381: aload_0
/*     */     //   382: getfield fCurrentStep : [I
/*     */     //   385: iload_3
/*     */     //   386: iaload
/*     */     //   387: aload #5
/*     */     //   389: arraylength
/*     */     //   390: if_icmpne -> 426
/*     */     //   393: iload #7
/*     */     //   395: ifeq -> 416
/*     */     //   398: aload_0
/*     */     //   399: getfield fCurrentStep : [I
/*     */     //   402: iload_3
/*     */     //   403: iload #6
/*     */     //   405: iastore
/*     */     //   406: aload_0
/*     */     //   407: getfield fMatched : [I
/*     */     //   410: iload_3
/*     */     //   411: iconst_5
/*     */     //   412: iastore
/*     */     //   413: goto -> 703
/*     */     //   416: aload_0
/*     */     //   417: getfield fMatched : [I
/*     */     //   420: iload_3
/*     */     //   421: iconst_1
/*     */     //   422: iastore
/*     */     //   423: goto -> 703
/*     */     //   426: aload_0
/*     */     //   427: getfield fCurrentStep : [I
/*     */     //   430: iload_3
/*     */     //   431: iaload
/*     */     //   432: aload #5
/*     */     //   434: arraylength
/*     */     //   435: if_icmpge -> 703
/*     */     //   438: aload #5
/*     */     //   440: aload_0
/*     */     //   441: getfield fCurrentStep : [I
/*     */     //   444: iload_3
/*     */     //   445: iaload
/*     */     //   446: aaload
/*     */     //   447: getfield axis : Lcom/sun/org/apache/xerces/internal/impl/xpath/XPath$Axis;
/*     */     //   450: getfield type : S
/*     */     //   453: iconst_2
/*     */     //   454: if_icmpne -> 703
/*     */     //   457: aload_2
/*     */     //   458: invokeinterface getLength : ()I
/*     */     //   463: istore #8
/*     */     //   465: iload #8
/*     */     //   467: ifle -> 656
/*     */     //   470: aload #5
/*     */     //   472: aload_0
/*     */     //   473: getfield fCurrentStep : [I
/*     */     //   476: iload_3
/*     */     //   477: iaload
/*     */     //   478: aaload
/*     */     //   479: getfield nodeTest : Lcom/sun/org/apache/xerces/internal/impl/xpath/XPath$NodeTest;
/*     */     //   482: astore #9
/*     */     //   484: iconst_0
/*     */     //   485: istore #10
/*     */     //   487: iload #10
/*     */     //   489: iload #8
/*     */     //   491: if_icmpge -> 656
/*     */     //   494: aload_2
/*     */     //   495: iload #10
/*     */     //   497: aload_0
/*     */     //   498: getfield fQName : Lcom/sun/org/apache/xerces/internal/xni/QName;
/*     */     //   501: invokeinterface getName : (ILcom/sun/org/apache/xerces/internal/xni/QName;)V
/*     */     //   506: aload #9
/*     */     //   508: getfield type : S
/*     */     //   511: iconst_1
/*     */     //   512: if_icmpne -> 530
/*     */     //   515: aload #9
/*     */     //   517: getfield name : Lcom/sun/org/apache/xerces/internal/xni/QName;
/*     */     //   520: aload_0
/*     */     //   521: getfield fQName : Lcom/sun/org/apache/xerces/internal/xni/QName;
/*     */     //   524: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   527: ifeq -> 650
/*     */     //   530: aload_0
/*     */     //   531: getfield fCurrentStep : [I
/*     */     //   534: iload_3
/*     */     //   535: dup2
/*     */     //   536: iaload
/*     */     //   537: iconst_1
/*     */     //   538: iadd
/*     */     //   539: iastore
/*     */     //   540: aload_0
/*     */     //   541: getfield fCurrentStep : [I
/*     */     //   544: iload_3
/*     */     //   545: iaload
/*     */     //   546: aload #5
/*     */     //   548: arraylength
/*     */     //   549: if_icmpne -> 656
/*     */     //   552: aload_0
/*     */     //   553: getfield fMatched : [I
/*     */     //   556: iload_3
/*     */     //   557: iconst_3
/*     */     //   558: iastore
/*     */     //   559: iconst_0
/*     */     //   560: istore #11
/*     */     //   562: iload #11
/*     */     //   564: iload_3
/*     */     //   565: if_icmpge -> 587
/*     */     //   568: aload_0
/*     */     //   569: getfield fMatched : [I
/*     */     //   572: iload #11
/*     */     //   574: iaload
/*     */     //   575: iconst_1
/*     */     //   576: iand
/*     */     //   577: iconst_1
/*     */     //   578: if_icmpeq -> 587
/*     */     //   581: iinc #11, 1
/*     */     //   584: goto -> 562
/*     */     //   587: iload #11
/*     */     //   589: iload_3
/*     */     //   590: if_icmpne -> 647
/*     */     //   593: aload_2
/*     */     //   594: iload #10
/*     */     //   596: invokeinterface getAugmentations : (I)Lcom/sun/org/apache/xerces/internal/xni/Augmentations;
/*     */     //   601: ldc 'ATTRIBUTE_PSVI'
/*     */     //   603: invokeinterface getItem : (Ljava/lang/String;)Ljava/lang/Object;
/*     */     //   608: checkcast com/sun/org/apache/xerces/internal/xs/AttributePSVI
/*     */     //   611: astore #12
/*     */     //   613: aload_0
/*     */     //   614: aload #12
/*     */     //   616: invokeinterface getActualNormalizedValue : ()Ljava/lang/Object;
/*     */     //   621: putfield fMatchedString : Ljava/lang/Object;
/*     */     //   624: aload_0
/*     */     //   625: aload_0
/*     */     //   626: getfield fMatchedString : Ljava/lang/Object;
/*     */     //   629: aload #12
/*     */     //   631: invokeinterface getActualNormalizedValueType : ()S
/*     */     //   636: aload #12
/*     */     //   638: invokeinterface getItemValueTypes : ()Lcom/sun/org/apache/xerces/internal/xs/ShortList;
/*     */     //   643: iconst_0
/*     */     //   644: invokevirtual matched : (Ljava/lang/Object;SLcom/sun/org/apache/xerces/internal/xs/ShortList;Z)V
/*     */     //   647: goto -> 656
/*     */     //   650: iinc #10, 1
/*     */     //   653: goto -> 487
/*     */     //   656: aload_0
/*     */     //   657: getfield fMatched : [I
/*     */     //   660: iload_3
/*     */     //   661: iaload
/*     */     //   662: iconst_1
/*     */     //   663: iand
/*     */     //   664: iconst_1
/*     */     //   665: if_icmpeq -> 703
/*     */     //   668: aload_0
/*     */     //   669: getfield fCurrentStep : [I
/*     */     //   672: iload_3
/*     */     //   673: iaload
/*     */     //   674: iload #6
/*     */     //   676: if_icmple -> 690
/*     */     //   679: aload_0
/*     */     //   680: getfield fCurrentStep : [I
/*     */     //   683: iload_3
/*     */     //   684: iload #6
/*     */     //   686: iastore
/*     */     //   687: goto -> 703
/*     */     //   690: aload_0
/*     */     //   691: getfield fNoMatchDepth : [I
/*     */     //   694: iload_3
/*     */     //   695: dup2
/*     */     //   696: iaload
/*     */     //   697: iconst_1
/*     */     //   698: iadd
/*     */     //   699: iastore
/*     */     //   700: goto -> 703
/*     */     //   703: iinc #3, 1
/*     */     //   706: goto -> 2
/*     */     //   709: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #216	-> 0
/*     */     //   #218	-> 11
/*     */     //   #219	-> 19
/*     */     //   #222	-> 30
/*     */     //   #223	-> 51
/*     */     //   #224	-> 61
/*     */     //   #226	-> 64
/*     */     //   #227	-> 76
/*     */     //   #235	-> 84
/*     */     //   #236	-> 95
/*     */     //   #242	-> 126
/*     */     //   #244	-> 139
/*     */     //   #248	-> 151
/*     */     //   #249	-> 158
/*     */     //   #256	-> 161
/*     */     //   #257	-> 169
/*     */     //   #262	-> 200
/*     */     //   #264	-> 213
/*     */     //   #265	-> 231
/*     */     //   #269	-> 243
/*     */     //   #273	-> 253
/*     */     //   #277	-> 256
/*     */     //   #279	-> 297
/*     */     //   #280	-> 308
/*     */     //   #284	-> 315
/*     */     //   #285	-> 324
/*     */     //   #286	-> 336
/*     */     //   #287	-> 347
/*     */     //   #288	-> 355
/*     */     //   #290	-> 358
/*     */     //   #294	-> 368
/*     */     //   #297	-> 371
/*     */     //   #302	-> 381
/*     */     //   #303	-> 393
/*     */     //   #304	-> 398
/*     */     //   #305	-> 406
/*     */     //   #307	-> 416
/*     */     //   #309	-> 423
/*     */     //   #313	-> 426
/*     */     //   #318	-> 457
/*     */     //   #319	-> 465
/*     */     //   #320	-> 470
/*     */     //   #322	-> 484
/*     */     //   #323	-> 494
/*     */     //   #324	-> 506
/*     */     //   #325	-> 524
/*     */     //   #326	-> 530
/*     */     //   #327	-> 540
/*     */     //   #328	-> 552
/*     */     //   #329	-> 559
/*     */     //   #330	-> 562
/*     */     //   #331	-> 587
/*     */     //   #332	-> 593
/*     */     //   #333	-> 613
/*     */     //   #334	-> 624
/*     */     //   #336	-> 647
/*     */     //   #322	-> 650
/*     */     //   #341	-> 656
/*     */     //   #342	-> 668
/*     */     //   #343	-> 679
/*     */     //   #344	-> 687
/*     */     //   #346	-> 690
/*     */     //   #350	-> 700
/*     */     //   #216	-> 703
/*     */     //   #358	-> 709
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   308	73	8	step	Lcom/sun/org/apache/xerces/internal/impl/xpath/XPath$Step;
/*     */     //   315	66	9	nodeTest	Lcom/sun/org/apache/xerces/internal/impl/xpath/XPath$NodeTest;
/*     */     //   613	34	12	attrPSVI	Lcom/sun/org/apache/xerces/internal/xs/AttributePSVI;
/*     */     //   562	85	11	j	I
/*     */     //   487	169	10	aIndex	I
/*     */     //   484	172	9	nodeTest	Lcom/sun/org/apache/xerces/internal/impl/xpath/XPath$NodeTest;
/*     */     //   465	238	8	attrCount	I
/*     */     //   19	684	4	startStep	I
/*     */     //   95	608	5	steps	[Lcom/sun/org/apache/xerces/internal/impl/xpath/XPath$Step;
/*     */     //   169	534	6	descendantStep	I
/*     */     //   231	472	7	sawDescendant	Z
/*     */     //   2	707	3	i	I
/*     */     //   0	710	0	this	Lcom/sun/org/apache/xerces/internal/impl/xs/identity/XPathMatcher;
/*     */     //   0	710	1	element	Lcom/sun/org/apache/xerces/internal/xni/QName;
/*     */     //   0	710	2	attributes	Lcom/sun/org/apache/xerces/internal/xni/XMLAttributes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement(QName element, XSTypeDefinition type, boolean nillable, Object value, short valueType, ShortList itemValueType) {
/* 381 */     for (int i = 0; i < this.fLocationPaths.length; i++) {
/*     */       
/* 383 */       this.fCurrentStep[i] = this.fStepIndexes[i].pop();
/*     */ 
/*     */       
/* 386 */       if (this.fNoMatchDepth[i] > 0) {
/* 387 */         this.fNoMatchDepth[i] = this.fNoMatchDepth[i] - 1;
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 392 */         int j = 0;
/* 393 */         for (; j < i && (this.fMatched[j] & 0x1) != 1; j++);
/* 394 */         if (j >= i && this.fMatched[j] != 0 && (this.fMatched[j] & 0x3) != 3) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 402 */           handleContent(type, nillable, value, valueType, itemValueType);
/* 403 */           this.fMatched[i] = 0;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 422 */     StringBuffer str = new StringBuffer();
/* 423 */     String s = super.toString();
/* 424 */     int index2 = s.lastIndexOf('.');
/* 425 */     if (index2 != -1) {
/* 426 */       s = s.substring(index2 + 1);
/*     */     }
/* 428 */     str.append(s);
/* 429 */     for (int i = 0; i < this.fLocationPaths.length; i++) {
/* 430 */       str.append('[');
/* 431 */       XPath.Step[] steps = (this.fLocationPaths[i]).steps;
/* 432 */       for (int j = 0; j < steps.length; j++) {
/* 433 */         if (j == this.fCurrentStep[i]) {
/* 434 */           str.append('^');
/*     */         }
/* 436 */         str.append(steps[j].toString());
/* 437 */         if (j < steps.length - 1) {
/* 438 */           str.append('/');
/*     */         }
/*     */       } 
/* 441 */       if (this.fCurrentStep[i] == steps.length) {
/* 442 */         str.append('^');
/*     */       }
/* 444 */       str.append(']');
/* 445 */       str.append(',');
/*     */     } 
/* 447 */     return str.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String normalize(String s) {
/* 456 */     StringBuffer str = new StringBuffer();
/* 457 */     int length = s.length();
/* 458 */     for (int i = 0; i < length; i++) {
/* 459 */       char c = s.charAt(i);
/* 460 */       switch (c) {
/*     */         case '\n':
/* 462 */           str.append("\\n");
/*     */           break;
/*     */         
/*     */         default:
/* 466 */           str.append(c);
/*     */           break;
/*     */       } 
/*     */     } 
/* 470 */     return str.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xs/identity/XPathMatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */