/*    */ package sun.net.www.http;
/*    */ 
/*    */ import java.io.FilterInputStream;
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
/*    */ public class HttpCaptureInputStream
/*    */   extends FilterInputStream
/*    */ {
/* 36 */   private HttpCapture capture = null;
/*    */   
/*    */   public HttpCaptureInputStream(InputStream paramInputStream, HttpCapture paramHttpCapture) {
/* 39 */     super(paramInputStream);
/* 40 */     this.capture = paramHttpCapture;
/*    */   }
/*    */ 
/*    */   
/*    */   public int read() throws IOException {
/* 45 */     int i = super.read();
/* 46 */     this.capture.received(i);
/* 47 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() throws IOException {
/*    */     try {
/* 53 */       this.capture.flush();
/* 54 */     } catch (IOException iOException) {}
/*    */     
/* 56 */     super.close();
/*    */   }
/*    */ 
/*    */   
/*    */   public int read(byte[] paramArrayOfbyte) throws IOException {
/* 61 */     int i = super.read(paramArrayOfbyte);
/* 62 */     for (byte b = 0; b < i; b++) {
/* 63 */       this.capture.received(paramArrayOfbyte[b]);
/*    */     }
/* 65 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 70 */     int i = super.read(paramArrayOfbyte, paramInt1, paramInt2);
/* 71 */     for (byte b = 0; b < i; b++) {
/* 72 */       this.capture.received(paramArrayOfbyte[paramInt1 + b]);
/*    */     }
/* 74 */     return i;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/http/HttpCaptureInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */