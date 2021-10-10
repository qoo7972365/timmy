/*     */ package javax.rmi.ssl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.net.Socket;
/*     */ import java.rmi.server.RMIClientSocketFactory;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.net.SocketFactory;
/*     */ import javax.net.ssl.SSLSocket;
/*     */ import javax.net.ssl.SSLSocketFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SslRMIClientSocketFactory
/*     */   implements RMIClientSocketFactory, Serializable
/*     */ {
/*     */   public Socket createSocket(String paramString, int paramInt) throws IOException {
/* 117 */     SocketFactory socketFactory = getDefaultClientSocketFactory();
/*     */ 
/*     */ 
/*     */     
/* 121 */     SSLSocket sSLSocket = (SSLSocket)socketFactory.createSocket(paramString, paramInt);
/*     */ 
/*     */ 
/*     */     
/* 125 */     String str1 = System.getProperty("javax.rmi.ssl.client.enabledCipherSuites");
/* 126 */     if (str1 != null) {
/* 127 */       StringTokenizer stringTokenizer = new StringTokenizer(str1, ",");
/* 128 */       int i = stringTokenizer.countTokens();
/* 129 */       String[] arrayOfString = new String[i];
/* 130 */       for (byte b = 0; b < i; b++) {
/* 131 */         arrayOfString[b] = stringTokenizer.nextToken();
/*     */       }
/*     */       try {
/* 134 */         sSLSocket.setEnabledCipherSuites(arrayOfString);
/* 135 */       } catch (IllegalArgumentException illegalArgumentException) {
/* 136 */         throw (IOException)(new IOException(illegalArgumentException
/* 137 */             .getMessage())).initCause(illegalArgumentException);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 143 */     String str2 = System.getProperty("javax.rmi.ssl.client.enabledProtocols");
/* 144 */     if (str2 != null) {
/* 145 */       StringTokenizer stringTokenizer = new StringTokenizer(str2, ",");
/* 146 */       int i = stringTokenizer.countTokens();
/* 147 */       String[] arrayOfString = new String[i];
/* 148 */       for (byte b = 0; b < i; b++) {
/* 149 */         arrayOfString[b] = stringTokenizer.nextToken();
/*     */       }
/*     */       try {
/* 152 */         sSLSocket.setEnabledProtocols(arrayOfString);
/* 153 */       } catch (IllegalArgumentException illegalArgumentException) {
/* 154 */         throw (IOException)(new IOException(illegalArgumentException
/* 155 */             .getMessage())).initCause(illegalArgumentException);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 160 */     return sSLSocket;
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
/*     */   public boolean equals(Object paramObject) {
/* 176 */     if (paramObject == null) return false; 
/* 177 */     if (paramObject == this) return true; 
/* 178 */     return getClass().equals(paramObject.getClass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 189 */     return getClass().hashCode();
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
/* 203 */   private static SocketFactory defaultSocketFactory = null; private static final long serialVersionUID = -8310631444933958385L;
/*     */   
/*     */   private static synchronized SocketFactory getDefaultClientSocketFactory() {
/* 206 */     if (defaultSocketFactory == null)
/* 207 */       defaultSocketFactory = SSLSocketFactory.getDefault(); 
/* 208 */     return defaultSocketFactory;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/rmi/ssl/SslRMIClientSocketFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */