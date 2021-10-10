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
/*     */ public class StringWriter
/*     */   extends Writer
/*     */ {
/*     */   private StringBuffer buf;
/*     */   
/*     */   public StringWriter() {
/*  50 */     this.buf = new StringBuffer();
/*  51 */     this.lock = this.buf;
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
/*     */   public StringWriter(int paramInt) {
/*  66 */     if (paramInt < 0) {
/*  67 */       throw new IllegalArgumentException("Negative buffer size");
/*     */     }
/*  69 */     this.buf = new StringBuffer(paramInt);
/*  70 */     this.lock = this.buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(int paramInt) {
/*  77 */     this.buf.append((char)paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/*  88 */     if (paramInt1 < 0 || paramInt1 > paramArrayOfchar.length || paramInt2 < 0 || paramInt1 + paramInt2 > paramArrayOfchar.length || paramInt1 + paramInt2 < 0)
/*     */     {
/*  90 */       throw new IndexOutOfBoundsException(); } 
/*  91 */     if (paramInt2 == 0) {
/*     */       return;
/*     */     }
/*  94 */     this.buf.append(paramArrayOfchar, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(String paramString) {
/* 101 */     this.buf.append(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(String paramString, int paramInt1, int paramInt2) {
/* 112 */     this.buf.append(paramString.substring(paramInt1, paramInt1 + paramInt2));
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
/*     */   public StringWriter append(CharSequence paramCharSequence) {
/* 140 */     if (paramCharSequence == null) {
/* 141 */       write("null");
/*     */     } else {
/* 143 */       write(paramCharSequence.toString());
/* 144 */     }  return this;
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
/*     */   public StringWriter append(CharSequence paramCharSequence, int paramInt1, int paramInt2) {
/* 180 */     CharSequence charSequence = (paramCharSequence == null) ? "null" : paramCharSequence;
/* 181 */     write(charSequence.subSequence(paramInt1, paramInt2).toString());
/* 182 */     return this;
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
/*     */   public StringWriter append(char paramChar) {
/* 202 */     write(paramChar);
/* 203 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 210 */     return this.buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuffer getBuffer() {
/* 219 */     return this.buf;
/*     */   }
/*     */   
/*     */   public void flush() {}
/*     */   
/*     */   public void close() throws IOException {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/StringWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */