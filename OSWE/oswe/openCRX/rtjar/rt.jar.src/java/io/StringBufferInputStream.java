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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class StringBufferInputStream
/*     */   extends InputStream
/*     */ {
/*     */   protected String buffer;
/*     */   protected int pos;
/*     */   protected int count;
/*     */   
/*     */   public StringBufferInputStream(String paramString) {
/*  73 */     this.buffer = paramString;
/*  74 */     this.count = paramString.length();
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
/*     */   public synchronized int read() {
/*  92 */     return (this.pos < this.count) ? (this.buffer.charAt(this.pos++) & 0xFF) : -1;
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
/*     */   public synchronized int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 112 */     if (paramArrayOfbyte == null)
/* 113 */       throw new NullPointerException(); 
/* 114 */     if (paramInt1 < 0 || paramInt1 > paramArrayOfbyte.length || paramInt2 < 0 || paramInt1 + paramInt2 > paramArrayOfbyte.length || paramInt1 + paramInt2 < 0)
/*     */     {
/* 116 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 118 */     if (this.pos >= this.count) {
/* 119 */       return -1;
/*     */     }
/*     */     
/* 122 */     int i = this.count - this.pos;
/* 123 */     if (paramInt2 > i) {
/* 124 */       paramInt2 = i;
/*     */     }
/* 126 */     if (paramInt2 <= 0) {
/* 127 */       return 0;
/*     */     }
/* 129 */     String str = this.buffer;
/* 130 */     int j = paramInt2;
/* 131 */     while (--j >= 0) {
/* 132 */       paramArrayOfbyte[paramInt1++] = (byte)str.charAt(this.pos++);
/*     */     }
/*     */     
/* 135 */     return paramInt2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized long skip(long paramLong) {
/* 146 */     if (paramLong < 0L) {
/* 147 */       return 0L;
/*     */     }
/* 149 */     if (paramLong > (this.count - this.pos)) {
/* 150 */       paramLong = (this.count - this.pos);
/*     */     }
/* 152 */     this.pos = (int)(this.pos + paramLong);
/* 153 */     return paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int available() {
/* 164 */     return this.count - this.pos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void reset() {
/* 172 */     this.pos = 0;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/StringBufferInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */