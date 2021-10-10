/*    */ package sun.net.httpserver;
/*    */ 
/*    */ import java.io.FilterOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class UndefLengthOutputStream
/*    */   extends FilterOutputStream
/*    */ {
/*    */   private boolean closed = false;
/*    */   ExchangeImpl t;
/*    */   
/*    */   UndefLengthOutputStream(ExchangeImpl paramExchangeImpl, OutputStream paramOutputStream) {
/* 46 */     super(paramOutputStream);
/* 47 */     this.t = paramExchangeImpl;
/*    */   }
/*    */   
/*    */   public void write(int paramInt) throws IOException {
/* 51 */     if (this.closed) {
/* 52 */       throw new IOException("stream closed");
/*    */     }
/* 54 */     this.out.write(paramInt);
/*    */   }
/*    */   
/*    */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 58 */     if (this.closed) {
/* 59 */       throw new IOException("stream closed");
/*    */     }
/* 61 */     this.out.write(paramArrayOfbyte, paramInt1, paramInt2);
/*    */   }
/*    */   
/*    */   public void close() throws IOException {
/* 65 */     if (this.closed) {
/*    */       return;
/*    */     }
/* 68 */     this.closed = true;
/* 69 */     flush();
/* 70 */     LeftOverInputStream leftOverInputStream = this.t.getOriginalInputStream();
/* 71 */     if (!leftOverInputStream.isClosed()) {
/*    */       try {
/* 73 */         leftOverInputStream.close();
/* 74 */       } catch (IOException iOException) {}
/*    */     }
/* 76 */     WriteFinishedEvent writeFinishedEvent = new WriteFinishedEvent(this.t);
/* 77 */     this.t.getHttpContext().getServerImpl().addEvent(writeFinishedEvent);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/httpserver/UndefLengthOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */