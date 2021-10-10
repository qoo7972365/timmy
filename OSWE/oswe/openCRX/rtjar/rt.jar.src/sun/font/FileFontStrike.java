/*     */ package sun.font;
/*     */ 
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ public class FileFontStrike
/*     */   extends PhysicalStrike
/*     */ {
/*     */   static final int INVISIBLE_GLYPHS = 65534;
/*     */   private FileFont fileFont;
/*     */   private static final int UNINITIALISED = 0;
/*     */   private static final int INTARRAY = 1;
/*     */   private static final int LONGARRAY = 2;
/*     */   private static final int SEGINTARRAY = 3;
/*     */   private static final int SEGLONGARRAY = 4;
/*     */   private volatile int glyphCacheFormat;
/*     */   private static final int SEGSHIFT = 5;
/*     */   private static final int SEGSIZE = 32;
/*     */   private boolean segmentedCache;
/*     */   private int[][] segIntGlyphImages;
/*     */   private long[][] segLongGlyphImages;
/*     */   private float[] horizontalAdvances;
/*     */   private float[][] segHorizontalAdvances;
/*     */   ConcurrentHashMap<Integer, Rectangle2D.Float> boundsMap;
/*     */   SoftReference<ConcurrentHashMap<Integer, Point2D.Float>> glyphMetricsMapRef;
/*     */   AffineTransform invertDevTx;
/*     */   boolean useNatives;
/*     */   NativeStrike[] nativeStrikes;
/*     */   private int intPtSize;
/*     */   private static boolean isXPorLater = false;
/*     */   private WeakReference<ConcurrentHashMap<Integer, GeneralPath>> outlineMapRef;
/*     */   
/*     */   static {
/* 117 */     if (FontUtilities.isWindows && !FontUtilities.useT2K && 
/* 118 */       !GraphicsEnvironment.isHeadless()) {
/* 119 */       isXPorLater = initNative();
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
/*     */ 
/*     */ 
/*     */   
/*     */   FileFontStrike(FileFont paramFileFont, FontStrikeDesc paramFontStrikeDesc) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: aload_2
/*     */     //   3: invokespecial <init> : (Lsun/font/PhysicalFont;Lsun/font/FontStrikeDesc;)V
/*     */     //   6: aload_0
/*     */     //   7: iconst_0
/*     */     //   8: putfield glyphCacheFormat : I
/*     */     //   11: aload_0
/*     */     //   12: aload_1
/*     */     //   13: putfield fileFont : Lsun/font/FileFont;
/*     */     //   16: aload_2
/*     */     //   17: getfield style : I
/*     */     //   20: aload_1
/*     */     //   21: getfield style : I
/*     */     //   24: if_icmpeq -> 87
/*     */     //   27: aload_2
/*     */     //   28: getfield style : I
/*     */     //   31: iconst_2
/*     */     //   32: iand
/*     */     //   33: iconst_2
/*     */     //   34: if_icmpne -> 57
/*     */     //   37: aload_1
/*     */     //   38: getfield style : I
/*     */     //   41: iconst_2
/*     */     //   42: iand
/*     */     //   43: ifne -> 57
/*     */     //   46: aload_0
/*     */     //   47: iconst_1
/*     */     //   48: putfield algoStyle : Z
/*     */     //   51: aload_0
/*     */     //   52: ldc 0.7
/*     */     //   54: putfield italic : F
/*     */     //   57: aload_2
/*     */     //   58: getfield style : I
/*     */     //   61: iconst_1
/*     */     //   62: iand
/*     */     //   63: iconst_1
/*     */     //   64: if_icmpne -> 87
/*     */     //   67: aload_1
/*     */     //   68: getfield style : I
/*     */     //   71: iconst_1
/*     */     //   72: iand
/*     */     //   73: ifne -> 87
/*     */     //   76: aload_0
/*     */     //   77: iconst_1
/*     */     //   78: putfield algoStyle : Z
/*     */     //   81: aload_0
/*     */     //   82: ldc 1.33
/*     */     //   84: putfield boldness : F
/*     */     //   87: iconst_4
/*     */     //   88: newarray double
/*     */     //   90: astore_3
/*     */     //   91: aload_2
/*     */     //   92: getfield glyphTx : Ljava/awt/geom/AffineTransform;
/*     */     //   95: astore #4
/*     */     //   97: aload #4
/*     */     //   99: aload_3
/*     */     //   100: invokevirtual getMatrix : ([D)V
/*     */     //   103: aload_2
/*     */     //   104: getfield devTx : Ljava/awt/geom/AffineTransform;
/*     */     //   107: invokevirtual isIdentity : ()Z
/*     */     //   110: ifne -> 140
/*     */     //   113: aload_2
/*     */     //   114: getfield devTx : Ljava/awt/geom/AffineTransform;
/*     */     //   117: invokevirtual getType : ()I
/*     */     //   120: iconst_1
/*     */     //   121: if_icmpeq -> 140
/*     */     //   124: aload_0
/*     */     //   125: aload_2
/*     */     //   126: getfield devTx : Ljava/awt/geom/AffineTransform;
/*     */     //   129: invokevirtual createInverse : ()Ljava/awt/geom/AffineTransform;
/*     */     //   132: putfield invertDevTx : Ljava/awt/geom/AffineTransform;
/*     */     //   135: goto -> 140
/*     */     //   138: astore #5
/*     */     //   140: aload_2
/*     */     //   141: getfield aaHint : I
/*     */     //   144: iconst_1
/*     */     //   145: if_icmpeq -> 164
/*     */     //   148: aload_1
/*     */     //   149: getfield familyName : Ljava/lang/String;
/*     */     //   152: ldc 'Amble'
/*     */     //   154: invokevirtual startsWith : (Ljava/lang/String;)Z
/*     */     //   157: ifeq -> 164
/*     */     //   160: iconst_1
/*     */     //   161: goto -> 165
/*     */     //   164: iconst_0
/*     */     //   165: istore #5
/*     */     //   167: aload_3
/*     */     //   168: iconst_0
/*     */     //   169: daload
/*     */     //   170: invokestatic isNaN : (D)Z
/*     */     //   173: ifne -> 210
/*     */     //   176: aload_3
/*     */     //   177: iconst_1
/*     */     //   178: daload
/*     */     //   179: invokestatic isNaN : (D)Z
/*     */     //   182: ifne -> 210
/*     */     //   185: aload_3
/*     */     //   186: iconst_2
/*     */     //   187: daload
/*     */     //   188: invokestatic isNaN : (D)Z
/*     */     //   191: ifne -> 210
/*     */     //   194: aload_3
/*     */     //   195: iconst_3
/*     */     //   196: daload
/*     */     //   197: invokestatic isNaN : (D)Z
/*     */     //   200: ifne -> 210
/*     */     //   203: aload_1
/*     */     //   204: invokevirtual getScaler : ()Lsun/font/FontScaler;
/*     */     //   207: ifnonnull -> 220
/*     */     //   210: aload_0
/*     */     //   211: invokestatic getNullScalerContext : ()J
/*     */     //   214: putfield pScalerContext : J
/*     */     //   217: goto -> 250
/*     */     //   220: aload_0
/*     */     //   221: aload_1
/*     */     //   222: invokevirtual getScaler : ()Lsun/font/FontScaler;
/*     */     //   225: aload_3
/*     */     //   226: aload_2
/*     */     //   227: getfield aaHint : I
/*     */     //   230: aload_2
/*     */     //   231: getfield fmHint : I
/*     */     //   234: aload_0
/*     */     //   235: getfield boldness : F
/*     */     //   238: aload_0
/*     */     //   239: getfield italic : F
/*     */     //   242: iload #5
/*     */     //   244: invokevirtual createScalerContext : ([DIIFFZ)J
/*     */     //   247: putfield pScalerContext : J
/*     */     //   250: aload_0
/*     */     //   251: aload_1
/*     */     //   252: invokevirtual getMapper : ()Lsun/font/CharToGlyphMapper;
/*     */     //   255: putfield mapper : Lsun/font/CharToGlyphMapper;
/*     */     //   258: aload_0
/*     */     //   259: getfield mapper : Lsun/font/CharToGlyphMapper;
/*     */     //   262: invokevirtual getNumGlyphs : ()I
/*     */     //   265: istore #6
/*     */     //   267: aload_3
/*     */     //   268: iconst_3
/*     */     //   269: daload
/*     */     //   270: d2f
/*     */     //   271: fstore #7
/*     */     //   273: aload_0
/*     */     //   274: fload #7
/*     */     //   276: f2i
/*     */     //   277: dup_x1
/*     */     //   278: putfield intPtSize : I
/*     */     //   281: istore #8
/*     */     //   283: aload #4
/*     */     //   285: invokevirtual getType : ()I
/*     */     //   288: bipush #124
/*     */     //   290: iand
/*     */     //   291: ifne -> 298
/*     */     //   294: iconst_1
/*     */     //   295: goto -> 299
/*     */     //   298: iconst_0
/*     */     //   299: istore #9
/*     */     //   301: aload_0
/*     */     //   302: iload #6
/*     */     //   304: sipush #256
/*     */     //   307: if_icmpgt -> 345
/*     */     //   310: iload #6
/*     */     //   312: bipush #64
/*     */     //   314: if_icmple -> 349
/*     */     //   317: iload #9
/*     */     //   319: ifeq -> 345
/*     */     //   322: fload #7
/*     */     //   324: iload #8
/*     */     //   326: i2f
/*     */     //   327: fcmpl
/*     */     //   328: ifne -> 345
/*     */     //   331: iload #8
/*     */     //   333: bipush #6
/*     */     //   335: if_icmplt -> 345
/*     */     //   338: iload #8
/*     */     //   340: bipush #36
/*     */     //   342: if_icmple -> 349
/*     */     //   345: iconst_1
/*     */     //   346: goto -> 350
/*     */     //   349: iconst_0
/*     */     //   350: putfield segmentedCache : Z
/*     */     //   353: aload_0
/*     */     //   354: getfield pScalerContext : J
/*     */     //   357: lconst_0
/*     */     //   358: lcmp
/*     */     //   359: ifne -> 394
/*     */     //   362: aload_0
/*     */     //   363: new sun/font/FontStrikeDisposer
/*     */     //   366: dup
/*     */     //   367: aload_1
/*     */     //   368: aload_2
/*     */     //   369: invokespecial <init> : (Lsun/font/Font2D;Lsun/font/FontStrikeDesc;)V
/*     */     //   372: putfield disposer : Lsun/font/FontStrikeDisposer;
/*     */     //   375: aload_0
/*     */     //   376: invokespecial initGlyphCache : ()V
/*     */     //   379: aload_0
/*     */     //   380: invokestatic getNullScalerContext : ()J
/*     */     //   383: putfield pScalerContext : J
/*     */     //   386: invokestatic getInstance : ()Lsun/font/SunFontManager;
/*     */     //   389: aload_1
/*     */     //   390: invokevirtual deRegisterBadFont : (Lsun/font/Font2D;)V
/*     */     //   393: return
/*     */     //   394: getstatic sun/font/FontUtilities.isWindows : Z
/*     */     //   397: ifeq -> 509
/*     */     //   400: getstatic sun/font/FileFontStrike.isXPorLater : Z
/*     */     //   403: ifeq -> 509
/*     */     //   406: getstatic sun/font/FontUtilities.useT2K : Z
/*     */     //   409: ifne -> 509
/*     */     //   412: invokestatic isHeadless : ()Z
/*     */     //   415: ifne -> 509
/*     */     //   418: aload_1
/*     */     //   419: getfield useJavaRasterizer : Z
/*     */     //   422: ifne -> 509
/*     */     //   425: aload_2
/*     */     //   426: getfield aaHint : I
/*     */     //   429: iconst_4
/*     */     //   430: if_icmpeq -> 441
/*     */     //   433: aload_2
/*     */     //   434: getfield aaHint : I
/*     */     //   437: iconst_5
/*     */     //   438: if_icmpne -> 509
/*     */     //   441: aload_3
/*     */     //   442: iconst_1
/*     */     //   443: daload
/*     */     //   444: dconst_0
/*     */     //   445: dcmpl
/*     */     //   446: ifne -> 509
/*     */     //   449: aload_3
/*     */     //   450: iconst_2
/*     */     //   451: daload
/*     */     //   452: dconst_0
/*     */     //   453: dcmpl
/*     */     //   454: ifne -> 509
/*     */     //   457: aload_3
/*     */     //   458: iconst_0
/*     */     //   459: daload
/*     */     //   460: aload_3
/*     */     //   461: iconst_3
/*     */     //   462: daload
/*     */     //   463: dcmpl
/*     */     //   464: ifne -> 509
/*     */     //   467: aload_3
/*     */     //   468: iconst_0
/*     */     //   469: daload
/*     */     //   470: ldc2_w 3.0
/*     */     //   473: dcmpl
/*     */     //   474: iflt -> 509
/*     */     //   477: aload_3
/*     */     //   478: iconst_0
/*     */     //   479: daload
/*     */     //   480: ldc2_w 100.0
/*     */     //   483: dcmpg
/*     */     //   484: ifgt -> 509
/*     */     //   487: aload_1
/*     */     //   488: checkcast sun/font/TrueTypeFont
/*     */     //   491: aload_0
/*     */     //   492: getfield intPtSize : I
/*     */     //   495: invokevirtual useEmbeddedBitmapsForSize : (I)Z
/*     */     //   498: ifne -> 509
/*     */     //   501: aload_0
/*     */     //   502: iconst_1
/*     */     //   503: putfield useNatives : Z
/*     */     //   506: goto -> 636
/*     */     //   509: aload_1
/*     */     //   510: invokevirtual checkUseNatives : ()Z
/*     */     //   513: ifeq -> 636
/*     */     //   516: aload_2
/*     */     //   517: getfield aaHint : I
/*     */     //   520: ifne -> 636
/*     */     //   523: aload_0
/*     */     //   524: getfield algoStyle : Z
/*     */     //   527: ifne -> 636
/*     */     //   530: aload_3
/*     */     //   531: iconst_1
/*     */     //   532: daload
/*     */     //   533: dconst_0
/*     */     //   534: dcmpl
/*     */     //   535: ifne -> 636
/*     */     //   538: aload_3
/*     */     //   539: iconst_2
/*     */     //   540: daload
/*     */     //   541: dconst_0
/*     */     //   542: dcmpl
/*     */     //   543: ifne -> 636
/*     */     //   546: aload_3
/*     */     //   547: iconst_0
/*     */     //   548: daload
/*     */     //   549: ldc2_w 6.0
/*     */     //   552: dcmpl
/*     */     //   553: iflt -> 636
/*     */     //   556: aload_3
/*     */     //   557: iconst_0
/*     */     //   558: daload
/*     */     //   559: ldc2_w 36.0
/*     */     //   562: dcmpg
/*     */     //   563: ifgt -> 636
/*     */     //   566: aload_3
/*     */     //   567: iconst_0
/*     */     //   568: daload
/*     */     //   569: aload_3
/*     */     //   570: iconst_3
/*     */     //   571: daload
/*     */     //   572: dcmpl
/*     */     //   573: ifne -> 636
/*     */     //   576: aload_0
/*     */     //   577: iconst_1
/*     */     //   578: putfield useNatives : Z
/*     */     //   581: aload_1
/*     */     //   582: getfield nativeFonts : [Lsun/font/NativeFont;
/*     */     //   585: arraylength
/*     */     //   586: istore #10
/*     */     //   588: aload_0
/*     */     //   589: iload #10
/*     */     //   591: anewarray sun/font/NativeStrike
/*     */     //   594: putfield nativeStrikes : [Lsun/font/NativeStrike;
/*     */     //   597: iconst_0
/*     */     //   598: istore #11
/*     */     //   600: iload #11
/*     */     //   602: iload #10
/*     */     //   604: if_icmpge -> 636
/*     */     //   607: aload_0
/*     */     //   608: getfield nativeStrikes : [Lsun/font/NativeStrike;
/*     */     //   611: iload #11
/*     */     //   613: new sun/font/NativeStrike
/*     */     //   616: dup
/*     */     //   617: aload_1
/*     */     //   618: getfield nativeFonts : [Lsun/font/NativeFont;
/*     */     //   621: iload #11
/*     */     //   623: aaload
/*     */     //   624: aload_2
/*     */     //   625: iconst_0
/*     */     //   626: invokespecial <init> : (Lsun/font/NativeFont;Lsun/font/FontStrikeDesc;Z)V
/*     */     //   629: aastore
/*     */     //   630: iinc #11, 1
/*     */     //   633: goto -> 600
/*     */     //   636: invokestatic isLogging : ()Z
/*     */     //   639: ifeq -> 740
/*     */     //   642: getstatic sun/font/FontUtilities.isWindows : Z
/*     */     //   645: ifeq -> 740
/*     */     //   648: invokestatic getLogger : ()Lsun/util/logging/PlatformLogger;
/*     */     //   651: new java/lang/StringBuilder
/*     */     //   654: dup
/*     */     //   655: invokespecial <init> : ()V
/*     */     //   658: ldc 'Strike for '
/*     */     //   660: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   663: aload_1
/*     */     //   664: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*     */     //   667: ldc ' at size = '
/*     */     //   669: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   672: aload_0
/*     */     //   673: getfield intPtSize : I
/*     */     //   676: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*     */     //   679: ldc ' use natives = '
/*     */     //   681: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   684: aload_0
/*     */     //   685: getfield useNatives : Z
/*     */     //   688: invokevirtual append : (Z)Ljava/lang/StringBuilder;
/*     */     //   691: ldc ' useJavaRasteriser = '
/*     */     //   693: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   696: aload_1
/*     */     //   697: getfield useJavaRasterizer : Z
/*     */     //   700: invokevirtual append : (Z)Ljava/lang/StringBuilder;
/*     */     //   703: ldc ' AAHint = '
/*     */     //   705: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   708: aload_2
/*     */     //   709: getfield aaHint : I
/*     */     //   712: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*     */     //   715: ldc ' Has Embedded bitmaps = '
/*     */     //   717: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   720: aload_1
/*     */     //   721: checkcast sun/font/TrueTypeFont
/*     */     //   724: aload_0
/*     */     //   725: getfield intPtSize : I
/*     */     //   728: invokevirtual useEmbeddedBitmapsForSize : (I)Z
/*     */     //   731: invokevirtual append : (Z)Ljava/lang/StringBuilder;
/*     */     //   734: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   737: invokevirtual info : (Ljava/lang/String;)V
/*     */     //   740: aload_0
/*     */     //   741: new sun/font/FontStrikeDisposer
/*     */     //   744: dup
/*     */     //   745: aload_1
/*     */     //   746: aload_2
/*     */     //   747: aload_0
/*     */     //   748: getfield pScalerContext : J
/*     */     //   751: invokespecial <init> : (Lsun/font/Font2D;Lsun/font/FontStrikeDesc;J)V
/*     */     //   754: putfield disposer : Lsun/font/FontStrikeDisposer;
/*     */     //   757: ldc2_w 48.0
/*     */     //   760: dstore #10
/*     */     //   762: aload_0
/*     */     //   763: aload #4
/*     */     //   765: invokevirtual getScaleX : ()D
/*     */     //   768: invokestatic abs : (D)D
/*     */     //   771: dload #10
/*     */     //   773: dcmpg
/*     */     //   774: ifgt -> 823
/*     */     //   777: aload #4
/*     */     //   779: invokevirtual getScaleY : ()D
/*     */     //   782: invokestatic abs : (D)D
/*     */     //   785: dload #10
/*     */     //   787: dcmpg
/*     */     //   788: ifgt -> 823
/*     */     //   791: aload #4
/*     */     //   793: invokevirtual getShearX : ()D
/*     */     //   796: invokestatic abs : (D)D
/*     */     //   799: dload #10
/*     */     //   801: dcmpg
/*     */     //   802: ifgt -> 823
/*     */     //   805: aload #4
/*     */     //   807: invokevirtual getShearY : ()D
/*     */     //   810: invokestatic abs : (D)D
/*     */     //   813: dload #10
/*     */     //   815: dcmpg
/*     */     //   816: ifgt -> 823
/*     */     //   819: iconst_1
/*     */     //   820: goto -> 824
/*     */     //   823: iconst_0
/*     */     //   824: putfield getImageWithAdvance : Z
/*     */     //   827: aload_0
/*     */     //   828: getfield getImageWithAdvance : Z
/*     */     //   831: ifne -> 898
/*     */     //   834: aload_0
/*     */     //   835: getfield segmentedCache : Z
/*     */     //   838: ifne -> 877
/*     */     //   841: aload_0
/*     */     //   842: iload #6
/*     */     //   844: newarray float
/*     */     //   846: putfield horizontalAdvances : [F
/*     */     //   849: iconst_0
/*     */     //   850: istore #12
/*     */     //   852: iload #12
/*     */     //   854: iload #6
/*     */     //   856: if_icmpge -> 874
/*     */     //   859: aload_0
/*     */     //   860: getfield horizontalAdvances : [F
/*     */     //   863: iload #12
/*     */     //   865: ldc 3.4028235E38
/*     */     //   867: fastore
/*     */     //   868: iinc #12, 1
/*     */     //   871: goto -> 852
/*     */     //   874: goto -> 898
/*     */     //   877: iload #6
/*     */     //   879: bipush #32
/*     */     //   881: iadd
/*     */     //   882: iconst_1
/*     */     //   883: isub
/*     */     //   884: bipush #32
/*     */     //   886: idiv
/*     */     //   887: istore #12
/*     */     //   889: aload_0
/*     */     //   890: iload #12
/*     */     //   892: anewarray [F
/*     */     //   895: putfield segHorizontalAdvances : [[F
/*     */     //   898: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #124	-> 0
/*     */     //   #61	-> 6
/*     */     //   #125	-> 11
/*     */     //   #127	-> 16
/*     */     //   #132	-> 27
/*     */     //   #134	-> 46
/*     */     //   #135	-> 51
/*     */     //   #137	-> 57
/*     */     //   #139	-> 76
/*     */     //   #140	-> 81
/*     */     //   #143	-> 87
/*     */     //   #144	-> 91
/*     */     //   #145	-> 97
/*     */     //   #146	-> 103
/*     */     //   #147	-> 117
/*     */     //   #149	-> 124
/*     */     //   #151	-> 135
/*     */     //   #150	-> 138
/*     */     //   #168	-> 140
/*     */     //   #169	-> 154
/*     */     //   #177	-> 167
/*     */     //   #178	-> 188
/*     */     //   #179	-> 204
/*     */     //   #180	-> 210
/*     */     //   #182	-> 220
/*     */     //   #187	-> 250
/*     */     //   #188	-> 258
/*     */     //   #198	-> 267
/*     */     //   #199	-> 273
/*     */     //   #200	-> 283
/*     */     //   #201	-> 301
/*     */     //   #213	-> 353
/*     */     //   #217	-> 362
/*     */     //   #218	-> 375
/*     */     //   #219	-> 379
/*     */     //   #220	-> 386
/*     */     //   #221	-> 393
/*     */     //   #230	-> 394
/*     */     //   #232	-> 412
/*     */     //   #239	-> 495
/*     */     //   #240	-> 501
/*     */     //   #242	-> 509
/*     */     //   #245	-> 530
/*     */     //   #248	-> 576
/*     */     //   #249	-> 581
/*     */     //   #250	-> 588
/*     */     //   #254	-> 597
/*     */     //   #255	-> 607
/*     */     //   #254	-> 630
/*     */     //   #260	-> 636
/*     */     //   #261	-> 648
/*     */     //   #268	-> 728
/*     */     //   #262	-> 737
/*     */     //   #270	-> 740
/*     */     //   #278	-> 757
/*     */     //   #279	-> 762
/*     */     //   #280	-> 765
/*     */     //   #281	-> 779
/*     */     //   #282	-> 793
/*     */     //   #283	-> 807
/*     */     //   #294	-> 827
/*     */     //   #295	-> 834
/*     */     //   #296	-> 841
/*     */     //   #298	-> 849
/*     */     //   #299	-> 859
/*     */     //   #298	-> 868
/*     */     //   #302	-> 877
/*     */     //   #303	-> 889
/*     */     //   #306	-> 898
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   124	135	138	java/awt/geom/NoninvertibleTransformException
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
/*     */ 
/*     */   
/*     */   public int getNumGlyphs() {
/* 313 */     return this.fileFont.getNumGlyphs();
/*     */   }
/*     */   
/*     */   long getGlyphImageFromNative(int paramInt) {
/* 317 */     if (FontUtilities.isWindows) {
/* 318 */       return getGlyphImageFromWindows(paramInt);
/*     */     }
/* 320 */     return getGlyphImageFromX11(paramInt);
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
/*     */   long getGlyphImageFromWindows(int paramInt) {
/* 334 */     String str = this.fileFont.getFamilyName(null);
/*     */     
/* 336 */     int i = this.desc.style & 0x1 | this.desc.style & 0x2 | this.fileFont.getStyle();
/* 337 */     int j = this.intPtSize;
/*     */     
/* 339 */     long l = _getGlyphImageFromWindows(str, i, j, paramInt, (this.desc.fmHint == 2));
/*     */     
/* 341 */     if (l != 0L) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 349 */       float f = getGlyphAdvance(paramInt, false);
/* 350 */       StrikeCache.unsafe.putFloat(l + StrikeCache.xAdvanceOffset, f);
/*     */       
/* 352 */       return l;
/*     */     } 
/* 354 */     return this.fileFont.getGlyphImage(this.pScalerContext, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long getGlyphImageFromX11(int paramInt) {
/* 361 */     char c = this.fileFont.glyphToCharMap[paramInt];
/* 362 */     for (byte b = 0; b < this.nativeStrikes.length; b++) {
/* 363 */       CharToGlyphMapper charToGlyphMapper = this.fileFont.nativeFonts[b].getMapper();
/* 364 */       int i = charToGlyphMapper.charToGlyph(c) & 0xFFFF;
/* 365 */       if (i != charToGlyphMapper.getMissingGlyphCode()) {
/* 366 */         long l = this.nativeStrikes[b].getGlyphImagePtrNoCache(i);
/* 367 */         if (l != 0L) {
/* 368 */           return l;
/*     */         }
/*     */       } 
/*     */     } 
/* 372 */     return this.fileFont.getGlyphImage(this.pScalerContext, paramInt);
/*     */   }
/*     */   
/*     */   long getGlyphImagePtr(int paramInt) {
/* 376 */     if (paramInt >= 65534) {
/* 377 */       return StrikeCache.invisibleGlyphPtr;
/*     */     }
/* 379 */     long l = 0L;
/* 380 */     if ((l = getCachedGlyphPtr(paramInt)) != 0L) {
/* 381 */       return l;
/*     */     }
/* 383 */     if (this.useNatives) {
/* 384 */       l = getGlyphImageFromNative(paramInt);
/* 385 */       if (l == 0L && FontUtilities.isLogging()) {
/* 386 */         FontUtilities.getLogger()
/* 387 */           .info("Strike for " + this.fileFont + " at size = " + this.intPtSize + " couldn't get native glyph for code = " + paramInt);
/*     */       }
/*     */     } 
/*     */     
/* 391 */     if (l == 0L) {
/* 392 */       l = this.fileFont.getGlyphImage(this.pScalerContext, paramInt);
/*     */     }
/*     */     
/* 395 */     return setCachedGlyphPtr(paramInt, l);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void getGlyphImagePtrs(int[] paramArrayOfint, long[] paramArrayOflong, int paramInt) {
/* 401 */     for (byte b = 0; b < paramInt; b++) {
/* 402 */       int i = paramArrayOfint[b];
/* 403 */       if (i >= 65534) {
/* 404 */         paramArrayOflong[b] = StrikeCache.invisibleGlyphPtr;
/*     */       } else {
/* 406 */         paramArrayOflong[b] = getCachedGlyphPtr(i); if (getCachedGlyphPtr(i) == 0L) {
/*     */ 
/*     */           
/* 409 */           long l = 0L;
/* 410 */           if (this.useNatives)
/* 411 */             l = getGlyphImageFromNative(i); 
/* 412 */           if (l == 0L) {
/* 413 */             l = this.fileFont.getGlyphImage(this.pScalerContext, i);
/*     */           }
/*     */           
/* 416 */           paramArrayOflong[b] = setCachedGlyphPtr(i, l);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int getSlot0GlyphImagePtrs(int[] paramArrayOfint, long[] paramArrayOflong, int paramInt) {
/* 425 */     byte b1 = 0;
/*     */     
/* 427 */     for (byte b2 = 0; b2 < paramInt; b2++) {
/* 428 */       int i = paramArrayOfint[b2];
/* 429 */       if (i >>> 24 != 0) {
/* 430 */         return b1;
/*     */       }
/* 432 */       b1++;
/*     */       
/* 434 */       if (i >= 65534) {
/* 435 */         paramArrayOflong[b2] = StrikeCache.invisibleGlyphPtr;
/*     */       } else {
/* 437 */         paramArrayOflong[b2] = getCachedGlyphPtr(i); if (getCachedGlyphPtr(i) == 0L) {
/*     */ 
/*     */           
/* 440 */           long l = 0L;
/* 441 */           if (this.useNatives) {
/* 442 */             l = getGlyphImageFromNative(i);
/*     */           }
/* 444 */           if (l == 0L) {
/* 445 */             l = this.fileFont.getGlyphImage(this.pScalerContext, i);
/*     */           }
/*     */           
/* 448 */           paramArrayOflong[b2] = setCachedGlyphPtr(i, l);
/*     */         } 
/*     */       } 
/* 451 */     }  return b1;
/*     */   }
/*     */ 
/*     */   
/*     */   long getCachedGlyphPtr(int paramInt) {
/*     */     try {
/* 457 */       return getCachedGlyphPtrInternal(paramInt);
/* 458 */     } catch (Exception exception) {
/*     */       
/* 460 */       NullFontScaler nullFontScaler = (NullFontScaler)FontScaler.getNullScaler();
/* 461 */       long l = NullFontScaler.getNullScalerContext();
/* 462 */       return nullFontScaler.getGlyphImage(l, paramInt);
/*     */     } 
/*     */   }
/*     */   private long getCachedGlyphPtrInternal(int paramInt) {
/*     */     int i;
/* 467 */     switch (this.glyphCacheFormat) {
/*     */       case 1:
/* 469 */         return this.intGlyphImages[paramInt] & 0xFFFFFFFFL;
/*     */       case 3:
/* 471 */         i = paramInt >> 5;
/* 472 */         if (this.segIntGlyphImages[i] != null) {
/* 473 */           int j = paramInt % 32;
/* 474 */           return this.segIntGlyphImages[i][j] & 0xFFFFFFFFL;
/*     */         } 
/* 476 */         return 0L;
/*     */       
/*     */       case 2:
/* 479 */         return this.longGlyphImages[paramInt];
/*     */       case 4:
/* 481 */         i = paramInt >> 5;
/* 482 */         if (this.segLongGlyphImages[i] != null) {
/* 483 */           int j = paramInt % 32;
/* 484 */           return this.segLongGlyphImages[i][j];
/*     */         } 
/* 486 */         return 0L;
/*     */     } 
/*     */ 
/*     */     
/* 490 */     return 0L;
/*     */   }
/*     */   
/*     */   private synchronized long setCachedGlyphPtr(int paramInt, long paramLong) {
/*     */     try {
/* 495 */       return setCachedGlyphPtrInternal(paramInt, paramLong);
/* 496 */     } catch (Exception exception) {
/* 497 */       switch (this.glyphCacheFormat) {
/*     */         case 1:
/*     */         case 3:
/* 500 */           StrikeCache.freeIntPointer((int)paramLong);
/*     */           break;
/*     */         case 2:
/*     */         case 4:
/* 504 */           StrikeCache.freeLongPointer(paramLong);
/*     */           break;
/*     */       } 
/*     */       
/* 508 */       NullFontScaler nullFontScaler = (NullFontScaler)FontScaler.getNullScaler();
/* 509 */       long l = NullFontScaler.getNullScalerContext();
/* 510 */       return nullFontScaler.getGlyphImage(l, paramInt);
/*     */     } 
/*     */   }
/*     */   private long setCachedGlyphPtrInternal(int paramInt, long paramLong) {
/*     */     int i, j;
/* 515 */     switch (this.glyphCacheFormat) {
/*     */       case 1:
/* 517 */         if (this.intGlyphImages[paramInt] == 0) {
/* 518 */           this.intGlyphImages[paramInt] = (int)paramLong;
/* 519 */           return paramLong;
/*     */         } 
/* 521 */         StrikeCache.freeIntPointer((int)paramLong);
/* 522 */         return this.intGlyphImages[paramInt] & 0xFFFFFFFFL;
/*     */ 
/*     */       
/*     */       case 3:
/* 526 */         i = paramInt >> 5;
/* 527 */         j = paramInt % 32;
/* 528 */         if (this.segIntGlyphImages[i] == null) {
/* 529 */           this.segIntGlyphImages[i] = new int[32];
/*     */         }
/* 531 */         if (this.segIntGlyphImages[i][j] == 0) {
/* 532 */           this.segIntGlyphImages[i][j] = (int)paramLong;
/* 533 */           return paramLong;
/*     */         } 
/* 535 */         StrikeCache.freeIntPointer((int)paramLong);
/* 536 */         return this.segIntGlyphImages[i][j] & 0xFFFFFFFFL;
/*     */ 
/*     */       
/*     */       case 2:
/* 540 */         if (this.longGlyphImages[paramInt] == 0L) {
/* 541 */           this.longGlyphImages[paramInt] = paramLong;
/* 542 */           return paramLong;
/*     */         } 
/* 544 */         StrikeCache.freeLongPointer(paramLong);
/* 545 */         return this.longGlyphImages[paramInt];
/*     */ 
/*     */       
/*     */       case 4:
/* 549 */         i = paramInt >> 5;
/* 550 */         j = paramInt % 32;
/* 551 */         if (this.segLongGlyphImages[i] == null) {
/* 552 */           this.segLongGlyphImages[i] = new long[32];
/*     */         }
/* 554 */         if (this.segLongGlyphImages[i][j] == 0L) {
/* 555 */           this.segLongGlyphImages[i][j] = paramLong;
/* 556 */           return paramLong;
/*     */         } 
/* 558 */         StrikeCache.freeLongPointer(paramLong);
/* 559 */         return this.segLongGlyphImages[i][j];
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 567 */     initGlyphCache();
/* 568 */     return setCachedGlyphPtr(paramInt, paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void initGlyphCache() {
/* 574 */     int i = this.mapper.getNumGlyphs();
/* 575 */     byte b = 0;
/* 576 */     if (this.segmentedCache) {
/* 577 */       int j = (i + 32 - 1) / 32;
/* 578 */       if (longAddresses) {
/* 579 */         b = 4;
/* 580 */         this.segLongGlyphImages = new long[j][];
/* 581 */         this.disposer.segLongGlyphImages = this.segLongGlyphImages;
/*     */       } else {
/* 583 */         b = 3;
/* 584 */         this.segIntGlyphImages = new int[j][];
/* 585 */         this.disposer.segIntGlyphImages = this.segIntGlyphImages;
/*     */       }
/*     */     
/* 588 */     } else if (longAddresses) {
/* 589 */       b = 2;
/* 590 */       this.longGlyphImages = new long[i];
/* 591 */       this.disposer.longGlyphImages = this.longGlyphImages;
/*     */     } else {
/* 593 */       b = 1;
/* 594 */       this.intGlyphImages = new int[i];
/* 595 */       this.disposer.intGlyphImages = this.intGlyphImages;
/*     */     } 
/*     */     
/* 598 */     this.glyphCacheFormat = b;
/*     */   }
/*     */   
/*     */   float getGlyphAdvance(int paramInt) {
/* 602 */     return getGlyphAdvance(paramInt, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getGlyphAdvance(int paramInt, boolean paramBoolean) {
/*     */     float f;
/* 613 */     if (paramInt >= 65534) {
/* 614 */       return 0.0F;
/*     */     }
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
/* 644 */     if (this.horizontalAdvances != null) {
/* 645 */       f = this.horizontalAdvances[paramInt];
/* 646 */       if (f != Float.MAX_VALUE) {
/* 647 */         if (!paramBoolean && this.invertDevTx != null) {
/* 648 */           Point2D.Float float_ = new Point2D.Float(f, 0.0F);
/* 649 */           this.desc.devTx.deltaTransform(float_, float_);
/* 650 */           return float_.x;
/*     */         } 
/* 652 */         return f;
/*     */       }
/*     */     
/* 655 */     } else if (this.segmentedCache && this.segHorizontalAdvances != null) {
/* 656 */       int i = paramInt >> 5;
/* 657 */       float[] arrayOfFloat = this.segHorizontalAdvances[i];
/* 658 */       if (arrayOfFloat != null) {
/* 659 */         f = arrayOfFloat[paramInt % 32];
/* 660 */         if (f != Float.MAX_VALUE) {
/* 661 */           if (!paramBoolean && this.invertDevTx != null) {
/* 662 */             Point2D.Float float_ = new Point2D.Float(f, 0.0F);
/* 663 */             this.desc.devTx.deltaTransform(float_, float_);
/* 664 */             return float_.x;
/*     */           } 
/* 666 */           return f;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 672 */     if (!paramBoolean && this.invertDevTx != null) {
/* 673 */       Point2D.Float float_ = new Point2D.Float();
/* 674 */       this.fileFont.getGlyphMetrics(this.pScalerContext, paramInt, float_);
/* 675 */       return float_.x;
/*     */     } 
/*     */     
/* 678 */     if (this.invertDevTx != null || !paramBoolean) {
/*     */ 
/*     */ 
/*     */       
/* 682 */       f = (getGlyphMetrics(paramInt, paramBoolean)).x;
/*     */     } else {
/*     */       long l;
/* 685 */       if (this.getImageWithAdvance) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 691 */         l = getGlyphImagePtr(paramInt);
/*     */       } else {
/* 693 */         l = getCachedGlyphPtr(paramInt);
/*     */       } 
/* 695 */       if (l != 0L) {
/*     */         
/* 697 */         f = StrikeCache.unsafe.getFloat(l + StrikeCache.xAdvanceOffset);
/*     */       } else {
/*     */         
/* 700 */         f = this.fileFont.getGlyphAdvance(this.pScalerContext, paramInt);
/*     */       } 
/*     */     } 
/*     */     
/* 704 */     if (this.horizontalAdvances != null) {
/* 705 */       this.horizontalAdvances[paramInt] = f;
/* 706 */     } else if (this.segmentedCache && this.segHorizontalAdvances != null) {
/* 707 */       int i = paramInt >> 5;
/* 708 */       int j = paramInt % 32;
/* 709 */       if (this.segHorizontalAdvances[i] == null) {
/* 710 */         this.segHorizontalAdvances[i] = new float[32];
/* 711 */         for (byte b = 0; b < 32; b++) {
/* 712 */           this.segHorizontalAdvances[i][b] = Float.MAX_VALUE;
/*     */         }
/*     */       } 
/* 715 */       this.segHorizontalAdvances[i][j] = f;
/*     */     } 
/* 717 */     return f;
/*     */   }
/*     */   
/*     */   float getCodePointAdvance(int paramInt) {
/* 721 */     return getGlyphAdvance(this.mapper.charToGlyph(paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void getGlyphImageBounds(int paramInt, Point2D.Float paramFloat, Rectangle paramRectangle) {
/* 730 */     long l = getGlyphImagePtr(paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 736 */     if (l == 0L) {
/* 737 */       paramRectangle.x = (int)Math.floor(paramFloat.x);
/* 738 */       paramRectangle.y = (int)Math.floor(paramFloat.y);
/* 739 */       paramRectangle.width = paramRectangle.height = 0;
/*     */       
/*     */       return;
/*     */     } 
/* 743 */     float f1 = StrikeCache.unsafe.getFloat(l + StrikeCache.topLeftXOffset);
/* 744 */     float f2 = StrikeCache.unsafe.getFloat(l + StrikeCache.topLeftYOffset);
/*     */     
/* 746 */     paramRectangle.x = (int)Math.floor((paramFloat.x + f1));
/* 747 */     paramRectangle.y = (int)Math.floor((paramFloat.y + f2));
/* 748 */     paramRectangle
/* 749 */       .width = StrikeCache.unsafe.getShort(l + StrikeCache.widthOffset) & 0xFFFF;
/* 750 */     paramRectangle
/* 751 */       .height = StrikeCache.unsafe.getShort(l + StrikeCache.heightOffset) & 0xFFFF;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 759 */     if ((this.desc.aaHint == 4 || this.desc.aaHint == 5) && f1 <= -2.0F) {
/*     */ 
/*     */       
/* 762 */       int i = getGlyphImageMinX(l, paramRectangle.x);
/* 763 */       if (i > paramRectangle.x) {
/* 764 */         paramRectangle.x++;
/* 765 */         paramRectangle.width--;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int getGlyphImageMinX(long paramLong, int paramInt) {
/* 772 */     char c1 = StrikeCache.unsafe.getChar(paramLong + StrikeCache.widthOffset);
/* 773 */     char c2 = StrikeCache.unsafe.getChar(paramLong + StrikeCache.heightOffset);
/*     */     
/* 775 */     char c3 = StrikeCache.unsafe.getChar(paramLong + StrikeCache.rowBytesOffset);
/*     */     
/* 777 */     if (c3 == c1) {
/* 778 */       return paramInt;
/*     */     }
/*     */ 
/*     */     
/* 782 */     long l = StrikeCache.unsafe.getAddress(paramLong + StrikeCache.pixelDataOffset);
/*     */     
/* 784 */     if (l == 0L) {
/* 785 */       return paramInt;
/*     */     }
/*     */     
/* 788 */     for (byte b = 0; b < c2; b++) {
/* 789 */       for (byte b1 = 0; b1 < 3; b1++) {
/* 790 */         if (StrikeCache.unsafe.getByte(l + (b * c3) + b1) != 0) {
/* 791 */           return paramInt;
/*     */         }
/*     */       } 
/*     */     } 
/* 795 */     return paramInt + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   StrikeMetrics getFontMetrics() {
/* 802 */     if (this.strikeMetrics == null) {
/* 803 */       this
/* 804 */         .strikeMetrics = this.fileFont.getFontMetrics(this.pScalerContext);
/* 805 */       if (this.invertDevTx != null) {
/* 806 */         this.strikeMetrics.convertToUserSpace(this.invertDevTx);
/*     */       }
/*     */     } 
/* 809 */     return this.strikeMetrics;
/*     */   }
/*     */   
/*     */   Point2D.Float getGlyphMetrics(int paramInt) {
/* 813 */     return getGlyphMetrics(paramInt, true);
/*     */   }
/*     */   private Point2D.Float getGlyphMetrics(int paramInt, boolean paramBoolean) {
/*     */     long l;
/* 817 */     Point2D.Float float_ = new Point2D.Float();
/*     */ 
/*     */     
/* 820 */     if (paramInt >= 65534) {
/* 821 */       return float_;
/*     */     }
/*     */     
/* 824 */     if (this.getImageWithAdvance && paramBoolean) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 830 */       l = getGlyphImagePtr(paramInt);
/*     */     } else {
/* 832 */       l = getCachedGlyphPtr(paramInt);
/*     */     } 
/* 834 */     if (l != 0L) {
/* 835 */       float_ = new Point2D.Float();
/* 836 */       float_
/* 837 */         .x = StrikeCache.unsafe.getFloat(l + StrikeCache.xAdvanceOffset);
/* 838 */       float_
/* 839 */         .y = StrikeCache.unsafe.getFloat(l + StrikeCache.yAdvanceOffset);
/*     */ 
/*     */ 
/*     */       
/* 843 */       if (this.invertDevTx != null) {
/* 844 */         this.invertDevTx.deltaTransform(float_, float_);
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 854 */       Integer integer = Integer.valueOf(paramInt);
/* 855 */       Point2D.Float float_1 = null;
/* 856 */       ConcurrentHashMap<Object, Object> concurrentHashMap = null;
/* 857 */       if (this.glyphMetricsMapRef != null) {
/* 858 */         concurrentHashMap = (ConcurrentHashMap)this.glyphMetricsMapRef.get();
/*     */       }
/* 860 */       if (concurrentHashMap != null) {
/* 861 */         float_1 = (Point2D.Float)concurrentHashMap.get(integer);
/* 862 */         if (float_1 != null) {
/* 863 */           float_.x = float_1.x;
/* 864 */           float_.y = float_1.y;
/*     */           
/* 866 */           return float_;
/*     */         } 
/*     */       } 
/* 869 */       if (float_1 == null) {
/* 870 */         this.fileFont.getGlyphMetrics(this.pScalerContext, paramInt, float_);
/*     */ 
/*     */ 
/*     */         
/* 874 */         if (this.invertDevTx != null) {
/* 875 */           this.invertDevTx.deltaTransform(float_, float_);
/*     */         }
/* 877 */         float_1 = new Point2D.Float(float_.x, float_.y);
/*     */ 
/*     */ 
/*     */         
/* 881 */         if (concurrentHashMap == null) {
/* 882 */           concurrentHashMap = new ConcurrentHashMap<>();
/*     */           
/* 884 */           this.glyphMetricsMapRef = new SoftReference(concurrentHashMap);
/*     */         } 
/*     */ 
/*     */         
/* 888 */         concurrentHashMap.put(integer, float_1);
/*     */       } 
/*     */     } 
/* 891 */     return float_;
/*     */   }
/*     */   
/*     */   Point2D.Float getCharMetrics(char paramChar) {
/* 895 */     return getGlyphMetrics(this.mapper.charToGlyph(paramChar));
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
/*     */   Rectangle2D.Float getGlyphOutlineBounds(int paramInt) {
/* 917 */     if (this.boundsMap == null) {
/* 918 */       this.boundsMap = new ConcurrentHashMap<>();
/*     */     }
/*     */     
/* 921 */     Integer integer = Integer.valueOf(paramInt);
/* 922 */     Rectangle2D.Float float_ = this.boundsMap.get(integer);
/*     */     
/* 924 */     if (float_ == null) {
/* 925 */       float_ = this.fileFont.getGlyphOutlineBounds(this.pScalerContext, paramInt);
/* 926 */       this.boundsMap.put(integer, float_);
/*     */     } 
/* 928 */     return float_;
/*     */   }
/*     */   
/*     */   public Rectangle2D getOutlineBounds(int paramInt) {
/* 932 */     return this.fileFont.getGlyphOutlineBounds(this.pScalerContext, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GeneralPath getGlyphOutline(int paramInt, float paramFloat1, float paramFloat2) {
/* 940 */     GeneralPath generalPath = null;
/* 941 */     ConcurrentHashMap<Object, Object> concurrentHashMap = null;
/*     */     
/* 943 */     if (this.outlineMapRef != null) {
/* 944 */       concurrentHashMap = (ConcurrentHashMap)this.outlineMapRef.get();
/* 945 */       if (concurrentHashMap != null) {
/* 946 */         generalPath = (GeneralPath)concurrentHashMap.get(Integer.valueOf(paramInt));
/*     */       }
/*     */     } 
/*     */     
/* 950 */     if (generalPath == null) {
/* 951 */       generalPath = this.fileFont.getGlyphOutline(this.pScalerContext, paramInt, 0.0F, 0.0F);
/* 952 */       if (concurrentHashMap == null) {
/* 953 */         concurrentHashMap = new ConcurrentHashMap<>();
/* 954 */         this.outlineMapRef = new WeakReference(concurrentHashMap);
/*     */       } 
/*     */ 
/*     */       
/* 958 */       concurrentHashMap.put(Integer.valueOf(paramInt), generalPath);
/*     */     } 
/* 960 */     generalPath = (GeneralPath)generalPath.clone();
/* 961 */     if (paramFloat1 != 0.0F || paramFloat2 != 0.0F) {
/* 962 */       generalPath.transform(AffineTransform.getTranslateInstance(paramFloat1, paramFloat2));
/*     */     }
/* 964 */     return generalPath;
/*     */   }
/*     */   
/*     */   GeneralPath getGlyphVectorOutline(int[] paramArrayOfint, float paramFloat1, float paramFloat2) {
/* 968 */     return this.fileFont.getGlyphVectorOutline(this.pScalerContext, paramArrayOfint, paramArrayOfint.length, paramFloat1, paramFloat2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void adjustPoint(Point2D.Float paramFloat) {
/* 973 */     if (this.invertDevTx != null)
/* 974 */       this.invertDevTx.deltaTransform(paramFloat, paramFloat); 
/*     */   }
/*     */   
/*     */   private static native boolean initNative();
/*     */   
/*     */   private native long _getGlyphImageFromWindows(String paramString, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/FileFontStrike.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */