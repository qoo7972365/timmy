/*     */ package java.io;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Spliterators;
/*     */ import java.util.stream.Stream;
/*     */ import java.util.stream.StreamSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BufferedReader
/*     */   extends Reader
/*     */ {
/*     */   private Reader in;
/*     */   private char[] cb;
/*     */   private int nChars;
/*     */   private int nextChar;
/*     */   private static final int INVALIDATED = -2;
/*     */   private static final int UNMARKED = -1;
/*  79 */   private int markedChar = -1;
/*  80 */   private int readAheadLimit = 0;
/*     */ 
/*     */   
/*     */   private boolean skipLF = false;
/*     */ 
/*     */   
/*     */   private boolean markedSkipLF = false;
/*     */   
/*  88 */   private static int defaultCharBufferSize = 8192;
/*  89 */   private static int defaultExpectedLineLength = 80;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferedReader(Reader paramReader, int paramInt) {
/* 101 */     super(paramReader);
/* 102 */     if (paramInt <= 0)
/* 103 */       throw new IllegalArgumentException("Buffer size <= 0"); 
/* 104 */     this.in = paramReader;
/* 105 */     this.cb = new char[paramInt];
/* 106 */     this.nextChar = this.nChars = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferedReader(Reader paramReader) {
/* 116 */     this(paramReader, defaultCharBufferSize);
/*     */   }
/*     */ 
/*     */   
/*     */   private void ensureOpen() throws IOException {
/* 121 */     if (this.in == null) {
/* 122 */       throw new IOException("Stream closed");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void fill() throws IOException {
/*     */     int i;
/* 130 */     if (this.markedChar <= -1) {
/*     */       
/* 132 */       i = 0;
/*     */     } else {
/*     */       
/* 135 */       int j = this.nextChar - this.markedChar;
/* 136 */       if (j >= this.readAheadLimit) {
/*     */         
/* 138 */         this.markedChar = -2;
/* 139 */         this.readAheadLimit = 0;
/* 140 */         i = 0;
/*     */       } else {
/* 142 */         if (this.readAheadLimit <= this.cb.length) {
/*     */           
/* 144 */           System.arraycopy(this.cb, this.markedChar, this.cb, 0, j);
/* 145 */           this.markedChar = 0;
/* 146 */           i = j;
/*     */         } else {
/*     */           
/* 149 */           char[] arrayOfChar = new char[this.readAheadLimit];
/* 150 */           System.arraycopy(this.cb, this.markedChar, arrayOfChar, 0, j);
/* 151 */           this.cb = arrayOfChar;
/* 152 */           this.markedChar = 0;
/* 153 */           i = j;
/*     */         } 
/* 155 */         this.nextChar = this.nChars = j;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     while (true) {
/* 161 */       int j = this.in.read(this.cb, i, this.cb.length - i);
/* 162 */       if (j != 0) {
/* 163 */         if (j > 0) {
/* 164 */           this.nChars = i + j;
/* 165 */           this.nextChar = i;
/*     */         } 
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/* 178 */     synchronized (this.lock) {
/* 179 */       ensureOpen();
/*     */       while (true) {
/* 181 */         if (this.nextChar >= this.nChars) {
/* 182 */           fill();
/* 183 */           if (this.nextChar >= this.nChars)
/* 184 */             return -1; 
/*     */         } 
/* 186 */         if (this.skipLF) {
/* 187 */           this.skipLF = false;
/* 188 */           if (this.cb[this.nextChar] == '\n') {
/* 189 */             this.nextChar++; continue;
/*     */           } 
/*     */         }  break;
/*     */       } 
/* 193 */       return this.cb[this.nextChar++];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int read1(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException {
/* 203 */     if (this.nextChar >= this.nChars) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 209 */       if (paramInt2 >= this.cb.length && this.markedChar <= -1 && !this.skipLF) {
/* 210 */         return this.in.read(paramArrayOfchar, paramInt1, paramInt2);
/*     */       }
/* 212 */       fill();
/*     */     } 
/* 214 */     if (this.nextChar >= this.nChars) return -1; 
/* 215 */     if (this.skipLF) {
/* 216 */       this.skipLF = false;
/* 217 */       if (this.cb[this.nextChar] == '\n') {
/* 218 */         this.nextChar++;
/* 219 */         if (this.nextChar >= this.nChars)
/* 220 */           fill(); 
/* 221 */         if (this.nextChar >= this.nChars)
/* 222 */           return -1; 
/*     */       } 
/*     */     } 
/* 225 */     int i = Math.min(paramInt2, this.nChars - this.nextChar);
/* 226 */     System.arraycopy(this.cb, this.nextChar, paramArrayOfchar, paramInt1, i);
/* 227 */     this.nextChar += i;
/* 228 */     return i;
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
/*     */   public int read(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException {
/* 277 */     synchronized (this.lock) {
/* 278 */       ensureOpen();
/* 279 */       if (paramInt1 < 0 || paramInt1 > paramArrayOfchar.length || paramInt2 < 0 || paramInt1 + paramInt2 > paramArrayOfchar.length || paramInt1 + paramInt2 < 0)
/*     */       {
/* 281 */         throw new IndexOutOfBoundsException(); } 
/* 282 */       if (paramInt2 == 0) {
/* 283 */         return 0;
/*     */       }
/*     */       
/* 286 */       int i = read1(paramArrayOfchar, paramInt1, paramInt2);
/* 287 */       if (i <= 0) return i; 
/* 288 */       while (i < paramInt2 && this.in.ready()) {
/* 289 */         int j = read1(paramArrayOfchar, paramInt1 + i, paramInt2 - i);
/* 290 */         if (j <= 0)
/* 291 */           break;  i += j;
/*     */       } 
/* 293 */       return i;
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
/*     */   String readLine(boolean paramBoolean) throws IOException {
/* 313 */     StringBuffer stringBuffer = null;
/*     */ 
/*     */     
/* 316 */     synchronized (this.lock) {
/* 317 */       ensureOpen();
/* 318 */       boolean bool = (paramBoolean || this.skipLF) ? true : false;
/*     */ 
/*     */ 
/*     */       
/*     */       while (true) {
/* 323 */         if (this.nextChar >= this.nChars)
/* 324 */           fill(); 
/* 325 */         if (this.nextChar >= this.nChars) {
/* 326 */           if (stringBuffer != null && stringBuffer.length() > 0) {
/* 327 */             return stringBuffer.toString();
/*     */           }
/* 329 */           return null;
/*     */         } 
/* 331 */         boolean bool1 = false;
/* 332 */         char c = Character.MIN_VALUE;
/*     */ 
/*     */ 
/*     */         
/* 336 */         if (bool && this.cb[this.nextChar] == '\n')
/* 337 */           this.nextChar++; 
/* 338 */         this.skipLF = false;
/* 339 */         bool = false;
/*     */         
/*     */         int j;
/* 342 */         for (j = this.nextChar; j < this.nChars; j++) {
/* 343 */           c = this.cb[j];
/* 344 */           if (c == '\n' || c == '\r') {
/* 345 */             bool1 = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 350 */         int i = this.nextChar;
/* 351 */         this.nextChar = j;
/*     */         
/* 353 */         if (bool1) {
/*     */           String str;
/* 355 */           if (stringBuffer == null) {
/* 356 */             str = new String(this.cb, i, j - i);
/*     */           } else {
/* 358 */             stringBuffer.append(this.cb, i, j - i);
/* 359 */             str = stringBuffer.toString();
/*     */           } 
/* 361 */           this.nextChar++;
/* 362 */           if (c == '\r') {
/* 363 */             this.skipLF = true;
/*     */           }
/* 365 */           return str;
/*     */         } 
/*     */         
/* 368 */         if (stringBuffer == null)
/* 369 */           stringBuffer = new StringBuffer(defaultExpectedLineLength); 
/* 370 */         stringBuffer.append(this.cb, i, j - i);
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
/*     */   
/*     */   public String readLine() throws IOException {
/* 389 */     return readLine(false);
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
/*     */   public long skip(long paramLong) throws IOException {
/* 403 */     if (paramLong < 0L) {
/* 404 */       throw new IllegalArgumentException("skip value is negative");
/*     */     }
/* 406 */     synchronized (this.lock) {
/* 407 */       ensureOpen();
/* 408 */       long l = paramLong;
/* 409 */       while (l > 0L) {
/* 410 */         if (this.nextChar >= this.nChars)
/* 411 */           fill(); 
/* 412 */         if (this.nextChar >= this.nChars)
/*     */           break; 
/* 414 */         if (this.skipLF) {
/* 415 */           this.skipLF = false;
/* 416 */           if (this.cb[this.nextChar] == '\n') {
/* 417 */             this.nextChar++;
/*     */           }
/*     */         } 
/* 420 */         long l1 = (this.nChars - this.nextChar);
/* 421 */         if (l <= l1) {
/* 422 */           this.nextChar = (int)(this.nextChar + l);
/* 423 */           l = 0L;
/*     */           
/*     */           break;
/*     */         } 
/* 427 */         l -= l1;
/* 428 */         this.nextChar = this.nChars;
/*     */       } 
/*     */       
/* 431 */       return paramLong - l;
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
/*     */   public boolean ready() throws IOException {
/* 443 */     synchronized (this.lock) {
/* 444 */       ensureOpen();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 450 */       if (this.skipLF) {
/*     */ 
/*     */ 
/*     */         
/* 454 */         if (this.nextChar >= this.nChars && this.in.ready()) {
/* 455 */           fill();
/*     */         }
/* 457 */         if (this.nextChar < this.nChars) {
/* 458 */           if (this.cb[this.nextChar] == '\n')
/* 459 */             this.nextChar++; 
/* 460 */           this.skipLF = false;
/*     */         } 
/*     */       } 
/* 463 */       return (this.nextChar < this.nChars || this.in.ready());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean markSupported() {
/* 471 */     return true;
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
/*     */   public void mark(int paramInt) throws IOException {
/* 491 */     if (paramInt < 0) {
/* 492 */       throw new IllegalArgumentException("Read-ahead limit < 0");
/*     */     }
/* 494 */     synchronized (this.lock) {
/* 495 */       ensureOpen();
/* 496 */       this.readAheadLimit = paramInt;
/* 497 */       this.markedChar = this.nextChar;
/* 498 */       this.markedSkipLF = this.skipLF;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() throws IOException {
/* 509 */     synchronized (this.lock) {
/* 510 */       ensureOpen();
/* 511 */       if (this.markedChar < 0) {
/* 512 */         throw new IOException((this.markedChar == -2) ? "Mark invalid" : "Stream not marked");
/*     */       }
/*     */       
/* 515 */       this.nextChar = this.markedChar;
/* 516 */       this.skipLF = this.markedSkipLF;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 521 */     synchronized (this.lock) {
/* 522 */       if (this.in == null)
/*     */         return; 
/*     */       try {
/* 525 */         this.in.close();
/*     */       } finally {
/* 527 */         this.in = null;
/* 528 */         this.cb = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stream<String> lines() {
/* 562 */     Iterator<String> iterator = new Iterator<String>() {
/* 563 */         String nextLine = null;
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 567 */           if (this.nextLine != null) {
/* 568 */             return true;
/*     */           }
/*     */           try {
/* 571 */             this.nextLine = BufferedReader.this.readLine();
/* 572 */             return (this.nextLine != null);
/* 573 */           } catch (IOException iOException) {
/* 574 */             throw new UncheckedIOException(iOException);
/*     */           } 
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public String next() {
/* 581 */           if (this.nextLine != null || hasNext()) {
/* 582 */             String str = this.nextLine;
/* 583 */             this.nextLine = null;
/* 584 */             return str;
/*     */           } 
/* 586 */           throw new NoSuchElementException();
/*     */         }
/*     */       };
/*     */     
/* 590 */     return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 272), false);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/BufferedReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */