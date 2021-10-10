/*      */ package java.io;
/*      */ 
/*      */ import java.nio.charset.Charset;
/*      */ import java.nio.charset.IllegalCharsetNameException;
/*      */ import java.util.Formatter;
/*      */ import java.util.Locale;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PrintStream
/*      */   extends FilterOutputStream
/*      */   implements Appendable, Closeable
/*      */ {
/*      */   private final boolean autoFlush;
/*      */   private boolean trouble = false;
/*      */   private Formatter formatter;
/*      */   private BufferedWriter textOut;
/*      */   private OutputStreamWriter charOut;
/*      */   private boolean closing;
/*      */   
/*      */   private static <T> T requireNonNull(T paramT, String paramString) {
/*   78 */     if (paramT == null)
/*   79 */       throw new NullPointerException(paramString); 
/*   80 */     return paramT;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Charset toCharset(String paramString) throws UnsupportedEncodingException {
/*   91 */     requireNonNull(paramString, "charsetName");
/*      */     try {
/*   93 */       return Charset.forName(paramString);
/*   94 */     } catch (IllegalCharsetNameException|java.nio.charset.UnsupportedCharsetException illegalCharsetNameException) {
/*      */       
/*   96 */       throw new UnsupportedEncodingException(paramString);
/*      */     } 
/*      */   }
/*      */   
/*      */   private PrintStream(boolean paramBoolean, OutputStream paramOutputStream)
/*      */   {
/*  102 */     super(paramOutputStream);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  346 */     this.closing = false; this.autoFlush = paramBoolean; this.charOut = new OutputStreamWriter(this); this.textOut = new BufferedWriter(this.charOut); } private PrintStream(boolean paramBoolean, Charset paramCharset, OutputStream paramOutputStream) throws UnsupportedEncodingException { this(paramBoolean, paramOutputStream, paramCharset); } public PrintStream(OutputStream paramOutputStream) { this(paramOutputStream, false); } public PrintStream(OutputStream paramOutputStream, boolean paramBoolean) { this(paramBoolean, requireNonNull(paramOutputStream, "Null output stream")); } private PrintStream(boolean paramBoolean, OutputStream paramOutputStream, Charset paramCharset) { super(paramOutputStream); this.closing = false;
/*      */     this.autoFlush = paramBoolean;
/*      */     this.charOut = new OutputStreamWriter(this, paramCharset);
/*      */     this.textOut = new BufferedWriter(this.charOut); }
/*      */    public PrintStream(OutputStream paramOutputStream, boolean paramBoolean, String paramString) throws UnsupportedEncodingException {
/*      */     this(paramBoolean, requireNonNull(paramOutputStream, "Null output stream"), toCharset(paramString));
/*      */   } public PrintStream(String paramString) throws FileNotFoundException {
/*      */     this(false, new FileOutputStream(paramString));
/*      */   } public void close() {
/*  355 */     synchronized (this) {
/*  356 */       if (!this.closing) {
/*  357 */         this.closing = true;
/*      */         try {
/*  359 */           this.textOut.close();
/*  360 */           this.out.close();
/*      */         }
/*  362 */         catch (IOException iOException) {
/*  363 */           this.trouble = true;
/*      */         } 
/*  365 */         this.textOut = null;
/*  366 */         this.charOut = null;
/*  367 */         this.out = null;
/*      */       } 
/*      */     } 
/*      */   } public PrintStream(String paramString1, String paramString2) throws FileNotFoundException, UnsupportedEncodingException {
/*      */     this(false, toCharset(paramString2), new FileOutputStream(paramString1));
/*      */   } public PrintStream(File paramFile) throws FileNotFoundException {
/*      */     this(false, new FileOutputStream(paramFile));
/*      */   } public PrintStream(File paramFile, String paramString) throws FileNotFoundException, UnsupportedEncodingException {
/*      */     this(false, toCharset(paramString), new FileOutputStream(paramFile));
/*      */   } private void ensureOpen() throws IOException {
/*      */     if (this.out == null)
/*      */       throw new IOException("Stream closed"); 
/*      */   }
/*      */   public void flush() {
/*      */     synchronized (this) {
/*      */       try {
/*      */         ensureOpen();
/*      */         this.out.flush();
/*      */       } catch (IOException iOException) {
/*      */         this.trouble = true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   public boolean checkError() {
/*  391 */     if (this.out != null)
/*  392 */       flush(); 
/*  393 */     if (this.out instanceof PrintStream) {
/*  394 */       PrintStream printStream = (PrintStream)this.out;
/*  395 */       return printStream.checkError();
/*      */     } 
/*  397 */     return this.trouble;
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
/*      */   protected void setError() {
/*  410 */     this.trouble = true;
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
/*      */   protected void clearError() {
/*  423 */     this.trouble = false;
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
/*      */   public void write(int paramInt) {
/*      */     try {
/*  447 */       synchronized (this) {
/*  448 */         ensureOpen();
/*  449 */         this.out.write(paramInt);
/*  450 */         if (paramInt == 10 && this.autoFlush) {
/*  451 */           this.out.flush();
/*      */         }
/*      */       } 
/*  454 */     } catch (InterruptedIOException interruptedIOException) {
/*  455 */       Thread.currentThread().interrupt();
/*      */     }
/*  457 */     catch (IOException iOException) {
/*  458 */       this.trouble = true;
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
/*      */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*      */     try {
/*  478 */       synchronized (this) {
/*  479 */         ensureOpen();
/*  480 */         this.out.write(paramArrayOfbyte, paramInt1, paramInt2);
/*  481 */         if (this.autoFlush) {
/*  482 */           this.out.flush();
/*      */         }
/*      */       } 
/*  485 */     } catch (InterruptedIOException interruptedIOException) {
/*  486 */       Thread.currentThread().interrupt();
/*      */     }
/*  488 */     catch (IOException iOException) {
/*  489 */       this.trouble = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void write(char[] paramArrayOfchar) {
/*      */     try {
/*  501 */       synchronized (this) {
/*  502 */         ensureOpen();
/*  503 */         this.textOut.write(paramArrayOfchar);
/*  504 */         this.textOut.flushBuffer();
/*  505 */         this.charOut.flushBuffer();
/*  506 */         if (this.autoFlush)
/*  507 */           for (byte b = 0; b < paramArrayOfchar.length; b++) {
/*  508 */             if (paramArrayOfchar[b] == '\n') {
/*  509 */               this.out.flush();
/*      */             }
/*      */           }  
/*      */       } 
/*  513 */     } catch (InterruptedIOException interruptedIOException) {
/*  514 */       Thread.currentThread().interrupt();
/*      */     }
/*  516 */     catch (IOException iOException) {
/*  517 */       this.trouble = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void write(String paramString) {
/*      */     try {
/*  523 */       synchronized (this) {
/*  524 */         ensureOpen();
/*  525 */         this.textOut.write(paramString);
/*  526 */         this.textOut.flushBuffer();
/*  527 */         this.charOut.flushBuffer();
/*  528 */         if (this.autoFlush && paramString.indexOf('\n') >= 0) {
/*  529 */           this.out.flush();
/*      */         }
/*      */       } 
/*  532 */     } catch (InterruptedIOException interruptedIOException) {
/*  533 */       Thread.currentThread().interrupt();
/*      */     }
/*  535 */     catch (IOException iOException) {
/*  536 */       this.trouble = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void newLine() {
/*      */     try {
/*  542 */       synchronized (this) {
/*  543 */         ensureOpen();
/*  544 */         this.textOut.newLine();
/*  545 */         this.textOut.flushBuffer();
/*  546 */         this.charOut.flushBuffer();
/*  547 */         if (this.autoFlush) {
/*  548 */           this.out.flush();
/*      */         }
/*      */       } 
/*  551 */     } catch (InterruptedIOException interruptedIOException) {
/*  552 */       Thread.currentThread().interrupt();
/*      */     }
/*  554 */     catch (IOException iOException) {
/*  555 */       this.trouble = true;
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
/*      */   public void print(boolean paramBoolean) {
/*  571 */     write(paramBoolean ? "true" : "false");
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
/*      */   public void print(char paramChar) {
/*  583 */     write(String.valueOf(paramChar));
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
/*      */   public void print(int paramInt) {
/*  597 */     write(String.valueOf(paramInt));
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
/*      */   public void print(long paramLong) {
/*  611 */     write(String.valueOf(paramLong));
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
/*      */   public void print(float paramFloat) {
/*  625 */     write(String.valueOf(paramFloat));
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
/*      */   public void print(double paramDouble) {
/*  639 */     write(String.valueOf(paramDouble));
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
/*      */   public void print(char[] paramArrayOfchar) {
/*  653 */     write(paramArrayOfchar);
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
/*      */   public void print(String paramString) {
/*  666 */     if (paramString == null) {
/*  667 */       paramString = "null";
/*      */     }
/*  669 */     write(paramString);
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
/*      */   public void print(Object paramObject) {
/*  683 */     write(String.valueOf(paramObject));
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
/*      */   public void println() {
/*  696 */     newLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void println(boolean paramBoolean) {
/*  707 */     synchronized (this) {
/*  708 */       print(paramBoolean);
/*  709 */       newLine();
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
/*      */   public void println(char paramChar) {
/*  721 */     synchronized (this) {
/*  722 */       print(paramChar);
/*  723 */       newLine();
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
/*      */   public void println(int paramInt) {
/*  735 */     synchronized (this) {
/*  736 */       print(paramInt);
/*  737 */       newLine();
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
/*      */   public void println(long paramLong) {
/*  749 */     synchronized (this) {
/*  750 */       print(paramLong);
/*  751 */       newLine();
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
/*      */   public void println(float paramFloat) {
/*  763 */     synchronized (this) {
/*  764 */       print(paramFloat);
/*  765 */       newLine();
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
/*      */   public void println(double paramDouble) {
/*  777 */     synchronized (this) {
/*  778 */       print(paramDouble);
/*  779 */       newLine();
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
/*      */   public void println(char[] paramArrayOfchar) {
/*  791 */     synchronized (this) {
/*  792 */       print(paramArrayOfchar);
/*  793 */       newLine();
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
/*      */   public void println(String paramString) {
/*  805 */     synchronized (this) {
/*  806 */       print(paramString);
/*  807 */       newLine();
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
/*      */   public void println(Object paramObject) {
/*  821 */     String str = String.valueOf(paramObject);
/*  822 */     synchronized (this) {
/*  823 */       print(str);
/*  824 */       newLine();
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
/*      */   public PrintStream printf(String paramString, Object... paramVarArgs) {
/*  871 */     return format(paramString, paramVarArgs);
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
/*      */   public PrintStream printf(Locale paramLocale, String paramString, Object... paramVarArgs) {
/*  921 */     return format(paramLocale, paramString, paramVarArgs);
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
/*      */   public PrintStream format(String paramString, Object... paramVarArgs) {
/*      */     try {
/*  965 */       synchronized (this) {
/*  966 */         ensureOpen();
/*  967 */         if (this.formatter == null || this.formatter
/*  968 */           .locale() != Locale.getDefault())
/*  969 */           this.formatter = new Formatter(this); 
/*  970 */         this.formatter.format(Locale.getDefault(), paramString, paramVarArgs);
/*      */       } 
/*  972 */     } catch (InterruptedIOException interruptedIOException) {
/*  973 */       Thread.currentThread().interrupt();
/*  974 */     } catch (IOException iOException) {
/*  975 */       this.trouble = true;
/*      */     } 
/*  977 */     return this;
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
/*      */   public PrintStream format(Locale paramLocale, String paramString, Object... paramVarArgs) {
/*      */     try {
/* 1022 */       synchronized (this) {
/* 1023 */         ensureOpen();
/* 1024 */         if (this.formatter == null || this.formatter
/* 1025 */           .locale() != paramLocale)
/* 1026 */           this.formatter = new Formatter(this, paramLocale); 
/* 1027 */         this.formatter.format(paramLocale, paramString, paramVarArgs);
/*      */       } 
/* 1029 */     } catch (InterruptedIOException interruptedIOException) {
/* 1030 */       Thread.currentThread().interrupt();
/* 1031 */     } catch (IOException iOException) {
/* 1032 */       this.trouble = true;
/*      */     } 
/* 1034 */     return this;
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
/*      */   public PrintStream append(CharSequence paramCharSequence) {
/* 1062 */     if (paramCharSequence == null) {
/* 1063 */       print("null");
/*      */     } else {
/* 1065 */       print(paramCharSequence.toString());
/* 1066 */     }  return this;
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
/*      */   public PrintStream append(CharSequence paramCharSequence, int paramInt1, int paramInt2) {
/* 1103 */     CharSequence charSequence = (paramCharSequence == null) ? "null" : paramCharSequence;
/* 1104 */     write(charSequence.subSequence(paramInt1, paramInt2).toString());
/* 1105 */     return this;
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
/*      */   public PrintStream append(char paramChar) {
/* 1125 */     print(paramChar);
/* 1126 */     return this;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/PrintStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */