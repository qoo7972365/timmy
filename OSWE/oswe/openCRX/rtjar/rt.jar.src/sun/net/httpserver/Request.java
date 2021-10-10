/*     */ package sun.net.httpserver;
/*     */ 
/*     */ import com.sun.net.httpserver.Headers;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.SocketChannel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Request
/*     */ {
/*     */   static final int BUF_LEN = 2048;
/*     */   static final byte CR = 13;
/*     */   static final byte LF = 10;
/*     */   private String startLine;
/*     */   private SocketChannel chan;
/*     */   private InputStream is;
/*     */   private OutputStream os;
/*     */   char[] buf;
/*     */   int pos;
/*     */   StringBuffer lineBuf;
/*     */   Headers hdrs;
/*     */   
/*     */   Request(InputStream paramInputStream, OutputStream paramOutputStream) throws IOException {
/*  59 */     this.buf = new char[2048];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     this.hdrs = null;
/*     */     this.is = paramInputStream;
/*     */     this.os = paramOutputStream;
/*     */     do {
/*     */       this.startLine = readLine();
/*     */       if (this.startLine == null) {
/*     */         return;
/*     */       }
/*     */     } while (this.startLine != null && this.startLine.equals(""));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream inputStream() {
/*     */     return this.is;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public OutputStream outputStream() {
/*     */     return this.os;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String readLine() throws IOException {
/*     */     boolean bool1 = false, bool2 = false;
/*     */     this.pos = 0;
/*     */     this.lineBuf = new StringBuffer();
/*     */     while (!bool2) {
/*     */       int i = this.is.read();
/*     */       if (i == -1) {
/*     */         return null;
/*     */       }
/*     */       if (bool1) {
/*     */         if (i == 10) {
/*     */           bool2 = true;
/*     */           continue;
/*     */         } 
/*     */         bool1 = false;
/*     */         consume(13);
/*     */         consume(i);
/*     */         continue;
/*     */       } 
/*     */       if (i == 13) {
/*     */         bool1 = true;
/*     */         continue;
/*     */       } 
/*     */       consume(i);
/*     */     } 
/*     */     this.lineBuf.append(this.buf, 0, this.pos);
/*     */     return new String(this.lineBuf);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void consume(int paramInt) {
/*     */     if (this.pos == 2048) {
/*     */       this.lineBuf.append(this.buf);
/*     */       this.pos = 0;
/*     */     } 
/*     */     this.buf[this.pos++] = (char)paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String requestLine() {
/*     */     return this.startLine;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Headers headers() throws IOException {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield hdrs : Lcom/sun/net/httpserver/Headers;
/*     */     //   4: ifnull -> 12
/*     */     //   7: aload_0
/*     */     //   8: getfield hdrs : Lcom/sun/net/httpserver/Headers;
/*     */     //   11: areturn
/*     */     //   12: aload_0
/*     */     //   13: new com/sun/net/httpserver/Headers
/*     */     //   16: dup
/*     */     //   17: invokespecial <init> : ()V
/*     */     //   20: putfield hdrs : Lcom/sun/net/httpserver/Headers;
/*     */     //   23: bipush #10
/*     */     //   25: newarray char
/*     */     //   27: astore_1
/*     */     //   28: iconst_0
/*     */     //   29: istore_2
/*     */     //   30: aload_0
/*     */     //   31: getfield is : Ljava/io/InputStream;
/*     */     //   34: invokevirtual read : ()I
/*     */     //   37: istore_3
/*     */     //   38: iload_3
/*     */     //   39: bipush #13
/*     */     //   41: if_icmpeq -> 50
/*     */     //   44: iload_3
/*     */     //   45: bipush #10
/*     */     //   47: if_icmpne -> 88
/*     */     //   50: aload_0
/*     */     //   51: getfield is : Ljava/io/InputStream;
/*     */     //   54: invokevirtual read : ()I
/*     */     //   57: istore #4
/*     */     //   59: iload #4
/*     */     //   61: bipush #13
/*     */     //   63: if_icmpeq -> 73
/*     */     //   66: iload #4
/*     */     //   68: bipush #10
/*     */     //   70: if_icmpne -> 78
/*     */     //   73: aload_0
/*     */     //   74: getfield hdrs : Lcom/sun/net/httpserver/Headers;
/*     */     //   77: areturn
/*     */     //   78: aload_1
/*     */     //   79: iconst_0
/*     */     //   80: iload_3
/*     */     //   81: i2c
/*     */     //   82: castore
/*     */     //   83: iconst_1
/*     */     //   84: istore_2
/*     */     //   85: iload #4
/*     */     //   87: istore_3
/*     */     //   88: iload_3
/*     */     //   89: bipush #10
/*     */     //   91: if_icmpeq -> 503
/*     */     //   94: iload_3
/*     */     //   95: bipush #13
/*     */     //   97: if_icmpeq -> 503
/*     */     //   100: iload_3
/*     */     //   101: iflt -> 503
/*     */     //   104: iconst_m1
/*     */     //   105: istore #4
/*     */     //   107: iload_3
/*     */     //   108: bipush #32
/*     */     //   110: if_icmple -> 117
/*     */     //   113: iconst_1
/*     */     //   114: goto -> 118
/*     */     //   117: iconst_0
/*     */     //   118: istore #6
/*     */     //   120: aload_1
/*     */     //   121: iload_2
/*     */     //   122: iinc #2, 1
/*     */     //   125: iload_3
/*     */     //   126: i2c
/*     */     //   127: castore
/*     */     //   128: aload_0
/*     */     //   129: getfield is : Ljava/io/InputStream;
/*     */     //   132: invokevirtual read : ()I
/*     */     //   135: dup
/*     */     //   136: istore #5
/*     */     //   138: iflt -> 326
/*     */     //   141: iload #5
/*     */     //   143: lookupswitch default -> 288, 9 -> 210, 10 -> 220, 13 -> 220, 32 -> 214, 58 -> 192
/*     */     //   192: iload #6
/*     */     //   194: ifeq -> 204
/*     */     //   197: iload_2
/*     */     //   198: ifle -> 204
/*     */     //   201: iload_2
/*     */     //   202: istore #4
/*     */     //   204: iconst_0
/*     */     //   205: istore #6
/*     */     //   207: goto -> 288
/*     */     //   210: bipush #32
/*     */     //   212: istore #5
/*     */     //   214: iconst_0
/*     */     //   215: istore #6
/*     */     //   217: goto -> 288
/*     */     //   220: aload_0
/*     */     //   221: getfield is : Ljava/io/InputStream;
/*     */     //   224: invokevirtual read : ()I
/*     */     //   227: istore_3
/*     */     //   228: iload #5
/*     */     //   230: bipush #13
/*     */     //   232: if_icmpne -> 263
/*     */     //   235: iload_3
/*     */     //   236: bipush #10
/*     */     //   238: if_icmpne -> 263
/*     */     //   241: aload_0
/*     */     //   242: getfield is : Ljava/io/InputStream;
/*     */     //   245: invokevirtual read : ()I
/*     */     //   248: istore_3
/*     */     //   249: iload_3
/*     */     //   250: bipush #13
/*     */     //   252: if_icmpne -> 263
/*     */     //   255: aload_0
/*     */     //   256: getfield is : Ljava/io/InputStream;
/*     */     //   259: invokevirtual read : ()I
/*     */     //   262: istore_3
/*     */     //   263: iload_3
/*     */     //   264: bipush #10
/*     */     //   266: if_icmpeq -> 328
/*     */     //   269: iload_3
/*     */     //   270: bipush #13
/*     */     //   272: if_icmpeq -> 328
/*     */     //   275: iload_3
/*     */     //   276: bipush #32
/*     */     //   278: if_icmple -> 284
/*     */     //   281: goto -> 328
/*     */     //   284: bipush #32
/*     */     //   286: istore #5
/*     */     //   288: iload_2
/*     */     //   289: aload_1
/*     */     //   290: arraylength
/*     */     //   291: if_icmplt -> 314
/*     */     //   294: aload_1
/*     */     //   295: arraylength
/*     */     //   296: iconst_2
/*     */     //   297: imul
/*     */     //   298: newarray char
/*     */     //   300: astore #7
/*     */     //   302: aload_1
/*     */     //   303: iconst_0
/*     */     //   304: aload #7
/*     */     //   306: iconst_0
/*     */     //   307: iload_2
/*     */     //   308: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
/*     */     //   311: aload #7
/*     */     //   313: astore_1
/*     */     //   314: aload_1
/*     */     //   315: iload_2
/*     */     //   316: iinc #2, 1
/*     */     //   319: iload #5
/*     */     //   321: i2c
/*     */     //   322: castore
/*     */     //   323: goto -> 128
/*     */     //   326: iconst_m1
/*     */     //   327: istore_3
/*     */     //   328: iload_2
/*     */     //   329: ifle -> 348
/*     */     //   332: aload_1
/*     */     //   333: iload_2
/*     */     //   334: iconst_1
/*     */     //   335: isub
/*     */     //   336: caload
/*     */     //   337: bipush #32
/*     */     //   339: if_icmpgt -> 348
/*     */     //   342: iinc #2, -1
/*     */     //   345: goto -> 328
/*     */     //   348: iload #4
/*     */     //   350: ifgt -> 362
/*     */     //   353: aconst_null
/*     */     //   354: astore #7
/*     */     //   356: iconst_0
/*     */     //   357: istore #4
/*     */     //   359: goto -> 410
/*     */     //   362: aload_1
/*     */     //   363: iconst_0
/*     */     //   364: iload #4
/*     */     //   366: invokestatic copyValueOf : ([CII)Ljava/lang/String;
/*     */     //   369: astore #7
/*     */     //   371: iload #4
/*     */     //   373: iload_2
/*     */     //   374: if_icmpge -> 389
/*     */     //   377: aload_1
/*     */     //   378: iload #4
/*     */     //   380: caload
/*     */     //   381: bipush #58
/*     */     //   383: if_icmpne -> 389
/*     */     //   386: iinc #4, 1
/*     */     //   389: iload #4
/*     */     //   391: iload_2
/*     */     //   392: if_icmpge -> 410
/*     */     //   395: aload_1
/*     */     //   396: iload #4
/*     */     //   398: caload
/*     */     //   399: bipush #32
/*     */     //   401: if_icmpgt -> 410
/*     */     //   404: iinc #4, 1
/*     */     //   407: goto -> 389
/*     */     //   410: iload #4
/*     */     //   412: iload_2
/*     */     //   413: if_icmplt -> 428
/*     */     //   416: new java/lang/String
/*     */     //   419: dup
/*     */     //   420: invokespecial <init> : ()V
/*     */     //   423: astore #8
/*     */     //   425: goto -> 440
/*     */     //   428: aload_1
/*     */     //   429: iload #4
/*     */     //   431: iload_2
/*     */     //   432: iload #4
/*     */     //   434: isub
/*     */     //   435: invokestatic copyValueOf : ([CII)Ljava/lang/String;
/*     */     //   438: astore #8
/*     */     //   440: aload_0
/*     */     //   441: getfield hdrs : Lcom/sun/net/httpserver/Headers;
/*     */     //   444: invokevirtual size : ()I
/*     */     //   447: invokestatic getMaxReqHeaders : ()I
/*     */     //   450: if_icmplt -> 487
/*     */     //   453: new java/io/IOException
/*     */     //   456: dup
/*     */     //   457: new java/lang/StringBuilder
/*     */     //   460: dup
/*     */     //   461: invokespecial <init> : ()V
/*     */     //   464: ldc 'Maximum number of request headers (sun.net.httpserver.maxReqHeaders) exceeded, '
/*     */     //   466: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   469: invokestatic getMaxReqHeaders : ()I
/*     */     //   472: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*     */     //   475: ldc '.'
/*     */     //   477: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   480: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   483: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   486: athrow
/*     */     //   487: aload_0
/*     */     //   488: getfield hdrs : Lcom/sun/net/httpserver/Headers;
/*     */     //   491: aload #7
/*     */     //   493: aload #8
/*     */     //   495: invokevirtual add : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   498: iconst_0
/*     */     //   499: istore_2
/*     */     //   500: goto -> 88
/*     */     //   503: aload_0
/*     */     //   504: getfield hdrs : Lcom/sun/net/httpserver/Headers;
/*     */     //   507: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #122	-> 0
/*     */     //   #123	-> 7
/*     */     //   #125	-> 12
/*     */     //   #127	-> 23
/*     */     //   #128	-> 28
/*     */     //   #130	-> 30
/*     */     //   #133	-> 38
/*     */     //   #134	-> 50
/*     */     //   #135	-> 59
/*     */     //   #136	-> 73
/*     */     //   #138	-> 78
/*     */     //   #139	-> 83
/*     */     //   #140	-> 85
/*     */     //   #143	-> 88
/*     */     //   #144	-> 104
/*     */     //   #146	-> 107
/*     */     //   #147	-> 120
/*     */     //   #149	-> 128
/*     */     //   #150	-> 141
/*     */     //   #153	-> 192
/*     */     //   #154	-> 201
/*     */     //   #155	-> 204
/*     */     //   #156	-> 207
/*     */     //   #158	-> 210
/*     */     //   #160	-> 214
/*     */     //   #161	-> 217
/*     */     //   #164	-> 220
/*     */     //   #165	-> 228
/*     */     //   #166	-> 241
/*     */     //   #167	-> 249
/*     */     //   #168	-> 255
/*     */     //   #170	-> 263
/*     */     //   #171	-> 281
/*     */     //   #173	-> 284
/*     */     //   #176	-> 288
/*     */     //   #177	-> 294
/*     */     //   #178	-> 302
/*     */     //   #179	-> 311
/*     */     //   #181	-> 314
/*     */     //   #183	-> 326
/*     */     //   #185	-> 328
/*     */     //   #186	-> 342
/*     */     //   #188	-> 348
/*     */     //   #189	-> 353
/*     */     //   #190	-> 356
/*     */     //   #192	-> 362
/*     */     //   #193	-> 371
/*     */     //   #194	-> 386
/*     */     //   #195	-> 389
/*     */     //   #196	-> 404
/*     */     //   #199	-> 410
/*     */     //   #200	-> 416
/*     */     //   #202	-> 428
/*     */     //   #204	-> 440
/*     */     //   #205	-> 453
/*     */     //   #207	-> 469
/*     */     //   #210	-> 487
/*     */     //   #211	-> 498
/*     */     //   #212	-> 500
/*     */     //   #213	-> 503
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class ReadStream
/*     */     extends InputStream
/*     */   {
/*     */     SocketChannel channel;
/*     */ 
/*     */     
/*     */     ByteBuffer chanbuf;
/*     */ 
/*     */     
/*     */     byte[] one;
/*     */ 
/*     */     
/*     */     private boolean closed = false;
/*     */ 
/*     */     
/*     */     private boolean eof = false;
/*     */ 
/*     */     
/*     */     ByteBuffer markBuf;
/*     */ 
/*     */     
/*     */     boolean marked;
/*     */ 
/*     */     
/*     */     boolean reset;
/*     */ 
/*     */     
/*     */     int readlimit;
/*     */     
/*     */     static long readTimeout;
/*     */     
/*     */     ServerImpl server;
/*     */     
/*     */     static final int BUFSIZE = 8192;
/*     */ 
/*     */     
/*     */     public ReadStream(ServerImpl param1ServerImpl, SocketChannel param1SocketChannel) throws IOException {
/* 234 */       this.channel = param1SocketChannel;
/* 235 */       this.server = param1ServerImpl;
/* 236 */       this.chanbuf = ByteBuffer.allocate(8192);
/* 237 */       this.chanbuf.clear();
/* 238 */       this.one = new byte[1];
/* 239 */       this.closed = this.marked = this.reset = false;
/*     */     }
/*     */     
/*     */     public synchronized int read(byte[] param1ArrayOfbyte) throws IOException {
/* 243 */       return read(param1ArrayOfbyte, 0, param1ArrayOfbyte.length);
/*     */     }
/*     */     
/*     */     public synchronized int read() throws IOException {
/* 247 */       int i = read(this.one, 0, 1);
/* 248 */       if (i == 1) {
/* 249 */         return this.one[0] & 0xFF;
/*     */       }
/* 251 */       return -1;
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
/*     */     public synchronized int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: getfield closed : Z
/*     */       //   4: ifeq -> 17
/*     */       //   7: new java/io/IOException
/*     */       //   10: dup
/*     */       //   11: ldc 'Stream closed'
/*     */       //   13: invokespecial <init> : (Ljava/lang/String;)V
/*     */       //   16: athrow
/*     */       //   17: aload_0
/*     */       //   18: getfield eof : Z
/*     */       //   21: ifeq -> 26
/*     */       //   24: iconst_m1
/*     */       //   25: ireturn
/*     */       //   26: getstatic sun/net/httpserver/Request$ReadStream.$assertionsDisabled : Z
/*     */       //   29: ifne -> 50
/*     */       //   32: aload_0
/*     */       //   33: getfield channel : Ljava/nio/channels/SocketChannel;
/*     */       //   36: invokevirtual isBlocking : ()Z
/*     */       //   39: ifne -> 50
/*     */       //   42: new java/lang/AssertionError
/*     */       //   45: dup
/*     */       //   46: invokespecial <init> : ()V
/*     */       //   49: athrow
/*     */       //   50: iload_2
/*     */       //   51: iflt -> 66
/*     */       //   54: iload_3
/*     */       //   55: iflt -> 66
/*     */       //   58: iload_3
/*     */       //   59: aload_1
/*     */       //   60: arraylength
/*     */       //   61: iload_2
/*     */       //   62: isub
/*     */       //   63: if_icmple -> 74
/*     */       //   66: new java/lang/IndexOutOfBoundsException
/*     */       //   69: dup
/*     */       //   70: invokespecial <init> : ()V
/*     */       //   73: athrow
/*     */       //   74: aload_0
/*     */       //   75: getfield reset : Z
/*     */       //   78: ifeq -> 131
/*     */       //   81: aload_0
/*     */       //   82: getfield markBuf : Ljava/nio/ByteBuffer;
/*     */       //   85: invokevirtual remaining : ()I
/*     */       //   88: istore #4
/*     */       //   90: iload #4
/*     */       //   92: iload_3
/*     */       //   93: if_icmple -> 100
/*     */       //   96: iload_3
/*     */       //   97: goto -> 102
/*     */       //   100: iload #4
/*     */       //   102: istore #5
/*     */       //   104: aload_0
/*     */       //   105: getfield markBuf : Ljava/nio/ByteBuffer;
/*     */       //   108: aload_1
/*     */       //   109: iload_2
/*     */       //   110: iload #5
/*     */       //   112: invokevirtual get : ([BII)Ljava/nio/ByteBuffer;
/*     */       //   115: pop
/*     */       //   116: iload #4
/*     */       //   118: iload #5
/*     */       //   120: if_icmpne -> 235
/*     */       //   123: aload_0
/*     */       //   124: iconst_0
/*     */       //   125: putfield reset : Z
/*     */       //   128: goto -> 235
/*     */       //   131: aload_0
/*     */       //   132: getfield chanbuf : Ljava/nio/ByteBuffer;
/*     */       //   135: invokevirtual clear : ()Ljava/nio/Buffer;
/*     */       //   138: pop
/*     */       //   139: iload_3
/*     */       //   140: sipush #8192
/*     */       //   143: if_icmpge -> 155
/*     */       //   146: aload_0
/*     */       //   147: getfield chanbuf : Ljava/nio/ByteBuffer;
/*     */       //   150: iload_3
/*     */       //   151: invokevirtual limit : (I)Ljava/nio/Buffer;
/*     */       //   154: pop
/*     */       //   155: aload_0
/*     */       //   156: getfield channel : Ljava/nio/channels/SocketChannel;
/*     */       //   159: aload_0
/*     */       //   160: getfield chanbuf : Ljava/nio/ByteBuffer;
/*     */       //   163: invokevirtual read : (Ljava/nio/ByteBuffer;)I
/*     */       //   166: istore #5
/*     */       //   168: iload #5
/*     */       //   170: ifeq -> 155
/*     */       //   173: iload #5
/*     */       //   175: iconst_m1
/*     */       //   176: if_icmpne -> 186
/*     */       //   179: aload_0
/*     */       //   180: iconst_1
/*     */       //   181: putfield eof : Z
/*     */       //   184: iconst_m1
/*     */       //   185: ireturn
/*     */       //   186: aload_0
/*     */       //   187: getfield chanbuf : Ljava/nio/ByteBuffer;
/*     */       //   190: invokevirtual flip : ()Ljava/nio/Buffer;
/*     */       //   193: pop
/*     */       //   194: aload_0
/*     */       //   195: getfield chanbuf : Ljava/nio/ByteBuffer;
/*     */       //   198: aload_1
/*     */       //   199: iload_2
/*     */       //   200: iload #5
/*     */       //   202: invokevirtual get : ([BII)Ljava/nio/ByteBuffer;
/*     */       //   205: pop
/*     */       //   206: aload_0
/*     */       //   207: getfield marked : Z
/*     */       //   210: ifeq -> 235
/*     */       //   213: aload_0
/*     */       //   214: getfield markBuf : Ljava/nio/ByteBuffer;
/*     */       //   217: aload_1
/*     */       //   218: iload_2
/*     */       //   219: iload #5
/*     */       //   221: invokevirtual put : ([BII)Ljava/nio/ByteBuffer;
/*     */       //   224: pop
/*     */       //   225: goto -> 235
/*     */       //   228: astore #6
/*     */       //   230: aload_0
/*     */       //   231: iconst_0
/*     */       //   232: putfield marked : Z
/*     */       //   235: iload #5
/*     */       //   237: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #259	-> 0
/*     */       //   #260	-> 7
/*     */       //   #262	-> 17
/*     */       //   #263	-> 24
/*     */       //   #266	-> 26
/*     */       //   #268	-> 50
/*     */       //   #269	-> 66
/*     */       //   #272	-> 74
/*     */       //   #273	-> 81
/*     */       //   #274	-> 90
/*     */       //   #275	-> 104
/*     */       //   #276	-> 116
/*     */       //   #277	-> 123
/*     */       //   #280	-> 131
/*     */       //   #281	-> 139
/*     */       //   #282	-> 146
/*     */       //   #285	-> 155
/*     */       //   #286	-> 168
/*     */       //   #287	-> 173
/*     */       //   #288	-> 179
/*     */       //   #289	-> 184
/*     */       //   #291	-> 186
/*     */       //   #292	-> 194
/*     */       //   #294	-> 206
/*     */       //   #296	-> 213
/*     */       //   #299	-> 225
/*     */       //   #297	-> 228
/*     */       //   #298	-> 230
/*     */       //   #302	-> 235
/*     */       // Exception table:
/*     */       //   from	to	target	type
/*     */       //   213	225	228	java/nio/BufferOverflowException
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
/*     */     public boolean markSupported() {
/* 306 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized int available() throws IOException {
/* 311 */       if (this.closed) {
/* 312 */         throw new IOException("Stream is closed");
/*     */       }
/* 314 */       if (this.eof) {
/* 315 */         return -1;
/*     */       }
/* 317 */       if (this.reset) {
/* 318 */         return this.markBuf.remaining();
/*     */       }
/* 320 */       return this.chanbuf.remaining();
/*     */     }
/*     */     
/*     */     public void close() throws IOException {
/* 324 */       if (this.closed) {
/*     */         return;
/*     */       }
/* 327 */       this.channel.close();
/* 328 */       this.closed = true;
/*     */     }
/*     */     
/*     */     public synchronized void mark(int param1Int) {
/* 332 */       if (this.closed)
/*     */         return; 
/* 334 */       this.readlimit = param1Int;
/* 335 */       this.markBuf = ByteBuffer.allocate(param1Int);
/* 336 */       this.marked = true;
/* 337 */       this.reset = false;
/*     */     }
/*     */     
/*     */     public synchronized void reset() throws IOException {
/* 341 */       if (this.closed)
/*     */         return; 
/* 343 */       if (!this.marked)
/* 344 */         throw new IOException("Stream not marked"); 
/* 345 */       this.marked = false;
/* 346 */       this.reset = true;
/* 347 */       this.markBuf.flip();
/*     */     }
/*     */   }
/*     */   
/*     */   static class WriteStream extends OutputStream {
/*     */     SocketChannel channel;
/*     */     ByteBuffer buf;
/*     */     SelectionKey key;
/*     */     boolean closed;
/*     */     byte[] one;
/*     */     ServerImpl server;
/*     */     
/*     */     public WriteStream(ServerImpl param1ServerImpl, SocketChannel param1SocketChannel) throws IOException {
/* 360 */       this.channel = param1SocketChannel;
/* 361 */       this.server = param1ServerImpl;
/* 362 */       assert param1SocketChannel.isBlocking();
/* 363 */       this.closed = false;
/* 364 */       this.one = new byte[1];
/* 365 */       this.buf = ByteBuffer.allocate(4096);
/*     */     }
/*     */     
/*     */     public synchronized void write(int param1Int) throws IOException {
/* 369 */       this.one[0] = (byte)param1Int;
/* 370 */       write(this.one, 0, 1);
/*     */     }
/*     */     
/*     */     public synchronized void write(byte[] param1ArrayOfbyte) throws IOException {
/* 374 */       write(param1ArrayOfbyte, 0, param1ArrayOfbyte.length);
/*     */     }
/*     */     
/*     */     public synchronized void write(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/* 378 */       int i = param1Int2;
/* 379 */       if (this.closed) {
/* 380 */         throw new IOException("stream is closed");
/*     */       }
/* 382 */       int j = this.buf.capacity();
/* 383 */       if (j < param1Int2) {
/* 384 */         int m = param1Int2 - j;
/* 385 */         this.buf = ByteBuffer.allocate(2 * (j + m));
/*     */       } 
/* 387 */       this.buf.clear();
/* 388 */       this.buf.put(param1ArrayOfbyte, param1Int1, param1Int2);
/* 389 */       this.buf.flip();
/*     */       int k;
/* 391 */       while ((k = this.channel.write(this.buf)) < i) {
/* 392 */         i -= k;
/* 393 */         if (i == 0)
/*     */           return; 
/*     */       } 
/*     */     }
/*     */     
/*     */     public void close() throws IOException {
/* 399 */       if (this.closed) {
/*     */         return;
/*     */       }
/* 402 */       this.channel.close();
/* 403 */       this.closed = true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/httpserver/Request.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */