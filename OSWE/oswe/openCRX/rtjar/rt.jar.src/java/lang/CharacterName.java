/*     */ package java.lang;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class CharacterName
/*     */ {
/*     */   private static SoftReference<byte[]> refStrPool;
/*     */   private static int[][] lookup;
/*     */   
/*     */   private static synchronized byte[] initNamePool() {
/*     */     // Byte code:
/*     */     //   0: aconst_null
/*     */     //   1: astore_0
/*     */     //   2: getstatic java/lang/CharacterName.refStrPool : Ljava/lang/ref/SoftReference;
/*     */     //   5: ifnull -> 24
/*     */     //   8: getstatic java/lang/CharacterName.refStrPool : Ljava/lang/ref/SoftReference;
/*     */     //   11: invokevirtual get : ()Ljava/lang/Object;
/*     */     //   14: checkcast [B
/*     */     //   17: dup
/*     */     //   18: astore_0
/*     */     //   19: ifnull -> 24
/*     */     //   22: aload_0
/*     */     //   23: areturn
/*     */     //   24: aconst_null
/*     */     //   25: astore_1
/*     */     //   26: new java/io/DataInputStream
/*     */     //   29: dup
/*     */     //   30: new java/util/zip/InflaterInputStream
/*     */     //   33: dup
/*     */     //   34: new java/lang/CharacterName$1
/*     */     //   37: dup
/*     */     //   38: invokespecial <init> : ()V
/*     */     //   41: invokestatic doPrivileged : (Ljava/security/PrivilegedAction;)Ljava/lang/Object;
/*     */     //   44: checkcast java/io/InputStream
/*     */     //   47: invokespecial <init> : (Ljava/io/InputStream;)V
/*     */     //   50: invokespecial <init> : (Ljava/io/InputStream;)V
/*     */     //   53: astore_1
/*     */     //   54: sipush #4352
/*     */     //   57: anewarray [I
/*     */     //   60: putstatic java/lang/CharacterName.lookup : [[I
/*     */     //   63: aload_1
/*     */     //   64: invokevirtual readInt : ()I
/*     */     //   67: istore_2
/*     */     //   68: aload_1
/*     */     //   69: invokevirtual readInt : ()I
/*     */     //   72: istore_3
/*     */     //   73: iload_3
/*     */     //   74: newarray byte
/*     */     //   76: astore #4
/*     */     //   78: aload_1
/*     */     //   79: aload #4
/*     */     //   81: invokevirtual readFully : ([B)V
/*     */     //   84: iconst_0
/*     */     //   85: istore #5
/*     */     //   87: iconst_0
/*     */     //   88: istore #6
/*     */     //   90: iconst_0
/*     */     //   91: istore #7
/*     */     //   93: aload #4
/*     */     //   95: iload #6
/*     */     //   97: iinc #6, 1
/*     */     //   100: baload
/*     */     //   101: sipush #255
/*     */     //   104: iand
/*     */     //   105: istore #8
/*     */     //   107: iload #8
/*     */     //   109: ifne -> 175
/*     */     //   112: aload #4
/*     */     //   114: iload #6
/*     */     //   116: iinc #6, 1
/*     */     //   119: baload
/*     */     //   120: sipush #255
/*     */     //   123: iand
/*     */     //   124: istore #8
/*     */     //   126: aload #4
/*     */     //   128: iload #6
/*     */     //   130: iinc #6, 1
/*     */     //   133: baload
/*     */     //   134: sipush #255
/*     */     //   137: iand
/*     */     //   138: bipush #16
/*     */     //   140: ishl
/*     */     //   141: aload #4
/*     */     //   143: iload #6
/*     */     //   145: iinc #6, 1
/*     */     //   148: baload
/*     */     //   149: sipush #255
/*     */     //   152: iand
/*     */     //   153: bipush #8
/*     */     //   155: ishl
/*     */     //   156: ior
/*     */     //   157: aload #4
/*     */     //   159: iload #6
/*     */     //   161: iinc #6, 1
/*     */     //   164: baload
/*     */     //   165: sipush #255
/*     */     //   168: iand
/*     */     //   169: ior
/*     */     //   170: istore #7
/*     */     //   172: goto -> 178
/*     */     //   175: iinc #7, 1
/*     */     //   178: iload #7
/*     */     //   180: bipush #8
/*     */     //   182: ishr
/*     */     //   183: istore #9
/*     */     //   185: getstatic java/lang/CharacterName.lookup : [[I
/*     */     //   188: iload #9
/*     */     //   190: aaload
/*     */     //   191: ifnonnull -> 205
/*     */     //   194: getstatic java/lang/CharacterName.lookup : [[I
/*     */     //   197: iload #9
/*     */     //   199: sipush #256
/*     */     //   202: newarray int
/*     */     //   204: aastore
/*     */     //   205: getstatic java/lang/CharacterName.lookup : [[I
/*     */     //   208: iload #9
/*     */     //   210: aaload
/*     */     //   211: iload #7
/*     */     //   213: sipush #255
/*     */     //   216: iand
/*     */     //   217: iload #5
/*     */     //   219: bipush #8
/*     */     //   221: ishl
/*     */     //   222: iload #8
/*     */     //   224: ior
/*     */     //   225: iastore
/*     */     //   226: iload #5
/*     */     //   228: iload #8
/*     */     //   230: iadd
/*     */     //   231: istore #5
/*     */     //   233: iload #6
/*     */     //   235: iload_3
/*     */     //   236: if_icmplt -> 93
/*     */     //   239: iload_2
/*     */     //   240: iload_3
/*     */     //   241: isub
/*     */     //   242: newarray byte
/*     */     //   244: astore_0
/*     */     //   245: aload_1
/*     */     //   246: aload_0
/*     */     //   247: invokevirtual readFully : ([B)V
/*     */     //   250: new java/lang/ref/SoftReference
/*     */     //   253: dup
/*     */     //   254: aload_0
/*     */     //   255: invokespecial <init> : (Ljava/lang/Object;)V
/*     */     //   258: putstatic java/lang/CharacterName.refStrPool : Ljava/lang/ref/SoftReference;
/*     */     //   261: aload_1
/*     */     //   262: ifnull -> 269
/*     */     //   265: aload_1
/*     */     //   266: invokevirtual close : ()V
/*     */     //   269: goto -> 308
/*     */     //   272: astore_2
/*     */     //   273: goto -> 308
/*     */     //   276: astore_2
/*     */     //   277: new java/lang/InternalError
/*     */     //   280: dup
/*     */     //   281: aload_2
/*     */     //   282: invokevirtual getMessage : ()Ljava/lang/String;
/*     */     //   285: aload_2
/*     */     //   286: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
/*     */     //   289: athrow
/*     */     //   290: astore #10
/*     */     //   292: aload_1
/*     */     //   293: ifnull -> 300
/*     */     //   296: aload_1
/*     */     //   297: invokevirtual close : ()V
/*     */     //   300: goto -> 305
/*     */     //   303: astore #11
/*     */     //   305: aload #10
/*     */     //   307: athrow
/*     */     //   308: aload_0
/*     */     //   309: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #42	-> 0
/*     */     //   #43	-> 2
/*     */     //   #44	-> 22
/*     */     //   #45	-> 24
/*     */     //   #47	-> 26
/*     */     //   #48	-> 41
/*     */     //   #55	-> 54
/*     */     //   #56	-> 63
/*     */     //   #57	-> 68
/*     */     //   #58	-> 73
/*     */     //   #59	-> 78
/*     */     //   #61	-> 84
/*     */     //   #62	-> 87
/*     */     //   #63	-> 90
/*     */     //   #65	-> 93
/*     */     //   #66	-> 107
/*     */     //   #67	-> 112
/*     */     //   #69	-> 126
/*     */     //   #73	-> 175
/*     */     //   #75	-> 178
/*     */     //   #76	-> 185
/*     */     //   #77	-> 194
/*     */     //   #79	-> 205
/*     */     //   #80	-> 226
/*     */     //   #81	-> 233
/*     */     //   #82	-> 239
/*     */     //   #83	-> 245
/*     */     //   #84	-> 250
/*     */     //   #89	-> 261
/*     */     //   #90	-> 265
/*     */     //   #91	-> 269
/*     */     //   #92	-> 273
/*     */     //   #85	-> 276
/*     */     //   #86	-> 277
/*     */     //   #88	-> 290
/*     */     //   #89	-> 292
/*     */     //   #90	-> 296
/*     */     //   #91	-> 300
/*     */     //   #92	-> 305
/*     */     //   #93	-> 308
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   26	261	276	java/lang/Exception
/*     */     //   26	261	290	finally
/*     */     //   261	269	272	java/lang/Exception
/*     */     //   276	292	290	finally
/*     */     //   292	300	303	java/lang/Exception
/*     */   }
/*     */   
/*     */   public static String get(int paramInt) {
/*  97 */     byte[] arrayOfByte = null;
/*  98 */     if (refStrPool == null || (arrayOfByte = refStrPool.get()) == null)
/*  99 */       arrayOfByte = initNamePool(); 
/* 100 */     int i = 0;
/* 101 */     if (lookup[paramInt >> 8] == null || (i = lookup[paramInt >> 8][paramInt & 0xFF]) == 0)
/*     */     {
/* 103 */       return null;
/*     */     }
/* 105 */     return new String(arrayOfByte, 0, i >>> 8, i & 0xFF);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/CharacterName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */