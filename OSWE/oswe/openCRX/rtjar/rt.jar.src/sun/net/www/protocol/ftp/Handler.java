/*    */ package sun.net.www.protocol.ftp;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.Proxy;
/*    */ import java.net.URL;
/*    */ import java.net.URLConnection;
/*    */ import java.net.URLStreamHandler;
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
/*    */ public class Handler
/*    */   extends URLStreamHandler
/*    */ {
/*    */   protected int getDefaultPort() {
/* 44 */     return 21;
/*    */   }
/*    */   
/*    */   protected boolean equals(URL paramURL1, URL paramURL2) {
/* 48 */     String str1 = paramURL1.getUserInfo();
/* 49 */     String str2 = paramURL2.getUserInfo();
/* 50 */     return (super.equals(paramURL1, paramURL2) && ((str1 == null) ? (str2 == null) : str1
/* 51 */       .equals(str2)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected URLConnection openConnection(URL paramURL) throws IOException {
/* 56 */     return openConnection(paramURL, null);
/*    */   }
/*    */ 
/*    */   
/*    */   protected URLConnection openConnection(URL paramURL, Proxy paramProxy) throws IOException {
/* 61 */     return new FtpURLConnection(paramURL, paramProxy);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/protocol/ftp/Handler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */