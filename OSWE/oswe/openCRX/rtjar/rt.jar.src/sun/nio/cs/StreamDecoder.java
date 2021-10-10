/*     */ package sun.nio.cs;
/*     */ 
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CoderResult;
/*     */ import java.nio.charset.CodingErrorAction;
/*     */ import java.nio.charset.IllegalCharsetNameException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StreamDecoder
/*     */   extends Reader
/*     */ {
/*     */   private static final int MIN_BYTE_BUFFER_SIZE = 32;
/*     */   private static final int DEFAULT_BYTE_BUFFER_SIZE = 8192;
/*     */   private volatile boolean isOpen = true;
/*     */   
/*     */   private void ensureOpen() throws IOException {
/*  45 */     if (!this.isOpen) {
/*  46 */       throw new IOException("Stream closed");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean haveLeftoverChar = false;
/*     */ 
/*     */ 
/*     */   
/*     */   private char leftoverChar;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StreamDecoder forInputStreamReader(InputStream paramInputStream, Object paramObject, String paramString) throws UnsupportedEncodingException {
/*  64 */     String str = paramString;
/*  65 */     if (str == null)
/*  66 */       str = Charset.defaultCharset().name(); 
/*     */     try {
/*  68 */       if (Charset.isSupported(str))
/*  69 */         return new StreamDecoder(paramInputStream, paramObject, Charset.forName(str)); 
/*  70 */     } catch (IllegalCharsetNameException illegalCharsetNameException) {}
/*  71 */     throw new UnsupportedEncodingException(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StreamDecoder forInputStreamReader(InputStream paramInputStream, Object paramObject, Charset paramCharset) {
/*  78 */     return new StreamDecoder(paramInputStream, paramObject, paramCharset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StreamDecoder forInputStreamReader(InputStream paramInputStream, Object paramObject, CharsetDecoder paramCharsetDecoder) {
/*  85 */     return new StreamDecoder(paramInputStream, paramObject, paramCharsetDecoder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StreamDecoder forDecoder(ReadableByteChannel paramReadableByteChannel, CharsetDecoder paramCharsetDecoder, int paramInt) {
/*  95 */     return new StreamDecoder(paramReadableByteChannel, paramCharsetDecoder, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEncoding() {
/* 106 */     if (isOpen())
/* 107 */       return encodingName(); 
/* 108 */     return null;
/*     */   }
/*     */   
/*     */   public int read() throws IOException {
/* 112 */     return read0();
/*     */   }
/*     */ 
/*     */   
/*     */   private int read0() throws IOException {
/* 117 */     synchronized (this.lock) {
/*     */ 
/*     */       
/* 120 */       if (this.haveLeftoverChar) {
/* 121 */         this.haveLeftoverChar = false;
/* 122 */         return this.leftoverChar;
/*     */       } 
/*     */ 
/*     */       
/* 126 */       char[] arrayOfChar = new char[2];
/* 127 */       int i = read(arrayOfChar, 0, 2);
/* 128 */       switch (i) {
/*     */         case -1:
/* 130 */           return -1;
/*     */         case 2:
/* 132 */           this.leftoverChar = arrayOfChar[1];
/* 133 */           this.haveLeftoverChar = true;
/*     */         
/*     */         case 1:
/* 136 */           return arrayOfChar[0];
/*     */       } 
/* 138 */       assert false : i;
/* 139 */       return -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int read(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException {
/* 145 */     int i = paramInt1;
/* 146 */     int j = paramInt2;
/* 147 */     synchronized (this.lock) {
/* 148 */       ensureOpen();
/* 149 */       if (i < 0 || i > paramArrayOfchar.length || j < 0 || i + j > paramArrayOfchar.length || i + j < 0)
/*     */       {
/* 151 */         throw new IndexOutOfBoundsException();
/*     */       }
/* 153 */       if (j == 0) {
/* 154 */         return 0;
/*     */       }
/* 156 */       byte b = 0;
/*     */       
/* 158 */       if (this.haveLeftoverChar) {
/*     */         
/* 160 */         paramArrayOfchar[i] = this.leftoverChar;
/* 161 */         i++; j--;
/* 162 */         this.haveLeftoverChar = false;
/* 163 */         b = 1;
/* 164 */         if (j == 0 || !implReady())
/*     */         {
/* 166 */           return b;
/*     */         }
/*     */       } 
/* 169 */       if (j == 1) {
/*     */         
/* 171 */         int k = read0();
/* 172 */         if (k == -1)
/* 173 */           return !b ? -1 : b; 
/* 174 */         paramArrayOfchar[i] = (char)k;
/* 175 */         return b + 1;
/*     */       } 
/*     */       
/* 178 */       return b + implRead(paramArrayOfchar, i, i + j);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean ready() throws IOException {
/* 183 */     synchronized (this.lock) {
/* 184 */       ensureOpen();
/* 185 */       return (this.haveLeftoverChar || implReady());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 190 */     synchronized (this.lock) {
/* 191 */       if (!this.isOpen)
/*     */         return; 
/* 193 */       implClose();
/* 194 */       this.isOpen = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isOpen() {
/* 199 */     return this.isOpen;
/*     */   }
/*     */ 
/*     */   
/*     */   private static volatile boolean channelsAvailable = true;
/*     */   
/*     */   private Charset cs;
/*     */   private CharsetDecoder decoder;
/*     */   private ByteBuffer bb;
/*     */   private InputStream in;
/*     */   private ReadableByteChannel ch;
/*     */   
/*     */   private static FileChannel getChannel(FileInputStream paramFileInputStream) {
/* 212 */     if (!channelsAvailable)
/* 213 */       return null; 
/*     */     try {
/* 215 */       return paramFileInputStream.getChannel();
/* 216 */     } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
/* 217 */       channelsAvailable = false;
/* 218 */       return null;
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
/*     */   StreamDecoder(InputStream paramInputStream, Object paramObject, Charset paramCharset) {
/* 231 */     this(paramInputStream, paramObject, paramCharset
/* 232 */         .newDecoder()
/* 233 */         .onMalformedInput(CodingErrorAction.REPLACE)
/* 234 */         .onUnmappableCharacter(CodingErrorAction.REPLACE));
/*     */   }
/*     */   
/*     */   StreamDecoder(InputStream paramInputStream, Object paramObject, CharsetDecoder paramCharsetDecoder) {
/* 238 */     super(paramObject);
/* 239 */     this.cs = paramCharsetDecoder.charset();
/* 240 */     this.decoder = paramCharsetDecoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 248 */     if (this.ch == null) {
/* 249 */       this.in = paramInputStream;
/* 250 */       this.ch = null;
/* 251 */       this.bb = ByteBuffer.allocate(8192);
/*     */     } 
/* 253 */     this.bb.flip();
/*     */   }
/*     */   
/*     */   StreamDecoder(ReadableByteChannel paramReadableByteChannel, CharsetDecoder paramCharsetDecoder, int paramInt) {
/* 257 */     this.in = null;
/* 258 */     this.ch = paramReadableByteChannel;
/* 259 */     this.decoder = paramCharsetDecoder;
/* 260 */     this.cs = paramCharsetDecoder.charset();
/* 261 */     this.bb = ByteBuffer.allocate((paramInt < 0) ? 8192 : ((paramInt < 32) ? 32 : paramInt));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 266 */     this.bb.flip();
/*     */   }
/*     */   
/*     */   private int readBytes() throws IOException {
/* 270 */     this.bb.compact();
/*     */     try {
/* 272 */       if (this.ch != null) {
/*     */         
/* 274 */         int j = this.ch.read(this.bb);
/* 275 */         if (j < 0) {
/* 276 */           return j;
/*     */         }
/*     */       } else {
/* 279 */         int j = this.bb.limit();
/* 280 */         int k = this.bb.position();
/* 281 */         assert k <= j;
/* 282 */         byte b = (k <= j) ? (j - k) : 0;
/* 283 */         assert !b;
/* 284 */         int m = this.in.read(this.bb.array(), this.bb.arrayOffset() + k, b);
/* 285 */         if (m < 0)
/* 286 */           return m; 
/* 287 */         if (m == 0)
/* 288 */           throw new IOException("Underlying input stream returned zero bytes"); 
/* 289 */         assert m <= b : "n = " + m + ", rem = " + b;
/* 290 */         this.bb.position(k + m);
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 295 */       this.bb.flip();
/*     */     } 
/*     */     
/* 298 */     int i = this.bb.remaining();
/* 299 */     assert i != 0 : i;
/* 300 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int implRead(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException {
/* 309 */     assert paramInt2 - paramInt1 > 1;
/*     */     
/* 311 */     CharBuffer charBuffer = CharBuffer.wrap(paramArrayOfchar, paramInt1, paramInt2 - paramInt1);
/* 312 */     if (charBuffer.position() != 0)
/*     */     {
/* 314 */       charBuffer = charBuffer.slice();
/*     */     }
/* 316 */     boolean bool = false;
/*     */     while (true) {
/* 318 */       CoderResult coderResult = this.decoder.decode(this.bb, charBuffer, bool);
/* 319 */       if (coderResult.isUnderflow()) {
/* 320 */         if (bool)
/*     */           break; 
/* 322 */         if (!charBuffer.hasRemaining())
/*     */           break; 
/* 324 */         if (charBuffer.position() > 0 && !inReady())
/*     */           break; 
/* 326 */         int i = readBytes();
/* 327 */         if (i < 0) {
/* 328 */           bool = true;
/* 329 */           if (charBuffer.position() == 0 && !this.bb.hasRemaining())
/*     */             break; 
/* 331 */           this.decoder.reset();
/*     */         } 
/*     */         continue;
/*     */       } 
/* 335 */       if (coderResult.isOverflow()) {
/* 336 */         assert charBuffer.position() > 0;
/*     */         break;
/*     */       } 
/* 339 */       coderResult.throwException();
/*     */     } 
/*     */     
/* 342 */     if (bool)
/*     */     {
/* 344 */       this.decoder.reset();
/*     */     }
/*     */     
/* 347 */     if (charBuffer.position() == 0) {
/* 348 */       if (bool)
/* 349 */         return -1; 
/*     */       assert false;
/*     */     } 
/* 352 */     return charBuffer.position();
/*     */   }
/*     */   
/*     */   String encodingName() {
/* 356 */     return (this.cs instanceof HistoricallyNamedCharset) ? ((HistoricallyNamedCharset)this.cs)
/* 357 */       .historicalName() : this.cs
/* 358 */       .name();
/*     */   }
/*     */   
/*     */   private boolean inReady() {
/*     */     try {
/* 363 */       return ((this.in != null && this.in.available() > 0) || this.ch instanceof FileChannel);
/*     */     }
/* 365 */     catch (IOException iOException) {
/* 366 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean implReady() {
/* 371 */     return (this.bb.hasRemaining() || inReady());
/*     */   }
/*     */   
/*     */   void implClose() throws IOException {
/* 375 */     if (this.ch != null) {
/* 376 */       this.ch.close();
/*     */     } else {
/* 378 */       this.in.close();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/cs/StreamDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */