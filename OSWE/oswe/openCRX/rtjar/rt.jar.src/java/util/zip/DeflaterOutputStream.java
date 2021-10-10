/*     */ package java.util.zip;
/*     */ 
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeflaterOutputStream
/*     */   extends FilterOutputStream
/*     */ {
/*     */   protected Deflater def;
/*     */   protected byte[] buf;
/*     */   private boolean closed = false;
/*     */   private final boolean syncFlush;
/*     */   boolean usesDefaultDeflater;
/*     */   
/*     */   public DeflaterOutputStream(OutputStream paramOutputStream, Deflater paramDeflater, int paramInt, boolean paramBoolean) {
/*  82 */     super(paramOutputStream);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 145 */     this.usesDefaultDeflater = false;
/*     */     if (paramOutputStream == null || paramDeflater == null) {
/*     */       throw new NullPointerException();
/*     */     }
/*     */     if (paramInt <= 0) {
/*     */       throw new IllegalArgumentException("buffer size <= 0");
/*     */     }
/*     */     this.def = paramDeflater;
/*     */     this.buf = new byte[paramInt];
/*     */     this.syncFlush = paramBoolean;
/*     */   }
/*     */   
/*     */   public DeflaterOutputStream(OutputStream paramOutputStream, Deflater paramDeflater, int paramInt) {
/*     */     this(paramOutputStream, paramDeflater, paramInt, false);
/*     */   }
/*     */   
/*     */   public DeflaterOutputStream(OutputStream paramOutputStream, boolean paramBoolean) {
/* 162 */     this(paramOutputStream, new Deflater(), 512, paramBoolean);
/* 163 */     this.usesDefaultDeflater = true;
/*     */   }
/*     */   
/*     */   public DeflaterOutputStream(OutputStream paramOutputStream, Deflater paramDeflater, boolean paramBoolean) {
/*     */     this(paramOutputStream, paramDeflater, 512, paramBoolean);
/*     */   }
/*     */   
/*     */   public DeflaterOutputStream(OutputStream paramOutputStream, Deflater paramDeflater) {
/*     */     this(paramOutputStream, paramDeflater, 512, false);
/*     */   }
/*     */   
/*     */   public DeflaterOutputStream(OutputStream paramOutputStream) {
/* 175 */     this(paramOutputStream, false);
/* 176 */     this.usesDefaultDeflater = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(int paramInt) throws IOException {
/* 186 */     byte[] arrayOfByte = new byte[1];
/* 187 */     arrayOfByte[0] = (byte)(paramInt & 0xFF);
/* 188 */     write(arrayOfByte, 0, 1);
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
/*     */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 200 */     if (this.def.finished()) {
/* 201 */       throw new IOException("write beyond end of stream");
/*     */     }
/* 203 */     if ((paramInt1 | paramInt2 | paramInt1 + paramInt2 | paramArrayOfbyte.length - paramInt1 + paramInt2) < 0)
/* 204 */       throw new IndexOutOfBoundsException(); 
/* 205 */     if (paramInt2 == 0) {
/*     */       return;
/*     */     }
/* 208 */     if (!this.def.finished()) {
/* 209 */       this.def.setInput(paramArrayOfbyte, paramInt1, paramInt2);
/* 210 */       while (!this.def.needsInput()) {
/* 211 */         deflate();
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
/*     */   public void finish() throws IOException {
/* 223 */     if (!this.def.finished()) {
/* 224 */       this.def.finish();
/* 225 */       while (!this.def.finished()) {
/* 226 */         deflate();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 237 */     if (!this.closed) {
/* 238 */       finish();
/* 239 */       if (this.usesDefaultDeflater)
/* 240 */         this.def.end(); 
/* 241 */       this.out.close();
/* 242 */       this.closed = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void deflate() throws IOException {
/* 251 */     int i = this.def.deflate(this.buf, 0, this.buf.length);
/* 252 */     if (i > 0) {
/* 253 */       this.out.write(this.buf, 0, i);
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
/*     */   public void flush() throws IOException {
/* 273 */     if (this.syncFlush && !this.def.finished()) {
/* 274 */       int i = 0;
/* 275 */       while ((i = this.def.deflate(this.buf, 0, this.buf.length, 2)) > 0) {
/*     */         
/* 277 */         this.out.write(this.buf, 0, i);
/* 278 */         if (i < this.buf.length)
/*     */           break; 
/*     */       } 
/*     */     } 
/* 282 */     this.out.flush();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/zip/DeflaterOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */