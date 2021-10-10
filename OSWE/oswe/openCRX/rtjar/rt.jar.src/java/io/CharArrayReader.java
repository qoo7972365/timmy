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
/*     */ public class CharArrayReader
/*     */   extends Reader
/*     */ {
/*     */   protected char[] buf;
/*     */   protected int pos;
/*  43 */   protected int markedPos = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int count;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharArrayReader(char[] paramArrayOfchar) {
/*  56 */     this.buf = paramArrayOfchar;
/*  57 */     this.pos = 0;
/*  58 */     this.count = paramArrayOfchar.length;
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
/*     */   public CharArrayReader(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/*  79 */     if (paramInt1 < 0 || paramInt1 > paramArrayOfchar.length || paramInt2 < 0 || paramInt1 + paramInt2 < 0)
/*     */     {
/*  81 */       throw new IllegalArgumentException();
/*     */     }
/*  83 */     this.buf = paramArrayOfchar;
/*  84 */     this.pos = paramInt1;
/*  85 */     this.count = Math.min(paramInt1 + paramInt2, paramArrayOfchar.length);
/*  86 */     this.markedPos = paramInt1;
/*     */   }
/*     */ 
/*     */   
/*     */   private void ensureOpen() throws IOException {
/*  91 */     if (this.buf == null) {
/*  92 */       throw new IOException("Stream closed");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/* 101 */     synchronized (this.lock) {
/* 102 */       ensureOpen();
/* 103 */       if (this.pos >= this.count) {
/* 104 */         return -1;
/*     */       }
/* 106 */       return this.buf[this.pos++];
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
/*     */   public int read(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException {
/* 121 */     synchronized (this.lock) {
/* 122 */       ensureOpen();
/* 123 */       if (paramInt1 < 0 || paramInt1 > paramArrayOfchar.length || paramInt2 < 0 || paramInt1 + paramInt2 > paramArrayOfchar.length || paramInt1 + paramInt2 < 0)
/*     */       {
/* 125 */         throw new IndexOutOfBoundsException(); } 
/* 126 */       if (paramInt2 == 0) {
/* 127 */         return 0;
/*     */       }
/*     */       
/* 130 */       if (this.pos >= this.count) {
/* 131 */         return -1;
/*     */       }
/*     */       
/* 134 */       int i = this.count - this.pos;
/* 135 */       if (paramInt2 > i) {
/* 136 */         paramInt2 = i;
/*     */       }
/* 138 */       if (paramInt2 <= 0) {
/* 139 */         return 0;
/*     */       }
/* 141 */       System.arraycopy(this.buf, this.pos, paramArrayOfchar, paramInt1, paramInt2);
/* 142 */       this.pos += paramInt2;
/* 143 */       return paramInt2;
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
/*     */   public long skip(long paramLong) throws IOException {
/* 160 */     synchronized (this.lock) {
/* 161 */       ensureOpen();
/*     */       
/* 163 */       long l = (this.count - this.pos);
/* 164 */       if (paramLong > l) {
/* 165 */         paramLong = l;
/*     */       }
/* 167 */       if (paramLong < 0L) {
/* 168 */         return 0L;
/*     */       }
/* 170 */       this.pos = (int)(this.pos + paramLong);
/* 171 */       return paramLong;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean ready() throws IOException {
/* 182 */     synchronized (this.lock) {
/* 183 */       ensureOpen();
/* 184 */       return (this.count - this.pos > 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean markSupported() {
/* 192 */     return true;
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
/*     */   public void mark(int paramInt) throws IOException {
/* 208 */     synchronized (this.lock) {
/* 209 */       ensureOpen();
/* 210 */       this.markedPos = this.pos;
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
/* 221 */     synchronized (this.lock) {
/* 222 */       ensureOpen();
/* 223 */       this.pos = this.markedPos;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 234 */     this.buf = null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/CharArrayReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */