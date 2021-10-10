/*    */ package sun.net.www.protocol.https;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.Proxy;
/*    */ import java.net.URL;
/*    */ import javax.net.ssl.HostnameVerifier;
/*    */ import javax.net.ssl.HttpsURLConnection;
/*    */ import javax.net.ssl.SSLSocketFactory;
/*    */ import sun.net.www.protocol.http.Handler;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DelegateHttpsURLConnection
/*    */   extends AbstractDelegateHttpsURLConnection
/*    */ {
/*    */   public HttpsURLConnection httpsURLConnection;
/*    */   
/*    */   DelegateHttpsURLConnection(URL paramURL, Handler paramHandler, HttpsURLConnection paramHttpsURLConnection) throws IOException {
/* 57 */     this(paramURL, null, paramHandler, paramHttpsURLConnection);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   DelegateHttpsURLConnection(URL paramURL, Proxy paramProxy, Handler paramHandler, HttpsURLConnection paramHttpsURLConnection) throws IOException {
/* 64 */     super(paramURL, paramProxy, paramHandler);
/* 65 */     this.httpsURLConnection = paramHttpsURLConnection;
/*    */   }
/*    */   
/*    */   protected SSLSocketFactory getSSLSocketFactory() {
/* 69 */     return this.httpsURLConnection.getSSLSocketFactory();
/*    */   }
/*    */   
/*    */   protected HostnameVerifier getHostnameVerifier() {
/* 73 */     return this.httpsURLConnection.getHostnameVerifier();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void dispose() throws Throwable {
/* 81 */     finalize();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/protocol/https/DelegateHttpsURLConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */