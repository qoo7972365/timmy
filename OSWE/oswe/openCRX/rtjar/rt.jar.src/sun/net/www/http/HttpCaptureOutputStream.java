/*    */ package sun.net.www.http;
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
/*    */ public class HttpCaptureOutputStream
/*    */   extends FilterOutputStream
/*    */ {
/* 36 */   private HttpCapture capture = null;
/*    */   
/*    */   public HttpCaptureOutputStream(OutputStream paramOutputStream, HttpCapture paramHttpCapture) {
/* 39 */     super(paramOutputStream);
/* 40 */     this.capture = paramHttpCapture;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(int paramInt) throws IOException {
/* 45 */     this.capture.sent(paramInt);
/* 46 */     this.out.write(paramInt);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] paramArrayOfbyte) throws IOException {
/* 51 */     for (byte b : paramArrayOfbyte) {
/* 52 */       this.capture.sent(b);
/*    */     }
/* 54 */     this.out.write(paramArrayOfbyte);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 59 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 60 */       this.capture.sent(paramArrayOfbyte[i]);
/*    */     }
/* 62 */     this.out.write(paramArrayOfbyte, paramInt1, paramInt2);
/*    */   }
/*    */ 
/*    */   
/*    */   public void flush() throws IOException {
/*    */     try {
/* 68 */       this.capture.flush();
/* 69 */     } catch (IOException iOException) {}
/*    */     
/* 71 */     super.flush();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/http/HttpCaptureOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */