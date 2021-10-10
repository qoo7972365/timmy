/*     */ package javax.sql.rowset.serial;
/*     */ 
/*     */ import java.io.CharArrayReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.Serializable;
/*     */ import java.io.Writer;
/*     */ import java.sql.Clob;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SerialClob
/*     */   implements Clob, Serializable, Cloneable
/*     */ {
/*     */   private char[] buf;
/*     */   private Clob clob;
/*     */   private long len;
/*     */   private long origLen;
/*     */   static final long serialVersionUID = -1662519690087375313L;
/*     */   
/*     */   public SerialClob(char[] paramArrayOfchar) throws SerialException, SQLException {
/* 107 */     this.len = paramArrayOfchar.length;
/* 108 */     this.buf = new char[(int)this.len];
/* 109 */     for (byte b = 0; b < this.len; b++) {
/* 110 */       this.buf[b] = paramArrayOfchar[b];
/*     */     }
/* 112 */     this.origLen = this.len;
/* 113 */     this.clob = null;
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
/*     */   public SerialClob(Clob paramClob) throws SerialException, SQLException {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: invokespecial <init> : ()V
/*     */     //   4: aload_1
/*     */     //   5: ifnonnull -> 18
/*     */     //   8: new java/sql/SQLException
/*     */     //   11: dup
/*     */     //   12: ldc 'Cannot instantiate a SerialClob object with a null Clob object'
/*     */     //   14: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   17: athrow
/*     */     //   18: aload_0
/*     */     //   19: aload_1
/*     */     //   20: invokeinterface length : ()J
/*     */     //   25: putfield len : J
/*     */     //   28: aload_0
/*     */     //   29: aload_1
/*     */     //   30: putfield clob : Ljava/sql/Clob;
/*     */     //   33: aload_0
/*     */     //   34: aload_0
/*     */     //   35: getfield len : J
/*     */     //   38: l2i
/*     */     //   39: newarray char
/*     */     //   41: putfield buf : [C
/*     */     //   44: iconst_0
/*     */     //   45: istore_2
/*     */     //   46: iconst_0
/*     */     //   47: istore_3
/*     */     //   48: aload_1
/*     */     //   49: invokeinterface getCharacterStream : ()Ljava/io/Reader;
/*     */     //   54: astore #4
/*     */     //   56: aconst_null
/*     */     //   57: astore #5
/*     */     //   59: aload #4
/*     */     //   61: ifnonnull -> 74
/*     */     //   64: new java/sql/SQLException
/*     */     //   67: dup
/*     */     //   68: ldc 'Invalid Clob object. The call to getCharacterStream returned null which cannot be serialized.'
/*     */     //   70: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   73: athrow
/*     */     //   74: aload_1
/*     */     //   75: invokeinterface getAsciiStream : ()Ljava/io/InputStream;
/*     */     //   80: astore #6
/*     */     //   82: aconst_null
/*     */     //   83: astore #7
/*     */     //   85: aload #6
/*     */     //   87: ifnonnull -> 100
/*     */     //   90: new java/sql/SQLException
/*     */     //   93: dup
/*     */     //   94: ldc 'Invalid Clob object. The call to getAsciiStream returned null which cannot be serialized.'
/*     */     //   96: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   99: athrow
/*     */     //   100: aload #6
/*     */     //   102: ifnull -> 187
/*     */     //   105: aload #7
/*     */     //   107: ifnull -> 130
/*     */     //   110: aload #6
/*     */     //   112: invokevirtual close : ()V
/*     */     //   115: goto -> 187
/*     */     //   118: astore #8
/*     */     //   120: aload #7
/*     */     //   122: aload #8
/*     */     //   124: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
/*     */     //   127: goto -> 187
/*     */     //   130: aload #6
/*     */     //   132: invokevirtual close : ()V
/*     */     //   135: goto -> 187
/*     */     //   138: astore #8
/*     */     //   140: aload #8
/*     */     //   142: astore #7
/*     */     //   144: aload #8
/*     */     //   146: athrow
/*     */     //   147: astore #9
/*     */     //   149: aload #6
/*     */     //   151: ifnull -> 184
/*     */     //   154: aload #7
/*     */     //   156: ifnull -> 179
/*     */     //   159: aload #6
/*     */     //   161: invokevirtual close : ()V
/*     */     //   164: goto -> 184
/*     */     //   167: astore #10
/*     */     //   169: aload #7
/*     */     //   171: aload #10
/*     */     //   173: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
/*     */     //   176: goto -> 184
/*     */     //   179: aload #6
/*     */     //   181: invokevirtual close : ()V
/*     */     //   184: aload #9
/*     */     //   186: athrow
/*     */     //   187: new java/io/BufferedReader
/*     */     //   190: dup
/*     */     //   191: aload #4
/*     */     //   193: invokespecial <init> : (Ljava/io/Reader;)V
/*     */     //   196: astore #6
/*     */     //   198: aconst_null
/*     */     //   199: astore #7
/*     */     //   201: aload #6
/*     */     //   203: aload_0
/*     */     //   204: getfield buf : [C
/*     */     //   207: iload_3
/*     */     //   208: aload_0
/*     */     //   209: getfield len : J
/*     */     //   212: iload_3
/*     */     //   213: i2l
/*     */     //   214: lsub
/*     */     //   215: l2i
/*     */     //   216: invokevirtual read : ([CII)I
/*     */     //   219: istore_2
/*     */     //   220: iload_3
/*     */     //   221: iload_2
/*     */     //   222: iadd
/*     */     //   223: istore_3
/*     */     //   224: iload_2
/*     */     //   225: ifgt -> 201
/*     */     //   228: aload #6
/*     */     //   230: ifnull -> 315
/*     */     //   233: aload #7
/*     */     //   235: ifnull -> 258
/*     */     //   238: aload #6
/*     */     //   240: invokevirtual close : ()V
/*     */     //   243: goto -> 315
/*     */     //   246: astore #8
/*     */     //   248: aload #7
/*     */     //   250: aload #8
/*     */     //   252: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
/*     */     //   255: goto -> 315
/*     */     //   258: aload #6
/*     */     //   260: invokevirtual close : ()V
/*     */     //   263: goto -> 315
/*     */     //   266: astore #8
/*     */     //   268: aload #8
/*     */     //   270: astore #7
/*     */     //   272: aload #8
/*     */     //   274: athrow
/*     */     //   275: astore #11
/*     */     //   277: aload #6
/*     */     //   279: ifnull -> 312
/*     */     //   282: aload #7
/*     */     //   284: ifnull -> 307
/*     */     //   287: aload #6
/*     */     //   289: invokevirtual close : ()V
/*     */     //   292: goto -> 312
/*     */     //   295: astore #12
/*     */     //   297: aload #7
/*     */     //   299: aload #12
/*     */     //   301: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
/*     */     //   304: goto -> 312
/*     */     //   307: aload #6
/*     */     //   309: invokevirtual close : ()V
/*     */     //   312: aload #11
/*     */     //   314: athrow
/*     */     //   315: aload #4
/*     */     //   317: ifnull -> 402
/*     */     //   320: aload #5
/*     */     //   322: ifnull -> 345
/*     */     //   325: aload #4
/*     */     //   327: invokevirtual close : ()V
/*     */     //   330: goto -> 402
/*     */     //   333: astore #6
/*     */     //   335: aload #5
/*     */     //   337: aload #6
/*     */     //   339: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
/*     */     //   342: goto -> 402
/*     */     //   345: aload #4
/*     */     //   347: invokevirtual close : ()V
/*     */     //   350: goto -> 402
/*     */     //   353: astore #6
/*     */     //   355: aload #6
/*     */     //   357: astore #5
/*     */     //   359: aload #6
/*     */     //   361: athrow
/*     */     //   362: astore #13
/*     */     //   364: aload #4
/*     */     //   366: ifnull -> 399
/*     */     //   369: aload #5
/*     */     //   371: ifnull -> 394
/*     */     //   374: aload #4
/*     */     //   376: invokevirtual close : ()V
/*     */     //   379: goto -> 399
/*     */     //   382: astore #14
/*     */     //   384: aload #5
/*     */     //   386: aload #14
/*     */     //   388: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
/*     */     //   391: goto -> 399
/*     */     //   394: aload #4
/*     */     //   396: invokevirtual close : ()V
/*     */     //   399: aload #13
/*     */     //   401: athrow
/*     */     //   402: goto -> 438
/*     */     //   405: astore #4
/*     */     //   407: new javax/sql/rowset/serial/SerialException
/*     */     //   410: dup
/*     */     //   411: new java/lang/StringBuilder
/*     */     //   414: dup
/*     */     //   415: invokespecial <init> : ()V
/*     */     //   418: ldc 'SerialClob: '
/*     */     //   420: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   423: aload #4
/*     */     //   425: invokevirtual getMessage : ()Ljava/lang/String;
/*     */     //   428: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   431: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   434: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   437: athrow
/*     */     //   438: aload_0
/*     */     //   439: aload_0
/*     */     //   440: getfield len : J
/*     */     //   443: putfield origLen : J
/*     */     //   446: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #142	-> 0
/*     */     //   #144	-> 4
/*     */     //   #145	-> 8
/*     */     //   #148	-> 18
/*     */     //   #149	-> 28
/*     */     //   #150	-> 33
/*     */     //   #151	-> 44
/*     */     //   #152	-> 46
/*     */     //   #154	-> 48
/*     */     //   #155	-> 59
/*     */     //   #156	-> 64
/*     */     //   #162	-> 74
/*     */     //   #163	-> 85
/*     */     //   #164	-> 90
/*     */     //   #167	-> 100
/*     */     //   #162	-> 138
/*     */     //   #167	-> 147
/*     */     //   #169	-> 187
/*     */     //   #171	-> 201
/*     */     //   #172	-> 220
/*     */     //   #173	-> 224
/*     */     //   #174	-> 228
/*     */     //   #169	-> 266
/*     */     //   #174	-> 275
/*     */     //   #175	-> 315
/*     */     //   #154	-> 353
/*     */     //   #175	-> 362
/*     */     //   #177	-> 402
/*     */     //   #175	-> 405
/*     */     //   #176	-> 407
/*     */     //   #179	-> 438
/*     */     //   #180	-> 446
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   48	402	405	java/io/IOException
/*     */     //   59	315	353	java/lang/Throwable
/*     */     //   59	315	362	finally
/*     */     //   85	100	138	java/lang/Throwable
/*     */     //   85	100	147	finally
/*     */     //   110	115	118	java/lang/Throwable
/*     */     //   138	149	147	finally
/*     */     //   159	164	167	java/lang/Throwable
/*     */     //   201	228	266	java/lang/Throwable
/*     */     //   201	228	275	finally
/*     */     //   238	243	246	java/lang/Throwable
/*     */     //   266	277	275	finally
/*     */     //   287	292	295	java/lang/Throwable
/*     */     //   325	330	333	java/lang/Throwable
/*     */     //   353	364	362	finally
/*     */     //   374	379	382	java/lang/Throwable
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
/*     */   public long length() throws SerialException {
/* 192 */     isValid();
/* 193 */     return this.len;
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
/*     */   public Reader getCharacterStream() throws SerialException {
/* 208 */     isValid();
/* 209 */     return new CharArrayReader(this.buf);
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
/*     */   public InputStream getAsciiStream() throws SerialException, SQLException {
/* 230 */     isValid();
/* 231 */     if (this.clob != null) {
/* 232 */       return this.clob.getAsciiStream();
/*     */     }
/* 234 */     throw new SerialException("Unsupported operation. SerialClob cannot return a the CLOB value as an ascii stream, unless instantiated with a fully implemented Clob object.");
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
/*     */   public String getSubString(long paramLong, int paramInt) throws SerialException {
/* 267 */     isValid();
/* 268 */     if (paramLong < 1L || paramLong > length()) {
/* 269 */       throw new SerialException("Invalid position in SerialClob object set");
/*     */     }
/*     */     
/* 272 */     if (paramLong - 1L + paramInt > length()) {
/* 273 */       throw new SerialException("Invalid position and substring length");
/*     */     }
/*     */     
/*     */     try {
/* 277 */       return new String(this.buf, (int)paramLong - 1, paramInt);
/*     */     }
/* 279 */     catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
/* 280 */       throw new SerialException("StringIndexOutOfBoundsException: " + stringIndexOutOfBoundsException
/* 281 */           .getMessage());
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
/*     */   public long position(String paramString, long paramLong) throws SerialException, SQLException {
/* 310 */     isValid();
/* 311 */     if (paramLong < 1L || paramLong > this.len) {
/* 312 */       return -1L;
/*     */     }
/*     */     
/* 315 */     char[] arrayOfChar = paramString.toCharArray();
/*     */     
/* 317 */     int i = (int)paramLong - 1;
/* 318 */     byte b = 0;
/* 319 */     long l = arrayOfChar.length;
/*     */     
/* 321 */     while (i < this.len) {
/* 322 */       if (arrayOfChar[b] == this.buf[i]) {
/* 323 */         if ((b + 1) == l) {
/* 324 */           return (i + 1) - l - 1L;
/*     */         }
/* 326 */         b++; i++; continue;
/*     */       } 
/* 328 */       if (arrayOfChar[b] != this.buf[i]) {
/* 329 */         i++;
/*     */       }
/*     */     } 
/* 332 */     return -1L;
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
/*     */   public long position(Clob paramClob, long paramLong) throws SerialException, SQLException {
/* 356 */     isValid();
/* 357 */     return position(paramClob.getSubString(1L, (int)paramClob.length()), paramLong);
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
/*     */   public int setString(long paramLong, String paramString) throws SerialException {
/* 380 */     return setString(paramLong, paramString, 0, paramString.length());
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
/*     */   public int setString(long paramLong, String paramString, int paramInt1, int paramInt2) throws SerialException {
/* 407 */     isValid();
/* 408 */     String str = paramString.substring(paramInt1);
/* 409 */     char[] arrayOfChar = str.toCharArray();
/*     */     
/* 411 */     if (paramInt1 < 0 || paramInt1 > paramString.length()) {
/* 412 */       throw new SerialException("Invalid offset in byte array set");
/*     */     }
/*     */     
/* 415 */     if (paramLong < 1L || paramLong > length()) {
/* 416 */       throw new SerialException("Invalid position in Clob object set");
/*     */     }
/*     */     
/* 419 */     if (paramInt2 > this.origLen) {
/* 420 */       throw new SerialException("Buffer is not sufficient to hold the value");
/*     */     }
/*     */     
/* 423 */     if (paramInt2 + paramInt1 > paramString.length())
/*     */     {
/* 425 */       throw new SerialException("Invalid OffSet. Cannot have combined offset  and length that is greater that the Blob buffer");
/*     */     }
/*     */ 
/*     */     
/* 429 */     byte b = 0;
/* 430 */     paramLong--;
/* 431 */     while (b < paramInt2 || paramInt1 + b + 1 < paramString.length() - paramInt1) {
/* 432 */       this.buf[(int)paramLong + b] = arrayOfChar[paramInt1 + b];
/* 433 */       b++;
/*     */     } 
/* 435 */     return b;
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
/*     */   public OutputStream setAsciiStream(long paramLong) throws SerialException, SQLException {
/* 459 */     isValid();
/* 460 */     if (this.clob != null) {
/* 461 */       return this.clob.setAsciiStream(paramLong);
/*     */     }
/* 463 */     throw new SerialException("Unsupported operation. SerialClob cannot return a writable ascii stream\n unless instantiated with a Clob object that has a setAsciiStream() implementation");
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
/*     */   public Writer setCharacterStream(long paramLong) throws SerialException, SQLException {
/* 491 */     isValid();
/* 492 */     if (this.clob != null) {
/* 493 */       return this.clob.setCharacterStream(paramLong);
/*     */     }
/* 495 */     throw new SerialException("Unsupported operation. SerialClob cannot return a writable character stream\n unless instantiated with a Clob object that has a setCharacterStream implementation");
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
/*     */   public void truncate(long paramLong) throws SerialException {
/* 516 */     isValid();
/* 517 */     if (paramLong > this.len) {
/* 518 */       throw new SerialException("Length more than what can be truncated");
/*     */     }
/*     */     
/* 521 */     this.len = paramLong;
/*     */ 
/*     */     
/* 524 */     if (this.len == 0L) {
/* 525 */       this.buf = new char[0];
/*     */     } else {
/* 527 */       this.buf = getSubString(1L, (int)this.len).toCharArray();
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
/*     */   public Reader getCharacterStream(long paramLong1, long paramLong2) throws SQLException {
/* 551 */     isValid();
/* 552 */     if (paramLong1 < 1L || paramLong1 > this.len) {
/* 553 */       throw new SerialException("Invalid position in Clob object set");
/*     */     }
/*     */     
/* 556 */     if (paramLong1 - 1L + paramLong2 > this.len) {
/* 557 */       throw new SerialException("Invalid position and substring length");
/*     */     }
/* 559 */     if (paramLong2 <= 0L) {
/* 560 */       throw new SerialException("Invalid length specified");
/*     */     }
/* 562 */     return new CharArrayReader(this.buf, (int)paramLong1, (int)paramLong2);
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
/*     */   public void free() throws SQLException {
/* 578 */     if (this.buf != null) {
/* 579 */       this.buf = null;
/* 580 */       if (this.clob != null) {
/* 581 */         this.clob.free();
/*     */       }
/* 583 */       this.clob = null;
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
/*     */   public boolean equals(Object paramObject) {
/* 600 */     if (this == paramObject) {
/* 601 */       return true;
/*     */     }
/* 603 */     if (paramObject instanceof SerialClob) {
/* 604 */       SerialClob serialClob = (SerialClob)paramObject;
/* 605 */       if (this.len == serialClob.len) {
/* 606 */         return Arrays.equals(this.buf, serialClob.buf);
/*     */       }
/*     */     } 
/* 609 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 617 */     return ((31 + Arrays.hashCode(this.buf)) * 31 + (int)this.len) * 31 + (int)this.origLen;
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
/*     */   public Object clone() {
/*     */     try {
/* 630 */       SerialClob serialClob = (SerialClob)super.clone();
/* 631 */       serialClob.buf = (this.buf != null) ? Arrays.copyOf(this.buf, (int)this.len) : null;
/* 632 */       serialClob.clob = null;
/* 633 */       return serialClob;
/* 634 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 636 */       throw new InternalError();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 647 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 648 */     char[] arrayOfChar = (char[])getField.get("buf", (Object)null);
/* 649 */     if (arrayOfChar == null)
/* 650 */       throw new InvalidObjectException("buf is null and should not be!"); 
/* 651 */     this.buf = (char[])arrayOfChar.clone();
/* 652 */     this.len = getField.get("len", 0L);
/* 653 */     if (this.buf.length != this.len)
/* 654 */       throw new InvalidObjectException("buf is not the expected size"); 
/* 655 */     this.origLen = getField.get("origLen", 0L);
/* 656 */     this.clob = (Clob)getField.get("clob", (Object)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException, ClassNotFoundException {
/* 666 */     ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 667 */     putField.put("buf", this.buf);
/* 668 */     putField.put("len", this.len);
/* 669 */     putField.put("origLen", this.origLen);
/*     */ 
/*     */     
/* 672 */     putField.put("clob", (this.clob instanceof Serializable) ? this.clob : null);
/* 673 */     paramObjectOutputStream.writeFields();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void isValid() throws SerialException {
/* 683 */     if (this.buf == null)
/* 684 */       throw new SerialException("Error: You cannot call a method on a SerialClob instance once free() has been called."); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sql/rowset/serial/SerialClob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */