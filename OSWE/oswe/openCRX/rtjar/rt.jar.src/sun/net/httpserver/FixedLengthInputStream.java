/*    */ package sun.net.httpserver;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
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
/*    */ class FixedLengthInputStream
/*    */   extends LeftOverInputStream
/*    */ {
/*    */   private long remaining;
/*    */   
/*    */   FixedLengthInputStream(ExchangeImpl paramExchangeImpl, InputStream paramInputStream, long paramLong) {
/* 43 */     super(paramExchangeImpl, paramInputStream);
/* 44 */     this.remaining = paramLong;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int readImpl(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 49 */     this.eof = (this.remaining == 0L);
/* 50 */     if (this.eof) {
/* 51 */       return -1;
/*    */     }
/* 53 */     if (paramInt2 > this.remaining) {
/* 54 */       paramInt2 = (int)this.remaining;
/*    */     }
/* 56 */     int i = this.in.read(paramArrayOfbyte, paramInt1, paramInt2);
/* 57 */     if (i > -1) {
/* 58 */       this.remaining -= i;
/* 59 */       if (this.remaining == 0L) {
/* 60 */         this.t.getServerImpl().requestCompleted(this.t.getConnection());
/*    */       }
/*    */     } 
/* 63 */     return i;
/*    */   }
/*    */   
/*    */   public int available() throws IOException {
/* 67 */     if (this.eof) {
/* 68 */       return 0;
/*    */     }
/* 70 */     int i = this.in.available();
/* 71 */     return (i < this.remaining) ? i : (int)this.remaining;
/*    */   }
/*    */   public boolean markSupported() {
/* 74 */     return false;
/*    */   }
/*    */   
/*    */   public void mark(int paramInt) {}
/*    */   
/*    */   public void reset() throws IOException {
/* 80 */     throw new IOException("mark/reset not supported");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/httpserver/FixedLengthInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */