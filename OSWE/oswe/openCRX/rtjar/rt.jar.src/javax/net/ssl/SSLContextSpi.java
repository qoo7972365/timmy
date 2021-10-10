/*     */ package javax.net.ssl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.KeyManagementException;
/*     */ import java.security.SecureRandom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SSLContextSpi
/*     */ {
/*     */   protected abstract void engineInit(KeyManager[] paramArrayOfKeyManager, TrustManager[] paramArrayOfTrustManager, SecureRandom paramSecureRandom) throws KeyManagementException;
/*     */   
/*     */   protected abstract SSLSocketFactory engineGetSocketFactory();
/*     */   
/*     */   protected abstract SSLServerSocketFactory engineGetServerSocketFactory();
/*     */   
/*     */   protected abstract SSLEngine engineCreateSSLEngine();
/*     */   
/*     */   protected abstract SSLEngine engineCreateSSLEngine(String paramString, int paramInt);
/*     */   
/*     */   protected abstract SSLSessionContext engineGetServerSessionContext();
/*     */   
/*     */   protected abstract SSLSessionContext engineGetClientSessionContext();
/*     */   
/*     */   private SSLSocket getDefaultSocket() {
/*     */     try {
/* 142 */       SSLSocketFactory sSLSocketFactory = engineGetSocketFactory();
/* 143 */       return (SSLSocket)sSLSocketFactory.createSocket();
/* 144 */     } catch (IOException iOException) {
/* 145 */       throw new UnsupportedOperationException("Could not obtain parameters", iOException);
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected SSLParameters engineGetDefaultSSLParameters() {
/* 168 */     SSLSocket sSLSocket = getDefaultSocket();
/* 169 */     return sSLSocket.getSSLParameters();
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
/*     */   protected SSLParameters engineGetSupportedSSLParameters() {
/* 192 */     SSLSocket sSLSocket = getDefaultSocket();
/* 193 */     SSLParameters sSLParameters = new SSLParameters();
/* 194 */     sSLParameters.setCipherSuites(sSLSocket.getSupportedCipherSuites());
/* 195 */     sSLParameters.setProtocols(sSLSocket.getSupportedProtocols());
/* 196 */     return sSLParameters;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/net/ssl/SSLContextSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */