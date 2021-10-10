/*     */ package java.io;
/*     */ 
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Arrays;
/*     */ import java.util.Formatter;
/*     */ import sun.misc.JavaIOAccess;
/*     */ import sun.misc.SharedSecrets;
/*     */ import sun.nio.cs.StreamDecoder;
/*     */ import sun.nio.cs.StreamEncoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Console
/*     */   implements Flushable
/*     */ {
/*     */   public PrintWriter writer() {
/* 101 */     return this.pw;
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
/*     */   public Reader reader() {
/* 136 */     return this.reader;
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
/*     */   public Console format(String paramString, Object... paramVarArgs) {
/* 170 */     this.formatter.format(paramString, paramVarArgs).flush();
/* 171 */     return this;
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
/*     */   public Console printf(String paramString, Object... paramVarArgs) {
/* 209 */     return format(paramString, paramVarArgs);
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
/*     */   public String readLine(String paramString, Object... paramVarArgs) {
/* 244 */     String str = null;
/* 245 */     synchronized (this.writeLock) {
/* 246 */       synchronized (this.readLock) {
/* 247 */         if (paramString.length() != 0)
/* 248 */           this.pw.format(paramString, paramVarArgs); 
/*     */         try {
/* 250 */           char[] arrayOfChar = readline(false);
/* 251 */           if (arrayOfChar != null)
/* 252 */             str = new String(arrayOfChar); 
/* 253 */         } catch (IOException iOException) {
/* 254 */           throw new IOError(iOException);
/*     */         } 
/*     */       } 
/*     */     } 
/* 258 */     return str;
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
/*     */   public String readLine() {
/* 272 */     return readLine("", new Object[0]);
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
/*     */   public char[] readPassword(String paramString, Object... paramVarArgs) {
/* 308 */     char[] arrayOfChar = null;
/* 309 */     synchronized (this.writeLock) {
/* 310 */       synchronized (this.readLock) {
/*     */         try {
/* 312 */           echoOff = echo(false);
/* 313 */         } catch (IOException iOException) {
/* 314 */           throw new IOError(iOException);
/*     */         } 
/* 316 */         IOError iOError = null;
/*     */         try {
/* 318 */           if (paramString.length() != 0)
/* 319 */             this.pw.format(paramString, paramVarArgs); 
/* 320 */           arrayOfChar = readline(true);
/* 321 */         } catch (IOException iOException) {
/* 322 */           iOError = new IOError(iOException);
/*     */         } finally {
/*     */           try {
/* 325 */             echoOff = echo(true);
/* 326 */           } catch (IOException iOException) {
/* 327 */             if (iOError == null) {
/* 328 */               iOError = new IOError(iOException);
/*     */             } else {
/* 330 */               iOError.addSuppressed(iOException);
/*     */             } 
/* 332 */           }  if (iOError != null)
/* 333 */             throw iOError; 
/*     */         } 
/* 335 */         this.pw.println();
/*     */       } 
/*     */     } 
/* 338 */     return arrayOfChar;
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
/*     */   public char[] readPassword() {
/* 352 */     return readPassword("", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {
/* 360 */     this.pw.flush();
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
/*     */   private char[] readline(boolean paramBoolean) throws IOException {
/* 376 */     int i = this.reader.read(this.rcb, 0, this.rcb.length);
/* 377 */     if (i < 0)
/* 378 */       return null; 
/* 379 */     if (this.rcb[i - 1] == '\r') {
/* 380 */       i--;
/*     */     } else {
/* 382 */       i--;
/* 383 */       if (this.rcb[i - 1] == '\n' && i > 0 && this.rcb[i - 1] == '\r')
/* 384 */         i--; 
/*     */     } 
/* 386 */     char[] arrayOfChar = new char[i];
/* 387 */     if (i > 0) {
/* 388 */       System.arraycopy(this.rcb, 0, arrayOfChar, 0, i);
/* 389 */       if (paramBoolean) {
/* 390 */         Arrays.fill(this.rcb, 0, i, ' ');
/*     */       }
/*     */     } 
/* 393 */     return arrayOfChar;
/*     */   }
/*     */   
/*     */   private char[] grow() {
/* 397 */     assert Thread.holdsLock(this.readLock);
/* 398 */     char[] arrayOfChar = new char[this.rcb.length * 2];
/* 399 */     System.arraycopy(this.rcb, 0, arrayOfChar, 0, this.rcb.length);
/* 400 */     this.rcb = arrayOfChar;
/* 401 */     return this.rcb;
/*     */   }
/*     */   
/*     */   class LineReader
/*     */     extends Reader {
/*     */     private Reader in;
/*     */     private char[] cb;
/*     */     
/*     */     LineReader(Reader param1Reader) {
/* 410 */       this.in = param1Reader;
/* 411 */       this.cb = new char[1024];
/* 412 */       this.nextChar = this.nChars = 0;
/* 413 */       this.leftoverLF = false;
/*     */     }
/*     */     private int nChars; private int nextChar; boolean leftoverLF;
/*     */     public void close() {}
/*     */     public boolean ready() throws IOException {
/* 418 */       return this.in.ready();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int read(char[] param1ArrayOfchar, int param1Int1, int param1Int2) throws IOException {
/* 424 */       int i = param1Int1;
/* 425 */       int j = param1Int1 + param1Int2;
/* 426 */       if (param1Int1 < 0 || param1Int1 > param1ArrayOfchar.length || param1Int2 < 0 || j < 0 || j > param1ArrayOfchar.length)
/*     */       {
/* 428 */         throw new IndexOutOfBoundsException();
/*     */       }
/* 430 */       synchronized (Console.this.readLock) {
/* 431 */         boolean bool = false;
/* 432 */         char c = Character.MIN_VALUE;
/*     */         while (true) {
/* 434 */           if (this.nextChar >= this.nChars) {
/* 435 */             int k = 0;
/*     */             while (true) {
/* 437 */               k = this.in.read(this.cb, 0, this.cb.length);
/* 438 */               if (k != 0) {
/* 439 */                 if (k > 0) {
/* 440 */                   this.nChars = k;
/* 441 */                   this.nextChar = 0;
/* 442 */                   if (k < this.cb.length && this.cb[k - 1] != '\n' && this.cb[k - 1] != '\r')
/*     */                   {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/* 449 */                     bool = true; } 
/*     */                   break;
/*     */                 } 
/* 452 */                 if (i - param1Int1 == 0)
/* 453 */                   return -1; 
/* 454 */                 return i - param1Int1;
/*     */               } 
/*     */             } 
/* 457 */           }  if (this.leftoverLF && param1ArrayOfchar == Console.this.rcb && this.cb[this.nextChar] == '\n')
/*     */           {
/*     */ 
/*     */ 
/*     */             
/* 462 */             this.nextChar++;
/*     */           }
/* 464 */           this.leftoverLF = false;
/* 465 */           while (this.nextChar < this.nChars) {
/* 466 */             c = param1ArrayOfchar[i++] = this.cb[this.nextChar];
/* 467 */             this.cb[this.nextChar++] = Character.MIN_VALUE;
/* 468 */             if (c == '\n')
/* 469 */               return i - param1Int1; 
/* 470 */             if (c == '\r') {
/* 471 */               if (i == j)
/*     */               {
/*     */ 
/*     */ 
/*     */                 
/* 476 */                 if (param1ArrayOfchar == Console.this.rcb) {
/* 477 */                   param1ArrayOfchar = Console.this.grow();
/* 478 */                   j = param1ArrayOfchar.length;
/*     */                 } else {
/* 480 */                   this.leftoverLF = true;
/* 481 */                   return i - param1Int1;
/*     */                 } 
/*     */               }
/* 484 */               if (this.nextChar == this.nChars && this.in.ready()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 492 */                 this.nChars = this.in.read(this.cb, 0, this.cb.length);
/* 493 */                 this.nextChar = 0;
/*     */               } 
/* 495 */               if (this.nextChar < this.nChars && this.cb[this.nextChar] == '\n') {
/* 496 */                 param1ArrayOfchar[i++] = '\n';
/* 497 */                 this.nextChar++;
/*     */               } 
/* 499 */               return i - param1Int1;
/* 500 */             }  if (i == j) {
/* 501 */               if (param1ArrayOfchar == Console.this.rcb) {
/* 502 */                 param1ArrayOfchar = Console.this.grow();
/* 503 */                 j = param1ArrayOfchar.length; continue;
/*     */               } 
/* 505 */               return i - param1Int1;
/*     */             } 
/*     */           } 
/*     */           
/* 509 */           if (bool) {
/* 510 */             return i - param1Int1;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 521 */       SharedSecrets.getJavaLangAccess()
/* 522 */         .registerShutdownHook(0, false, new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/*     */               try {
/* 527 */                 if (Console.echoOff) {
/* 528 */                   Console.echo(true);
/*     */                 }
/* 530 */               } catch (IOException iOException) {}
/*     */             }
/*     */           });
/* 533 */     } catch (IllegalStateException illegalStateException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 538 */     SharedSecrets.setJavaIOAccess(new JavaIOAccess() {
/*     */           public Console console() {
/* 540 */             if (Console.istty()) {
/* 541 */               if (Console.cons == null)
/* 542 */                 Console.cons = new Console(); 
/* 543 */               return Console.cons;
/*     */             } 
/* 545 */             return null;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Charset charset() {
/* 551 */             return Console.cons.cs;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 558 */   private Object readLock = new Object();
/* 559 */   private Object writeLock = new Object(); private Console() {
/* 560 */     String str = encoding();
/* 561 */     if (str != null) {
/*     */       try {
/* 563 */         this.cs = Charset.forName(str);
/* 564 */       } catch (Exception exception) {}
/*     */     }
/* 566 */     if (this.cs == null)
/* 567 */       this.cs = Charset.defaultCharset(); 
/* 568 */     this.out = StreamEncoder.forOutputStreamWriter(new FileOutputStream(FileDescriptor.out), this.writeLock, this.cs);
/*     */ 
/*     */ 
/*     */     
/* 572 */     this.pw = new PrintWriter(this.out, true) { public void close() {} };
/* 573 */     this.formatter = new Formatter(this.out);
/* 574 */     this.reader = new LineReader(StreamDecoder.forInputStreamReader(new FileInputStream(FileDescriptor.in), this.readLock, this.cs));
/*     */ 
/*     */ 
/*     */     
/* 578 */     this.rcb = new char[1024];
/*     */   }
/*     */   
/*     */   private Reader reader;
/*     */   private Writer out;
/*     */   private PrintWriter pw;
/*     */   private Formatter formatter;
/*     */   private Charset cs;
/*     */   private char[] rcb;
/*     */   private static boolean echoOff;
/*     */   private static Console cons;
/*     */   
/*     */   private static native String encoding();
/*     */   
/*     */   private static native boolean echo(boolean paramBoolean) throws IOException;
/*     */   
/*     */   private static native boolean istty();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/Console.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */