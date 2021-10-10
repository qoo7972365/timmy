/*     */ package sun.font;
/*     */ 
/*     */ import java.awt.Shape;
/*     */ import java.awt.font.LayoutPath;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.NoninvertibleTransformException;
/*     */ import java.awt.geom.PathIterator;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Formatter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class LayoutPathImpl
/*     */   extends LayoutPath
/*     */ {
/*     */   private static final boolean LOGMAP = false;
/*     */   
/*     */   public Point2D pointToPath(double paramDouble1, double paramDouble2) {
/*  57 */     Point2D.Double double_ = new Point2D.Double(paramDouble1, paramDouble2);
/*  58 */     pointToPath(double_, double_);
/*  59 */     return double_;
/*     */   }
/*     */   
/*     */   public Point2D pathToPoint(double paramDouble1, double paramDouble2, boolean paramBoolean) {
/*  63 */     Point2D.Double double_ = new Point2D.Double(paramDouble1, paramDouble2);
/*  64 */     pathToPoint(double_, paramBoolean, double_);
/*  65 */     return double_;
/*     */   }
/*     */   
/*     */   public void pointToPath(double paramDouble1, double paramDouble2, Point2D paramPoint2D) {
/*  69 */     paramPoint2D.setLocation(paramDouble1, paramDouble2);
/*  70 */     pointToPath(paramPoint2D, paramPoint2D);
/*     */   }
/*     */   
/*     */   public void pathToPoint(double paramDouble1, double paramDouble2, boolean paramBoolean, Point2D paramPoint2D) {
/*  74 */     paramPoint2D.setLocation(paramDouble1, paramDouble2);
/*  75 */     pathToPoint(paramPoint2D, paramBoolean, paramPoint2D);
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
/*  92 */   private static final Formatter LOG = new Formatter(System.out);
/*     */   
/*     */   public abstract double start();
/*     */   
/*     */   public abstract double end();
/*     */   
/*     */   public abstract double length();
/*     */   
/*     */   public abstract Shape mapShape(Shape paramShape);
/*     */   
/*     */   public enum EndType {
/* 103 */     PINNED, EXTENDED, CLOSED;
/* 104 */     public boolean isPinned() { return (this == PINNED); }
/* 105 */     public boolean isExtended() { return (this == EXTENDED); } public boolean isClosed() {
/* 106 */       return (this == CLOSED);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LayoutPathImpl getPath(EndType paramEndType, double... paramVarArgs) {
/* 117 */     if ((paramVarArgs.length & 0x1) != 0) {
/* 118 */       throw new IllegalArgumentException("odd number of points not allowed");
/*     */     }
/*     */     
/* 121 */     return SegmentPath.get(paramEndType, paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class SegmentPathBuilder
/*     */   {
/*     */     private double[] data;
/*     */ 
/*     */ 
/*     */     
/*     */     private int w;
/*     */ 
/*     */ 
/*     */     
/*     */     private double px;
/*     */ 
/*     */     
/*     */     private double py;
/*     */ 
/*     */     
/*     */     private double a;
/*     */ 
/*     */     
/*     */     private boolean pconnect;
/*     */ 
/*     */ 
/*     */     
/*     */     public void reset(int param1Int) {
/* 151 */       if (this.data == null || param1Int > this.data.length) {
/* 152 */         this.data = new double[param1Int];
/* 153 */       } else if (param1Int == 0) {
/* 154 */         this.data = null;
/*     */       } 
/* 156 */       this.w = 0;
/* 157 */       this.px = this.py = 0.0D;
/* 158 */       this.pconnect = false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public LayoutPathImpl.SegmentPath build(LayoutPathImpl.EndType param1EndType, double... param1VarArgs) {
/* 166 */       assert param1VarArgs.length % 2 == 0;
/*     */       
/* 168 */       reset(param1VarArgs.length / 2 * 3);
/*     */       
/* 170 */       for (byte b = 0; b < param1VarArgs.length; b += 2) {
/* 171 */         nextPoint(param1VarArgs[b], param1VarArgs[b + 1], (b != 0));
/*     */       }
/*     */       
/* 174 */       return complete(param1EndType);
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
/*     */     public void moveTo(double param1Double1, double param1Double2) {
/* 189 */       nextPoint(param1Double1, param1Double2, false);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void lineTo(double param1Double1, double param1Double2) {
/* 200 */       nextPoint(param1Double1, param1Double2, true);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void nextPoint(double param1Double1, double param1Double2, boolean param1Boolean) {
/* 211 */       if (param1Double1 == this.px && param1Double2 == this.py) {
/*     */         return;
/*     */       }
/*     */       
/* 215 */       if (this.w == 0) {
/* 216 */         if (this.data == null) {
/* 217 */           this.data = new double[6];
/*     */         }
/* 219 */         if (param1Boolean) {
/* 220 */           this.w = 3;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 225 */       if (this.w != 0 && !param1Boolean && !this.pconnect) {
/* 226 */         this.data[this.w - 3] = this.px = param1Double1;
/* 227 */         this.data[this.w - 2] = this.py = param1Double2;
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 232 */       if (this.w == this.data.length) {
/* 233 */         double[] arrayOfDouble = new double[this.w * 2];
/* 234 */         System.arraycopy(this.data, 0, arrayOfDouble, 0, this.w);
/* 235 */         this.data = arrayOfDouble;
/*     */       } 
/*     */       
/* 238 */       if (param1Boolean) {
/* 239 */         double d1 = param1Double1 - this.px;
/* 240 */         double d2 = param1Double2 - this.py;
/* 241 */         this.a += Math.sqrt(d1 * d1 + d2 * d2);
/*     */       } 
/*     */ 
/*     */       
/* 245 */       this.data[this.w++] = param1Double1;
/* 246 */       this.data[this.w++] = param1Double2;
/* 247 */       this.data[this.w++] = this.a;
/*     */ 
/*     */       
/* 250 */       this.px = param1Double1;
/* 251 */       this.py = param1Double2;
/* 252 */       this.pconnect = param1Boolean;
/*     */     }
/*     */     
/*     */     public LayoutPathImpl.SegmentPath complete() {
/* 256 */       return complete(LayoutPathImpl.EndType.EXTENDED);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public LayoutPathImpl.SegmentPath complete(LayoutPathImpl.EndType param1EndType) {
/*     */       LayoutPathImpl.SegmentPath segmentPath;
/* 267 */       if (this.data == null || this.w < 6) {
/* 268 */         return null;
/*     */       }
/*     */       
/* 271 */       if (this.w == this.data.length) {
/* 272 */         segmentPath = new LayoutPathImpl.SegmentPath(this.data, param1EndType);
/* 273 */         reset(0);
/*     */       } else {
/* 275 */         double[] arrayOfDouble = new double[this.w];
/* 276 */         System.arraycopy(this.data, 0, arrayOfDouble, 0, this.w);
/* 277 */         segmentPath = new LayoutPathImpl.SegmentPath(arrayOfDouble, param1EndType);
/* 278 */         reset(2);
/*     */       } 
/*     */       
/* 281 */       return segmentPath;
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
/*     */   public static final class SegmentPath
/*     */     extends LayoutPathImpl
/*     */   {
/*     */     private double[] data;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     LayoutPathImpl.EndType etype;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static SegmentPath get(LayoutPathImpl.EndType param1EndType, double... param1VarArgs) {
/* 317 */       return (new LayoutPathImpl.SegmentPathBuilder()).build(param1EndType, param1VarArgs);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     SegmentPath(double[] param1ArrayOfdouble, LayoutPathImpl.EndType param1EndType) {
/* 325 */       this.data = param1ArrayOfdouble;
/* 326 */       this.etype = param1EndType;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void pathToPoint(Point2D param1Point2D1, boolean param1Boolean, Point2D param1Point2D2) {
/* 334 */       locateAndGetIndex(param1Point2D1, param1Boolean, param1Point2D2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean pointToPath(Point2D param1Point2D1, Point2D param1Point2D2) {
/*     */       // Byte code:
/*     */       //   0: aload_1
/*     */       //   1: invokevirtual getX : ()D
/*     */       //   4: dstore_3
/*     */       //   5: aload_1
/*     */       //   6: invokevirtual getY : ()D
/*     */       //   9: dstore #5
/*     */       //   11: aload_0
/*     */       //   12: getfield data : [D
/*     */       //   15: iconst_0
/*     */       //   16: daload
/*     */       //   17: dstore #7
/*     */       //   19: aload_0
/*     */       //   20: getfield data : [D
/*     */       //   23: iconst_1
/*     */       //   24: daload
/*     */       //   25: dstore #9
/*     */       //   27: aload_0
/*     */       //   28: getfield data : [D
/*     */       //   31: iconst_2
/*     */       //   32: daload
/*     */       //   33: dstore #11
/*     */       //   35: ldc2_w 1.7976931348623157E308
/*     */       //   38: dstore #13
/*     */       //   40: dconst_0
/*     */       //   41: dstore #15
/*     */       //   43: dconst_0
/*     */       //   44: dstore #17
/*     */       //   46: dconst_0
/*     */       //   47: dstore #19
/*     */       //   49: iconst_0
/*     */       //   50: istore #21
/*     */       //   52: iconst_3
/*     */       //   53: istore #22
/*     */       //   55: iload #22
/*     */       //   57: aload_0
/*     */       //   58: getfield data : [D
/*     */       //   61: arraylength
/*     */       //   62: if_icmpge -> 376
/*     */       //   65: aload_0
/*     */       //   66: getfield data : [D
/*     */       //   69: iload #22
/*     */       //   71: daload
/*     */       //   72: dstore #23
/*     */       //   74: aload_0
/*     */       //   75: getfield data : [D
/*     */       //   78: iload #22
/*     */       //   80: iconst_1
/*     */       //   81: iadd
/*     */       //   82: daload
/*     */       //   83: dstore #25
/*     */       //   85: aload_0
/*     */       //   86: getfield data : [D
/*     */       //   89: iload #22
/*     */       //   91: iconst_2
/*     */       //   92: iadd
/*     */       //   93: daload
/*     */       //   94: dstore #27
/*     */       //   96: dload #23
/*     */       //   98: dload #7
/*     */       //   100: dsub
/*     */       //   101: dstore #29
/*     */       //   103: dload #25
/*     */       //   105: dload #9
/*     */       //   107: dsub
/*     */       //   108: dstore #31
/*     */       //   110: dload #27
/*     */       //   112: dload #11
/*     */       //   114: dsub
/*     */       //   115: dstore #33
/*     */       //   117: dload_3
/*     */       //   118: dload #7
/*     */       //   120: dsub
/*     */       //   121: dstore #35
/*     */       //   123: dload #5
/*     */       //   125: dload #9
/*     */       //   127: dsub
/*     */       //   128: dstore #37
/*     */       //   130: dload #29
/*     */       //   132: dload #35
/*     */       //   134: dmul
/*     */       //   135: dload #31
/*     */       //   137: dload #37
/*     */       //   139: dmul
/*     */       //   140: dadd
/*     */       //   141: dstore #39
/*     */       //   143: dload #33
/*     */       //   145: dconst_0
/*     */       //   146: dcmpl
/*     */       //   147: ifeq -> 173
/*     */       //   150: dload #39
/*     */       //   152: dconst_0
/*     */       //   153: dcmpg
/*     */       //   154: ifge -> 192
/*     */       //   157: aload_0
/*     */       //   158: getfield etype : Lsun/font/LayoutPathImpl$EndType;
/*     */       //   161: invokevirtual isExtended : ()Z
/*     */       //   164: ifeq -> 173
/*     */       //   167: iload #22
/*     */       //   169: iconst_3
/*     */       //   170: if_icmpeq -> 192
/*     */       //   173: dload #7
/*     */       //   175: dstore #41
/*     */       //   177: dload #9
/*     */       //   179: dstore #43
/*     */       //   181: dload #11
/*     */       //   183: dstore #45
/*     */       //   185: iload #22
/*     */       //   187: istore #47
/*     */       //   189: goto -> 304
/*     */       //   192: dload #33
/*     */       //   194: dload #33
/*     */       //   196: dmul
/*     */       //   197: dstore #48
/*     */       //   199: dload #39
/*     */       //   201: dload #48
/*     */       //   203: dcmpg
/*     */       //   204: ifle -> 229
/*     */       //   207: aload_0
/*     */       //   208: getfield etype : Lsun/font/LayoutPathImpl$EndType;
/*     */       //   211: invokevirtual isExtended : ()Z
/*     */       //   214: ifeq -> 273
/*     */       //   217: iload #22
/*     */       //   219: aload_0
/*     */       //   220: getfield data : [D
/*     */       //   223: arraylength
/*     */       //   224: iconst_3
/*     */       //   225: isub
/*     */       //   226: if_icmpne -> 273
/*     */       //   229: dload #39
/*     */       //   231: dload #48
/*     */       //   233: ddiv
/*     */       //   234: dstore #50
/*     */       //   236: dload #7
/*     */       //   238: dload #50
/*     */       //   240: dload #29
/*     */       //   242: dmul
/*     */       //   243: dadd
/*     */       //   244: dstore #41
/*     */       //   246: dload #9
/*     */       //   248: dload #50
/*     */       //   250: dload #31
/*     */       //   252: dmul
/*     */       //   253: dadd
/*     */       //   254: dstore #43
/*     */       //   256: dload #11
/*     */       //   258: dload #50
/*     */       //   260: dload #33
/*     */       //   262: dmul
/*     */       //   263: dadd
/*     */       //   264: dstore #45
/*     */       //   266: iload #22
/*     */       //   268: istore #47
/*     */       //   270: goto -> 304
/*     */       //   273: iload #22
/*     */       //   275: aload_0
/*     */       //   276: getfield data : [D
/*     */       //   279: arraylength
/*     */       //   280: iconst_3
/*     */       //   281: isub
/*     */       //   282: if_icmpne -> 358
/*     */       //   285: dload #23
/*     */       //   287: dstore #41
/*     */       //   289: dload #25
/*     */       //   291: dstore #43
/*     */       //   293: dload #27
/*     */       //   295: dstore #45
/*     */       //   297: aload_0
/*     */       //   298: getfield data : [D
/*     */       //   301: arraylength
/*     */       //   302: istore #47
/*     */       //   304: dload_3
/*     */       //   305: dload #41
/*     */       //   307: dsub
/*     */       //   308: dstore #48
/*     */       //   310: dload #5
/*     */       //   312: dload #43
/*     */       //   314: dsub
/*     */       //   315: dstore #50
/*     */       //   317: dload #48
/*     */       //   319: dload #48
/*     */       //   321: dmul
/*     */       //   322: dload #50
/*     */       //   324: dload #50
/*     */       //   326: dmul
/*     */       //   327: dadd
/*     */       //   328: dstore #52
/*     */       //   330: dload #52
/*     */       //   332: dload #13
/*     */       //   334: dcmpg
/*     */       //   335: ifgt -> 358
/*     */       //   338: dload #52
/*     */       //   340: dstore #13
/*     */       //   342: dload #41
/*     */       //   344: dstore #15
/*     */       //   346: dload #43
/*     */       //   348: dstore #17
/*     */       //   350: dload #45
/*     */       //   352: dstore #19
/*     */       //   354: iload #47
/*     */       //   356: istore #21
/*     */       //   358: dload #23
/*     */       //   360: dstore #7
/*     */       //   362: dload #25
/*     */       //   364: dstore #9
/*     */       //   366: dload #27
/*     */       //   368: dstore #11
/*     */       //   370: iinc #22, 3
/*     */       //   373: goto -> 55
/*     */       //   376: aload_0
/*     */       //   377: getfield data : [D
/*     */       //   380: iload #21
/*     */       //   382: iconst_3
/*     */       //   383: isub
/*     */       //   384: daload
/*     */       //   385: dstore #7
/*     */       //   387: aload_0
/*     */       //   388: getfield data : [D
/*     */       //   391: iload #21
/*     */       //   393: iconst_2
/*     */       //   394: isub
/*     */       //   395: daload
/*     */       //   396: dstore #9
/*     */       //   398: dload #15
/*     */       //   400: dload #7
/*     */       //   402: dcmpl
/*     */       //   403: ifne -> 414
/*     */       //   406: dload #17
/*     */       //   408: dload #9
/*     */       //   410: dcmpl
/*     */       //   411: ifeq -> 481
/*     */       //   414: aload_0
/*     */       //   415: getfield data : [D
/*     */       //   418: iload #21
/*     */       //   420: daload
/*     */       //   421: dstore #22
/*     */       //   423: aload_0
/*     */       //   424: getfield data : [D
/*     */       //   427: iload #21
/*     */       //   429: iconst_1
/*     */       //   430: iadd
/*     */       //   431: daload
/*     */       //   432: dstore #24
/*     */       //   434: dload #13
/*     */       //   436: invokestatic sqrt : (D)D
/*     */       //   439: dstore #26
/*     */       //   441: dload_3
/*     */       //   442: dload #15
/*     */       //   444: dsub
/*     */       //   445: dload #24
/*     */       //   447: dload #9
/*     */       //   449: dsub
/*     */       //   450: dmul
/*     */       //   451: dload #5
/*     */       //   453: dload #17
/*     */       //   455: dsub
/*     */       //   456: dload #22
/*     */       //   458: dload #7
/*     */       //   460: dsub
/*     */       //   461: dmul
/*     */       //   462: dcmpl
/*     */       //   463: ifle -> 471
/*     */       //   466: dload #26
/*     */       //   468: dneg
/*     */       //   469: dstore #26
/*     */       //   471: aload_2
/*     */       //   472: dload #19
/*     */       //   474: dload #26
/*     */       //   476: invokevirtual setLocation : (DD)V
/*     */       //   479: iconst_0
/*     */       //   480: ireturn
/*     */       //   481: iload #21
/*     */       //   483: iconst_3
/*     */       //   484: if_icmpeq -> 513
/*     */       //   487: aload_0
/*     */       //   488: getfield data : [D
/*     */       //   491: iload #21
/*     */       //   493: iconst_1
/*     */       //   494: isub
/*     */       //   495: daload
/*     */       //   496: aload_0
/*     */       //   497: getfield data : [D
/*     */       //   500: iload #21
/*     */       //   502: iconst_4
/*     */       //   503: isub
/*     */       //   504: daload
/*     */       //   505: dcmpl
/*     */       //   506: ifeq -> 513
/*     */       //   509: iconst_1
/*     */       //   510: goto -> 514
/*     */       //   513: iconst_0
/*     */       //   514: istore #22
/*     */       //   516: iload #21
/*     */       //   518: aload_0
/*     */       //   519: getfield data : [D
/*     */       //   522: arraylength
/*     */       //   523: if_icmpeq -> 552
/*     */       //   526: aload_0
/*     */       //   527: getfield data : [D
/*     */       //   530: iload #21
/*     */       //   532: iconst_1
/*     */       //   533: isub
/*     */       //   534: daload
/*     */       //   535: aload_0
/*     */       //   536: getfield data : [D
/*     */       //   539: iload #21
/*     */       //   541: iconst_2
/*     */       //   542: iadd
/*     */       //   543: daload
/*     */       //   544: dcmpl
/*     */       //   545: ifeq -> 552
/*     */       //   548: iconst_1
/*     */       //   549: goto -> 553
/*     */       //   552: iconst_0
/*     */       //   553: istore #23
/*     */       //   555: aload_0
/*     */       //   556: getfield etype : Lsun/font/LayoutPathImpl$EndType;
/*     */       //   559: invokevirtual isExtended : ()Z
/*     */       //   562: ifeq -> 585
/*     */       //   565: iload #21
/*     */       //   567: iconst_3
/*     */       //   568: if_icmpeq -> 581
/*     */       //   571: iload #21
/*     */       //   573: aload_0
/*     */       //   574: getfield data : [D
/*     */       //   577: arraylength
/*     */       //   578: if_icmpne -> 585
/*     */       //   581: iconst_1
/*     */       //   582: goto -> 586
/*     */       //   585: iconst_0
/*     */       //   586: istore #24
/*     */       //   588: iload #22
/*     */       //   590: ifeq -> 680
/*     */       //   593: iload #23
/*     */       //   595: ifeq -> 680
/*     */       //   598: new java/awt/geom/Point2D$Double
/*     */       //   601: dup
/*     */       //   602: dload_3
/*     */       //   603: dload #5
/*     */       //   605: invokespecial <init> : (DD)V
/*     */       //   608: astore #25
/*     */       //   610: aload_0
/*     */       //   611: iload #21
/*     */       //   613: iconst_3
/*     */       //   614: isub
/*     */       //   615: iload #24
/*     */       //   617: aload #25
/*     */       //   619: invokespecial calcoffset : (IZLjava/awt/geom/Point2D;)V
/*     */       //   622: new java/awt/geom/Point2D$Double
/*     */       //   625: dup
/*     */       //   626: dload_3
/*     */       //   627: dload #5
/*     */       //   629: invokespecial <init> : (DD)V
/*     */       //   632: astore #26
/*     */       //   634: aload_0
/*     */       //   635: iload #21
/*     */       //   637: iload #24
/*     */       //   639: aload #26
/*     */       //   641: invokespecial calcoffset : (IZLjava/awt/geom/Point2D;)V
/*     */       //   644: aload #25
/*     */       //   646: getfield y : D
/*     */       //   649: invokestatic abs : (D)D
/*     */       //   652: aload #26
/*     */       //   654: getfield y : D
/*     */       //   657: invokestatic abs : (D)D
/*     */       //   660: dcmpl
/*     */       //   661: ifle -> 672
/*     */       //   664: aload_2
/*     */       //   665: aload #25
/*     */       //   667: invokevirtual setLocation : (Ljava/awt/geom/Point2D;)V
/*     */       //   670: iconst_1
/*     */       //   671: ireturn
/*     */       //   672: aload_2
/*     */       //   673: aload #26
/*     */       //   675: invokevirtual setLocation : (Ljava/awt/geom/Point2D;)V
/*     */       //   678: iconst_0
/*     */       //   679: ireturn
/*     */       //   680: iload #22
/*     */       //   682: ifeq -> 705
/*     */       //   685: aload_2
/*     */       //   686: dload_3
/*     */       //   687: dload #5
/*     */       //   689: invokevirtual setLocation : (DD)V
/*     */       //   692: aload_0
/*     */       //   693: iload #21
/*     */       //   695: iconst_3
/*     */       //   696: isub
/*     */       //   697: iload #24
/*     */       //   699: aload_2
/*     */       //   700: invokespecial calcoffset : (IZLjava/awt/geom/Point2D;)V
/*     */       //   703: iconst_1
/*     */       //   704: ireturn
/*     */       //   705: aload_2
/*     */       //   706: dload_3
/*     */       //   707: dload #5
/*     */       //   709: invokevirtual setLocation : (DD)V
/*     */       //   712: aload_0
/*     */       //   713: iload #21
/*     */       //   715: iload #24
/*     */       //   717: aload_2
/*     */       //   718: invokespecial calcoffset : (IZLjava/awt/geom/Point2D;)V
/*     */       //   721: iconst_0
/*     */       //   722: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #397	-> 0
/*     */       //   #398	-> 5
/*     */       //   #400	-> 11
/*     */       //   #401	-> 19
/*     */       //   #402	-> 27
/*     */       //   #405	-> 35
/*     */       //   #406	-> 40
/*     */       //   #407	-> 43
/*     */       //   #408	-> 46
/*     */       //   #409	-> 49
/*     */       //   #411	-> 52
/*     */       //   #412	-> 65
/*     */       //   #413	-> 74
/*     */       //   #414	-> 85
/*     */       //   #416	-> 96
/*     */       //   #417	-> 103
/*     */       //   #418	-> 110
/*     */       //   #420	-> 117
/*     */       //   #421	-> 123
/*     */       //   #426	-> 130
/*     */       //   #430	-> 143
/*     */       //   #432	-> 161
/*     */       //   #434	-> 173
/*     */       //   #435	-> 177
/*     */       //   #436	-> 181
/*     */       //   #437	-> 185
/*     */       //   #439	-> 192
/*     */       //   #440	-> 199
/*     */       //   #441	-> 211
/*     */       //   #443	-> 229
/*     */       //   #444	-> 236
/*     */       //   #445	-> 246
/*     */       //   #446	-> 256
/*     */       //   #447	-> 266
/*     */       //   #448	-> 270
/*     */       //   #449	-> 273
/*     */       //   #450	-> 285
/*     */       //   #451	-> 289
/*     */       //   #452	-> 293
/*     */       //   #453	-> 297
/*     */       //   #460	-> 304
/*     */       //   #461	-> 310
/*     */       //   #462	-> 317
/*     */       //   #463	-> 330
/*     */       //   #464	-> 338
/*     */       //   #465	-> 342
/*     */       //   #466	-> 346
/*     */       //   #467	-> 350
/*     */       //   #468	-> 354
/*     */       //   #472	-> 358
/*     */       //   #473	-> 362
/*     */       //   #474	-> 366
/*     */       //   #411	-> 370
/*     */       //   #478	-> 376
/*     */       //   #479	-> 387
/*     */       //   #480	-> 398
/*     */       //   #481	-> 414
/*     */       //   #482	-> 423
/*     */       //   #483	-> 434
/*     */       //   #484	-> 441
/*     */       //   #485	-> 466
/*     */       //   #487	-> 471
/*     */       //   #488	-> 479
/*     */       //   #490	-> 481
/*     */       //   #491	-> 516
/*     */       //   #492	-> 555
/*     */       //   #493	-> 588
/*     */       //   #494	-> 598
/*     */       //   #495	-> 610
/*     */       //   #496	-> 622
/*     */       //   #497	-> 634
/*     */       //   #498	-> 644
/*     */       //   #499	-> 664
/*     */       //   #500	-> 670
/*     */       //   #502	-> 672
/*     */       //   #503	-> 678
/*     */       //   #505	-> 680
/*     */       //   #506	-> 685
/*     */       //   #507	-> 692
/*     */       //   #508	-> 703
/*     */       //   #510	-> 705
/*     */       //   #511	-> 712
/*     */       //   #512	-> 721
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void calcoffset(int param1Int, boolean param1Boolean, Point2D param1Point2D) {
/* 525 */       double d1 = this.data[param1Int - 3];
/* 526 */       double d2 = this.data[param1Int - 2];
/* 527 */       double d3 = param1Point2D.getX() - d1;
/* 528 */       double d4 = param1Point2D.getY() - d2;
/* 529 */       double d5 = this.data[param1Int] - d1;
/* 530 */       double d6 = this.data[param1Int + 1] - d2;
/* 531 */       double d7 = this.data[param1Int + 2] - this.data[param1Int - 1];
/*     */ 
/*     */ 
/*     */       
/* 535 */       double d8 = (d3 * d5 + d4 * d6) / d7;
/* 536 */       double d9 = (d3 * -d6 + d4 * d5) / d7;
/* 537 */       if (!param1Boolean)
/* 538 */         if (d8 < 0.0D) { d8 = 0.0D; }
/* 539 */         else if (d8 > d7) { d8 = d7; }
/*     */          
/* 541 */       d8 += this.data[param1Int - 1];
/* 542 */       param1Point2D.setLocation(d8, d9);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Shape mapShape(Shape param1Shape) {
/* 550 */       return (new Mapper()).mapShape(param1Shape);
/*     */     }
/*     */     
/*     */     public double start() {
/* 554 */       return this.data[2];
/*     */     }
/*     */     
/*     */     public double end() {
/* 558 */       return this.data[this.data.length - 1];
/*     */     }
/*     */     
/*     */     public double length() {
/* 562 */       return this.data[this.data.length - 1] - this.data[2];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private double getClosedAdvance(double param1Double, boolean param1Boolean) {
/* 573 */       if (this.etype.isClosed()) {
/* 574 */         param1Double -= this.data[2];
/* 575 */         int i = (int)(param1Double / length());
/* 576 */         param1Double -= i * length();
/* 577 */         if (param1Double < 0.0D || (param1Double == 0.0D && param1Boolean)) {
/* 578 */           param1Double += length();
/*     */         }
/*     */         
/* 581 */         param1Double += this.data[2];
/*     */       } 
/* 583 */       return param1Double;
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
/*     */     private int getSegmentIndexForAdvance(double param1Double, boolean param1Boolean) {
/* 605 */       param1Double = getClosedAdvance(param1Double, param1Boolean);
/*     */       
/*     */       byte b;
/*     */       
/*     */       int i;
/* 610 */       for (b = 5, i = this.data.length - 1; b < i; b += 3) {
/* 611 */         double d = this.data[b];
/* 612 */         if (param1Double < d || (param1Double == d && param1Boolean)) {
/*     */           break;
/*     */         }
/*     */       } 
/* 616 */       return b - 2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void map(int param1Int, double param1Double1, double param1Double2, Point2D param1Point2D) {
/* 625 */       double d1 = this.data[param1Int] - this.data[param1Int - 3];
/* 626 */       double d2 = this.data[param1Int + 1] - this.data[param1Int - 2];
/* 627 */       double d3 = this.data[param1Int + 2] - this.data[param1Int - 1];
/*     */       
/* 629 */       double d4 = d1 / d3;
/* 630 */       double d5 = d2 / d3;
/*     */       
/* 632 */       param1Double1 -= this.data[param1Int - 1];
/*     */       
/* 634 */       param1Point2D.setLocation(this.data[param1Int - 3] + param1Double1 * d4 - param1Double2 * d5, this.data[param1Int - 2] + param1Double1 * d5 + param1Double2 * d4);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int locateAndGetIndex(Point2D param1Point2D1, boolean param1Boolean, Point2D param1Point2D2) {
/* 642 */       double d1 = param1Point2D1.getX();
/* 643 */       double d2 = param1Point2D1.getY();
/* 644 */       int i = getSegmentIndexForAdvance(d1, param1Boolean);
/* 645 */       map(i, d1, d2, param1Point2D2);
/*     */       
/* 647 */       return i;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     class LineInfo
/*     */     {
/*     */       double sx;
/*     */ 
/*     */       
/*     */       double sy;
/*     */ 
/*     */       
/*     */       double lx;
/*     */ 
/*     */       
/*     */       double ly;
/*     */ 
/*     */       
/*     */       double m;
/*     */ 
/*     */ 
/*     */       
/*     */       void set(double param2Double1, double param2Double2, double param2Double3, double param2Double4) {
/* 671 */         this.sx = param2Double1;
/* 672 */         this.sy = param2Double2;
/* 673 */         this.lx = param2Double3;
/* 674 */         this.ly = param2Double4;
/* 675 */         double d = param2Double3 - param2Double1;
/* 676 */         if (d == 0.0D) {
/* 677 */           this.m = 0.0D;
/*     */         } else {
/* 679 */           double d1 = param2Double4 - param2Double2;
/* 680 */           this.m = d1 / d;
/*     */         } 
/*     */       }
/*     */       
/*     */       void set(LineInfo param2LineInfo) {
/* 685 */         this.sx = param2LineInfo.sx;
/* 686 */         this.sy = param2LineInfo.sy;
/* 687 */         this.lx = param2LineInfo.lx;
/* 688 */         this.ly = param2LineInfo.ly;
/* 689 */         this.m = param2LineInfo.m;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       boolean pin(double param2Double1, double param2Double2, LineInfo param2LineInfo) {
/* 698 */         param2LineInfo.set(this);
/* 699 */         if (this.lx >= this.sx) {
/* 700 */           if (this.sx < param2Double2 && this.lx >= param2Double1) {
/* 701 */             if (this.sx < param2Double1) {
/* 702 */               if (this.m != 0.0D) this.sy += this.m * (param2Double1 - this.sx); 
/* 703 */               param2LineInfo.sx = param2Double1;
/*     */             } 
/* 705 */             if (this.lx > param2Double2) {
/* 706 */               if (this.m != 0.0D) this.ly += this.m * (param2Double2 - this.lx); 
/* 707 */               param2LineInfo.lx = param2Double2;
/*     */             } 
/* 709 */             return true;
/*     */           }
/*     */         
/* 712 */         } else if (this.lx < param2Double2 && this.sx >= param2Double1) {
/* 713 */           if (this.lx < param2Double1) {
/* 714 */             if (this.m != 0.0D) this.ly += this.m * (param2Double1 - this.lx); 
/* 715 */             param2LineInfo.lx = param2Double1;
/*     */           } 
/* 717 */           if (this.sx > param2Double2) {
/* 718 */             if (this.m != 0.0D) this.sy += this.m * (param2Double2 - this.sx); 
/* 719 */             param2LineInfo.sx = param2Double2;
/*     */           } 
/* 721 */           return true;
/*     */         } 
/*     */         
/* 724 */         return false;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       boolean pin(int param2Int, LineInfo param2LineInfo) {
/* 733 */         double d1 = LayoutPathImpl.SegmentPath.this.data[param2Int - 1];
/* 734 */         double d2 = LayoutPathImpl.SegmentPath.this.data[param2Int + 2];
/* 735 */         switch (LayoutPathImpl.SegmentPath.this.etype) {
/*     */ 
/*     */           
/*     */           case EXTENDED:
/* 739 */             if (param2Int == 3) d1 = Double.NEGATIVE_INFINITY; 
/* 740 */             if (param2Int == LayoutPathImpl.SegmentPath.this.data.length - 3) d2 = Double.POSITIVE_INFINITY;
/*     */             
/*     */             break;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 747 */         return pin(d1, d2, param2LineInfo);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     class Segment
/*     */     {
/*     */       final int ix;
/*     */       
/*     */       final double ux;
/*     */       
/*     */       final double uy;
/*     */       final LayoutPathImpl.SegmentPath.LineInfo temp;
/*     */       boolean broken;
/*     */       double cx;
/*     */       double cy;
/*     */       GeneralPath gp;
/*     */       
/*     */       Segment(int param2Int) {
/* 766 */         this.ix = param2Int;
/* 767 */         double d = LayoutPathImpl.SegmentPath.this.data[param2Int + 2] - LayoutPathImpl.SegmentPath.this.data[param2Int - 1];
/* 768 */         this.ux = (LayoutPathImpl.SegmentPath.this.data[param2Int] - LayoutPathImpl.SegmentPath.this.data[param2Int - 3]) / d;
/* 769 */         this.uy = (LayoutPathImpl.SegmentPath.this.data[param2Int + 1] - LayoutPathImpl.SegmentPath.this.data[param2Int - 2]) / d;
/* 770 */         this.temp = new LayoutPathImpl.SegmentPath.LineInfo();
/*     */       }
/*     */ 
/*     */       
/*     */       void init() {
/* 775 */         this.broken = true;
/* 776 */         this.cx = this.cy = Double.MIN_VALUE;
/* 777 */         this.gp = new GeneralPath();
/*     */       }
/*     */ 
/*     */       
/*     */       void move() {
/* 782 */         this.broken = true;
/*     */       }
/*     */       
/*     */       void close() {
/* 786 */         if (!this.broken)
/*     */         {
/* 788 */           this.gp.closePath();
/*     */         }
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       void line(LayoutPathImpl.SegmentPath.LineInfo param2LineInfo) {
/* 795 */         if (param2LineInfo.pin(this.ix, this.temp)) {
/*     */ 
/*     */           
/* 798 */           this.temp.sx -= LayoutPathImpl.SegmentPath.this.data[this.ix - 1];
/* 799 */           double d1 = LayoutPathImpl.SegmentPath.this.data[this.ix - 3] + this.temp.sx * this.ux - this.temp.sy * this.uy;
/* 800 */           double d2 = LayoutPathImpl.SegmentPath.this.data[this.ix - 2] + this.temp.sx * this.uy + this.temp.sy * this.ux;
/* 801 */           this.temp.lx -= LayoutPathImpl.SegmentPath.this.data[this.ix - 1];
/* 802 */           double d3 = LayoutPathImpl.SegmentPath.this.data[this.ix - 3] + this.temp.lx * this.ux - this.temp.ly * this.uy;
/* 803 */           double d4 = LayoutPathImpl.SegmentPath.this.data[this.ix - 2] + this.temp.lx * this.uy + this.temp.ly * this.ux;
/*     */ 
/*     */ 
/*     */           
/* 807 */           if (d1 != this.cx || d2 != this.cy) {
/* 808 */             if (this.broken) {
/*     */               
/* 810 */               this.gp.moveTo((float)d1, (float)d2);
/*     */             } else {
/*     */               
/* 813 */               this.gp.lineTo((float)d1, (float)d2);
/*     */             } 
/*     */           }
/*     */           
/* 817 */           this.gp.lineTo((float)d3, (float)d4);
/*     */           
/* 819 */           this.broken = false;
/* 820 */           this.cx = d3;
/* 821 */           this.cy = d4;
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     class Mapper
/*     */     {
/* 834 */       final LayoutPathImpl.SegmentPath.LineInfo li = new LayoutPathImpl.SegmentPath.LineInfo();
/* 835 */       final ArrayList<LayoutPathImpl.SegmentPath.Segment> segments = new ArrayList<>(); final Point2D.Double mpt; Mapper() {
/* 836 */         for (byte b = 3; b < LayoutPathImpl.SegmentPath.this.data.length; b += 3) {
/* 837 */           if (LayoutPathImpl.SegmentPath.this.data[b + 2] != LayoutPathImpl.SegmentPath.this.data[b - 1]) {
/* 838 */             this.segments.add(new LayoutPathImpl.SegmentPath.Segment(b));
/*     */           }
/*     */         } 
/*     */         
/* 842 */         this.mpt = new Point2D.Double();
/* 843 */         this.cpt = new Point2D.Double();
/*     */       }
/*     */       final Point2D.Double cpt; boolean haveMT;
/*     */       
/*     */       void init() {
/* 848 */         this.haveMT = false;
/* 849 */         for (LayoutPathImpl.SegmentPath.Segment segment : this.segments) {
/* 850 */           segment.init();
/*     */         }
/*     */       }
/*     */ 
/*     */       
/*     */       void moveTo(double param2Double1, double param2Double2) {
/* 856 */         this.mpt.x = param2Double1;
/* 857 */         this.mpt.y = param2Double2;
/* 858 */         this.haveMT = true;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       void lineTo(double param2Double1, double param2Double2) {
/* 864 */         if (this.haveMT) {
/*     */           
/* 866 */           this.cpt.x = this.mpt.x;
/* 867 */           this.cpt.y = this.mpt.y;
/*     */         } 
/*     */         
/* 870 */         if (param2Double1 == this.cpt.x && param2Double2 == this.cpt.y) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 875 */         if (this.haveMT) {
/*     */           
/* 877 */           this.haveMT = false;
/* 878 */           for (LayoutPathImpl.SegmentPath.Segment segment : this.segments) {
/* 879 */             segment.move();
/*     */           }
/*     */         } 
/*     */         
/* 883 */         this.li.set(this.cpt.x, this.cpt.y, param2Double1, param2Double2);
/* 884 */         for (LayoutPathImpl.SegmentPath.Segment segment : this.segments) {
/* 885 */           segment.line(this.li);
/*     */         }
/*     */         
/* 888 */         this.cpt.x = param2Double1;
/* 889 */         this.cpt.y = param2Double2;
/*     */       }
/*     */ 
/*     */       
/*     */       void close() {
/* 894 */         lineTo(this.mpt.x, this.mpt.y);
/* 895 */         for (LayoutPathImpl.SegmentPath.Segment segment : this.segments) {
/* 896 */           segment.close();
/*     */         }
/*     */       }
/*     */ 
/*     */       
/*     */       public Shape mapShape(Shape param2Shape) {
/* 902 */         PathIterator pathIterator = param2Shape.getPathIterator(null, 1.0D);
/*     */ 
/*     */         
/* 905 */         init();
/*     */         
/* 907 */         double[] arrayOfDouble = new double[2];
/* 908 */         while (!pathIterator.isDone()) {
/* 909 */           switch (pathIterator.currentSegment(arrayOfDouble)) { case 4:
/* 910 */               close(); break;
/* 911 */             case 0: moveTo(arrayOfDouble[0], arrayOfDouble[1]); break;
/* 912 */             case 1: lineTo(arrayOfDouble[0], arrayOfDouble[1]);
/*     */               break; }
/*     */ 
/*     */           
/* 916 */           pathIterator.next();
/*     */         } 
/*     */ 
/*     */         
/* 920 */         GeneralPath generalPath = new GeneralPath();
/* 921 */         for (LayoutPathImpl.SegmentPath.Segment segment : this.segments) {
/* 922 */           generalPath.append(segment.gp, false);
/*     */         }
/* 924 */         return generalPath;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 933 */       StringBuilder stringBuilder = new StringBuilder();
/* 934 */       stringBuilder.append("{");
/* 935 */       stringBuilder.append(this.etype.toString());
/* 936 */       stringBuilder.append(" ");
/* 937 */       for (byte b = 0; b < this.data.length; b += 3) {
/* 938 */         if (b > 0) {
/* 939 */           stringBuilder.append(",");
/*     */         }
/* 941 */         float f1 = (int)(this.data[b] * 100.0D) / 100.0F;
/* 942 */         float f2 = (int)(this.data[b + 1] * 100.0D) / 100.0F;
/* 943 */         float f3 = (int)(this.data[b + 2] * 10.0D) / 10.0F;
/* 944 */         stringBuilder.append("{");
/* 945 */         stringBuilder.append(f1);
/* 946 */         stringBuilder.append(",");
/* 947 */         stringBuilder.append(f2);
/* 948 */         stringBuilder.append(",");
/* 949 */         stringBuilder.append(f3);
/* 950 */         stringBuilder.append("}");
/*     */       } 
/* 952 */       stringBuilder.append("}");
/* 953 */       return stringBuilder.toString();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class EmptyPath
/*     */     extends LayoutPathImpl {
/*     */     private AffineTransform tx;
/*     */     
/*     */     public EmptyPath(AffineTransform param1AffineTransform) {
/* 962 */       this.tx = param1AffineTransform;
/*     */     }
/*     */     
/*     */     public void pathToPoint(Point2D param1Point2D1, boolean param1Boolean, Point2D param1Point2D2) {
/* 966 */       if (this.tx != null) {
/* 967 */         this.tx.transform(param1Point2D1, param1Point2D2);
/*     */       } else {
/* 969 */         param1Point2D2.setLocation(param1Point2D1);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean pointToPath(Point2D param1Point2D1, Point2D param1Point2D2) {
/* 974 */       param1Point2D2.setLocation(param1Point2D1);
/* 975 */       if (this.tx != null) {
/*     */         try {
/* 977 */           this.tx.inverseTransform(param1Point2D1, param1Point2D2);
/*     */         }
/* 979 */         catch (NoninvertibleTransformException noninvertibleTransformException) {}
/*     */       }
/*     */       
/* 982 */       return (param1Point2D2.getX() > 0.0D);
/*     */     }
/*     */     public double start() {
/* 985 */       return 0.0D;
/*     */     } public double end() {
/* 987 */       return 0.0D;
/*     */     } public double length() {
/* 989 */       return 0.0D;
/*     */     }
/*     */     public Shape mapShape(Shape param1Shape) {
/* 992 */       if (this.tx != null) {
/* 993 */         return this.tx.createTransformedShape(param1Shape);
/*     */       }
/* 995 */       return param1Shape;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/LayoutPathImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */