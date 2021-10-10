/*     */ package sun.net.httpserver;
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
/*     */ class FixedLengthOutputStream
/*     */   extends FilterOutputStream
/*     */ {
/*     */   private long remaining;
/*     */   private boolean eof = false;
/*     */   private boolean closed = false;
/*     */   ExchangeImpl t;
/*     */   
/*     */   FixedLengthOutputStream(ExchangeImpl paramExchangeImpl, OutputStream paramOutputStream, long paramLong) {
/*  49 */     super(paramOutputStream);
/*  50 */     this.t = paramExchangeImpl;
/*  51 */     this.remaining = paramLong;
/*     */   }
/*     */   
/*     */   public void write(int paramInt) throws IOException {
/*  55 */     if (this.closed) {
/*  56 */       throw new IOException("stream closed");
/*     */     }
/*  58 */     this.eof = (this.remaining == 0L);
/*  59 */     if (this.eof) {
/*  60 */       throw new StreamClosedException();
/*     */     }
/*  62 */     this.out.write(paramInt);
/*  63 */     this.remaining--;
/*     */   }
/*     */   
/*     */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/*  67 */     if (this.closed) {
/*  68 */       throw new IOException("stream closed");
/*     */     }
/*  70 */     this.eof = (this.remaining == 0L);
/*  71 */     if (this.eof) {
/*  72 */       throw new StreamClosedException();
/*     */     }
/*  74 */     if (paramInt2 > this.remaining)
/*     */     {
/*  76 */       throw new IOException("too many bytes to write to stream");
/*     */     }
/*  78 */     this.out.write(paramArrayOfbyte, paramInt1, paramInt2);
/*  79 */     this.remaining -= paramInt2;
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/*  83 */     if (this.closed) {
/*     */       return;
/*     */     }
/*  86 */     this.closed = true;
/*  87 */     if (this.remaining > 0L) {
/*  88 */       this.t.close();
/*  89 */       throw new IOException("insufficient bytes written to stream");
/*     */     } 
/*  91 */     flush();
/*  92 */     this.eof = true;
/*  93 */     LeftOverInputStream leftOverInputStream = this.t.getOriginalInputStream();
/*  94 */     if (!leftOverInputStream.isClosed()) {
/*     */       try {
/*  96 */         leftOverInputStream.close();
/*  97 */       } catch (IOException iOException) {}
/*     */     }
/*  99 */     WriteFinishedEvent writeFinishedEvent = new WriteFinishedEvent(this.t);
/* 100 */     this.t.getHttpContext().getServerImpl().addEvent(writeFinishedEvent);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/httpserver/FixedLengthOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */