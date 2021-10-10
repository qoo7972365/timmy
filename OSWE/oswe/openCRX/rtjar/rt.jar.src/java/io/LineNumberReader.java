/*     */ package java.io;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LineNumberReader
/*     */   extends BufferedReader
/*     */ {
/*  53 */   private int lineNumber = 0;
/*     */ 
/*     */   
/*     */   private int markedLineNumber;
/*     */ 
/*     */   
/*     */   private boolean skipLF;
/*     */ 
/*     */   
/*     */   private boolean markedSkipLF;
/*     */ 
/*     */   
/*     */   private static final int maxSkipBufferSize = 8192;
/*     */   
/*     */   private char[] skipBuffer;
/*     */ 
/*     */   
/*     */   public LineNumberReader(Reader paramReader)
/*     */   {
/*  72 */     super(paramReader);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     this.skipBuffer = null; } public LineNumberReader(Reader paramReader, int paramInt) { super(paramReader, paramInt); this.skipBuffer = null; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLineNumber(int paramInt) {
/*     */     this.lineNumber = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLineNumber() {
/*     */     return this.lineNumber;
/*     */   }
/*     */ 
/*     */   
/*     */   public long skip(long paramLong) throws IOException {
/* 230 */     if (paramLong < 0L)
/* 231 */       throw new IllegalArgumentException("skip() value is negative"); 
/* 232 */     int i = (int)Math.min(paramLong, 8192L);
/* 233 */     synchronized (this.lock) {
/* 234 */       if (this.skipBuffer == null || this.skipBuffer.length < i)
/* 235 */         this.skipBuffer = new char[i]; 
/* 236 */       long l = paramLong;
/* 237 */       while (l > 0L) {
/* 238 */         int j = read(this.skipBuffer, 0, (int)Math.min(l, i));
/* 239 */         if (j == -1)
/*     */           break; 
/* 241 */         l -= j;
/*     */       } 
/* 243 */       return paramLong - l;
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
/*     */   public void mark(int paramInt) throws IOException {
/* 261 */     synchronized (this.lock) {
/*     */ 
/*     */ 
/*     */       
/* 265 */       if (this.skipLF)
/* 266 */         paramInt++; 
/* 267 */       super.mark(paramInt);
/* 268 */       this.markedLineNumber = this.lineNumber;
/* 269 */       this.markedSkipLF = this.skipLF;
/*     */     } 
/*     */   } public int read() throws IOException { synchronized (this.lock) {
/*     */       int i = super.read(); if (this.skipLF) {
/*     */         if (i == 10)
/*     */           i = super.read();  this.skipLF = false;
/*     */       }  switch (i) {
/*     */         case 13:
/*     */           this.skipLF = true;
/*     */         case 10:
/*     */           this.lineNumber++; return 10;
/*     */       }  return i;
/* 281 */     }  } public void reset() throws IOException { synchronized (this.lock) {
/* 282 */       super.reset();
/* 283 */       this.lineNumber = this.markedLineNumber;
/* 284 */       this.skipLF = this.markedSkipLF;
/*     */     }  }
/*     */ 
/*     */   
/*     */   public int read(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException {
/*     */     synchronized (this.lock) {
/*     */       int i = super.read(paramArrayOfchar, paramInt1, paramInt2);
/*     */       for (int j = paramInt1; j < paramInt1 + i; j++) {
/*     */         char c = paramArrayOfchar[j];
/*     */         if (this.skipLF) {
/*     */           this.skipLF = false;
/*     */           if (c == '\n')
/*     */             continue; 
/*     */         } 
/*     */         switch (c) {
/*     */           case '\r':
/*     */             this.skipLF = true;
/*     */           case '\n':
/*     */             this.lineNumber++;
/*     */             break;
/*     */         } 
/*     */         continue;
/*     */       } 
/*     */       return i;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String readLine() throws IOException {
/*     */     synchronized (this.lock) {
/*     */       String str = readLine(this.skipLF);
/*     */       this.skipLF = false;
/*     */       if (str != null)
/*     */         this.lineNumber++; 
/*     */       return str;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/LineNumberReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */