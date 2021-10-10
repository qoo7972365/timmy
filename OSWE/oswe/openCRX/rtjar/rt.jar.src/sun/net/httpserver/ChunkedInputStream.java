/*     */ package sun.net.httpserver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ChunkedInputStream
/*     */   extends LeftOverInputStream
/*     */ {
/*     */   private int remaining;
/*     */   private boolean needToReadHeader;
/*     */   static final char CR = '\r';
/*     */   static final char LF = '\n';
/*     */   private static final int MAX_CHUNK_HEADER_SIZE = 2050;
/*     */   
/*     */   ChunkedInputStream(ExchangeImpl paramExchangeImpl, InputStream paramInputStream) {
/*  35 */     super(paramExchangeImpl, paramInputStream);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  42 */     this.needToReadHeader = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int numeric(char[] paramArrayOfchar, int paramInt) throws IOException {
/*  52 */     assert paramArrayOfchar.length >= paramInt;
/*  53 */     int i = 0;
/*  54 */     for (byte b = 0; b < paramInt; b++) {
/*  55 */       char c = paramArrayOfchar[b];
/*  56 */       int j = 0;
/*  57 */       if (c >= '0' && c <= '9') {
/*  58 */         j = c - 48;
/*  59 */       } else if (c >= 'a' && c <= 'f') {
/*  60 */         j = c - 97 + 10;
/*  61 */       } else if (c >= 'A' && c <= 'F') {
/*  62 */         j = c - 65 + 10;
/*     */       } else {
/*  64 */         throw new IOException("invalid chunk length");
/*     */       } 
/*  66 */       i = i * 16 + j;
/*     */     } 
/*  68 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int readChunkHeader() throws IOException {
/*  75 */     boolean bool1 = false;
/*     */     
/*  77 */     char[] arrayOfChar = new char[16];
/*  78 */     byte b1 = 0;
/*  79 */     boolean bool2 = false;
/*  80 */     byte b2 = 0;
/*     */     int i;
/*  82 */     while ((i = this.in.read()) != -1) {
/*  83 */       char c = (char)i;
/*  84 */       b2++;
/*  85 */       if (b1 == arrayOfChar.length - 1 || b2 > 'ࠂ')
/*     */       {
/*     */         
/*  88 */         throw new IOException("invalid chunk header");
/*     */       }
/*  90 */       if (bool1) {
/*  91 */         if (c == '\n') {
/*  92 */           return numeric(arrayOfChar, b1);
/*     */         }
/*     */         
/*  95 */         bool1 = false;
/*     */         
/*  97 */         if (!bool2)
/*  98 */           arrayOfChar[b1++] = c; 
/*     */         continue;
/*     */       } 
/* 101 */       if (c == '\r') {
/* 102 */         bool1 = true; continue;
/* 103 */       }  if (c == ';') {
/* 104 */         bool2 = true; continue;
/* 105 */       }  if (!bool2) {
/* 106 */         arrayOfChar[b1++] = c;
/*     */       }
/*     */     } 
/*     */     
/* 110 */     throw new IOException("end of stream reading chunk header");
/*     */   }
/*     */   
/*     */   protected int readImpl(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 114 */     if (this.eof) {
/* 115 */       return -1;
/*     */     }
/* 117 */     if (this.needToReadHeader) {
/* 118 */       this.remaining = readChunkHeader();
/* 119 */       if (this.remaining == 0) {
/* 120 */         this.eof = true;
/* 121 */         consumeCRLF();
/* 122 */         this.t.getServerImpl().requestCompleted(this.t.getConnection());
/* 123 */         return -1;
/*     */       } 
/* 125 */       this.needToReadHeader = false;
/*     */     } 
/* 127 */     if (paramInt2 > this.remaining) {
/* 128 */       paramInt2 = this.remaining;
/*     */     }
/* 130 */     int i = this.in.read(paramArrayOfbyte, paramInt1, paramInt2);
/* 131 */     if (i > -1) {
/* 132 */       this.remaining -= i;
/*     */     }
/* 134 */     if (this.remaining == 0) {
/* 135 */       this.needToReadHeader = true;
/* 136 */       consumeCRLF();
/*     */     } 
/* 138 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private void consumeCRLF() throws IOException {
/* 143 */     char c = (char)this.in.read();
/* 144 */     if (c != '\r') {
/* 145 */       throw new IOException("invalid chunk end");
/*     */     }
/* 147 */     c = (char)this.in.read();
/* 148 */     if (c != '\n') {
/* 149 */       throw new IOException("invalid chunk end");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int available() throws IOException {
/* 160 */     if (this.eof || this.closed) {
/* 161 */       return 0;
/*     */     }
/* 163 */     int i = this.in.available();
/* 164 */     return (i > this.remaining) ? this.remaining : i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDataBuffered() throws IOException {
/* 172 */     assert this.eof;
/* 173 */     return (this.in.available() > 0);
/*     */   }
/*     */   public boolean markSupported() {
/* 176 */     return false;
/*     */   }
/*     */   
/*     */   public void mark(int paramInt) {}
/*     */   
/*     */   public void reset() throws IOException {
/* 182 */     throw new IOException("mark/reset not supported");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/httpserver/ChunkedInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */