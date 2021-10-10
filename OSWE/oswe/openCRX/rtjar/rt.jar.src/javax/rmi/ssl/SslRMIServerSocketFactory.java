/*     */ package javax.rmi.ssl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.rmi.server.RMIServerSocketFactory;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import javax.net.ssl.SSLContext;
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
/*     */ public class SslRMIServerSocketFactory
/*     */   implements RMIServerSocketFactory
/*     */ {
/*     */   public SslRMIServerSocketFactory() {
/*  80 */     this(null, null, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SslRMIServerSocketFactory(String[] paramArrayOfString1, String[] paramArrayOfString2, boolean paramBoolean) throws IllegalArgumentException {
/* 118 */     this(null, paramArrayOfString1, paramArrayOfString2, paramBoolean);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SslRMIServerSocketFactory(SSLContext paramSSLContext, String[] paramArrayOfString1, String[] paramArrayOfString2, boolean paramBoolean) throws IllegalArgumentException {
/* 167 */     this
/* 168 */       .enabledCipherSuites = (paramArrayOfString1 == null) ? null : (String[])paramArrayOfString1.clone();
/* 169 */     this
/* 170 */       .enabledProtocols = (paramArrayOfString2 == null) ? null : (String[])paramArrayOfString2.clone();
/* 171 */     this.needClientAuth = paramBoolean;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 177 */     this.context = paramSSLContext;
/*     */ 
/*     */     
/* 180 */     SSLSocketFactory sSLSocketFactory = (paramSSLContext == null) ? getDefaultSSLSocketFactory() : paramSSLContext.getSocketFactory();
/* 181 */     SSLSocket sSLSocket = null;
/* 182 */     if (this.enabledCipherSuites != null || this.enabledProtocols != null) {
/*     */       try {
/* 184 */         sSLSocket = (SSLSocket)sSLSocketFactory.createSocket();
/* 185 */       } catch (Exception exception) {
/*     */ 
/*     */         
/* 188 */         throw (IllegalArgumentException)(new IllegalArgumentException("Unable to check if the cipher suites and protocols to enable are supported"))
/* 189 */           .initCause(exception);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 197 */     if (this.enabledCipherSuites != null) {
/* 198 */       sSLSocket.setEnabledCipherSuites(this.enabledCipherSuites);
/* 199 */       this.enabledCipherSuitesList = Arrays.asList(this.enabledCipherSuites);
/*     */     } 
/* 201 */     if (this.enabledProtocols != null) {
/* 202 */       sSLSocket.setEnabledProtocols(this.enabledProtocols);
/* 203 */       this.enabledProtocolsList = Arrays.asList(this.enabledProtocols);
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
/*     */   public final String[] getEnabledCipherSuites() {
/* 218 */     return (this.enabledCipherSuites == null) ? null : (String[])this.enabledCipherSuites
/* 219 */       .clone();
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
/*     */   public final String[] getEnabledProtocols() {
/* 234 */     return (this.enabledProtocols == null) ? null : (String[])this.enabledProtocols
/* 235 */       .clone();
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
/*     */   public final boolean getNeedClientAuth() {
/* 248 */     return this.needClientAuth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerSocket createServerSocket(int paramInt) throws IOException {
/* 259 */     final SSLSocketFactory sslSocketFactory = (this.context == null) ? getDefaultSSLSocketFactory() : this.context.getSocketFactory();
/* 260 */     return new ServerSocket(paramInt) {
/*     */         public Socket accept() throws IOException {
/* 262 */           Socket socket = super.accept();
/* 263 */           SSLSocket sSLSocket = (SSLSocket)sslSocketFactory.createSocket(socket, socket
/* 264 */               .getInetAddress().getHostName(), socket
/* 265 */               .getPort(), true);
/* 266 */           sSLSocket.setUseClientMode(false);
/* 267 */           if (SslRMIServerSocketFactory.this.enabledCipherSuites != null) {
/* 268 */             sSLSocket.setEnabledCipherSuites(SslRMIServerSocketFactory.this.enabledCipherSuites);
/*     */           }
/* 270 */           if (SslRMIServerSocketFactory.this.enabledProtocols != null) {
/* 271 */             sSLSocket.setEnabledProtocols(SslRMIServerSocketFactory.this.enabledProtocols);
/*     */           }
/* 273 */           sSLSocket.setNeedClientAuth(SslRMIServerSocketFactory.this.needClientAuth);
/* 274 */           return sSLSocket;
/*     */         }
/*     */       };
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
/*     */   public boolean equals(Object paramObject) {
/* 291 */     if (paramObject == null) return false; 
/* 292 */     if (paramObject == this) return true; 
/* 293 */     if (!(paramObject instanceof SslRMIServerSocketFactory))
/* 294 */       return false; 
/* 295 */     SslRMIServerSocketFactory sslRMIServerSocketFactory = (SslRMIServerSocketFactory)paramObject;
/* 296 */     return (getClass().equals(sslRMIServerSocketFactory.getClass()) && checkParameters(sslRMIServerSocketFactory));
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
/*     */ 
/*     */   
/*     */   private boolean checkParameters(SslRMIServerSocketFactory paramSslRMIServerSocketFactory) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield context : Ljavax/net/ssl/SSLContext;
/*     */     //   4: ifnonnull -> 17
/*     */     //   7: aload_1
/*     */     //   8: getfield context : Ljavax/net/ssl/SSLContext;
/*     */     //   11: ifnull -> 33
/*     */     //   14: goto -> 31
/*     */     //   17: aload_0
/*     */     //   18: getfield context : Ljavax/net/ssl/SSLContext;
/*     */     //   21: aload_1
/*     */     //   22: getfield context : Ljavax/net/ssl/SSLContext;
/*     */     //   25: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   28: ifne -> 33
/*     */     //   31: iconst_0
/*     */     //   32: ireturn
/*     */     //   33: aload_0
/*     */     //   34: getfield needClientAuth : Z
/*     */     //   37: aload_1
/*     */     //   38: getfield needClientAuth : Z
/*     */     //   41: if_icmpeq -> 46
/*     */     //   44: iconst_0
/*     */     //   45: ireturn
/*     */     //   46: aload_0
/*     */     //   47: getfield enabledCipherSuites : [Ljava/lang/String;
/*     */     //   50: ifnonnull -> 60
/*     */     //   53: aload_1
/*     */     //   54: getfield enabledCipherSuites : [Ljava/lang/String;
/*     */     //   57: ifnonnull -> 74
/*     */     //   60: aload_0
/*     */     //   61: getfield enabledCipherSuites : [Ljava/lang/String;
/*     */     //   64: ifnull -> 76
/*     */     //   67: aload_1
/*     */     //   68: getfield enabledCipherSuites : [Ljava/lang/String;
/*     */     //   71: ifnonnull -> 76
/*     */     //   74: iconst_0
/*     */     //   75: ireturn
/*     */     //   76: aload_0
/*     */     //   77: getfield enabledCipherSuites : [Ljava/lang/String;
/*     */     //   80: ifnull -> 113
/*     */     //   83: aload_1
/*     */     //   84: getfield enabledCipherSuites : [Ljava/lang/String;
/*     */     //   87: ifnull -> 113
/*     */     //   90: aload_1
/*     */     //   91: getfield enabledCipherSuites : [Ljava/lang/String;
/*     */     //   94: invokestatic asList : ([Ljava/lang/Object;)Ljava/util/List;
/*     */     //   97: astore_2
/*     */     //   98: aload_0
/*     */     //   99: getfield enabledCipherSuitesList : Ljava/util/List;
/*     */     //   102: aload_2
/*     */     //   103: invokeinterface equals : (Ljava/lang/Object;)Z
/*     */     //   108: ifne -> 113
/*     */     //   111: iconst_0
/*     */     //   112: ireturn
/*     */     //   113: aload_0
/*     */     //   114: getfield enabledProtocols : [Ljava/lang/String;
/*     */     //   117: ifnonnull -> 127
/*     */     //   120: aload_1
/*     */     //   121: getfield enabledProtocols : [Ljava/lang/String;
/*     */     //   124: ifnonnull -> 141
/*     */     //   127: aload_0
/*     */     //   128: getfield enabledProtocols : [Ljava/lang/String;
/*     */     //   131: ifnull -> 143
/*     */     //   134: aload_1
/*     */     //   135: getfield enabledProtocols : [Ljava/lang/String;
/*     */     //   138: ifnonnull -> 143
/*     */     //   141: iconst_0
/*     */     //   142: ireturn
/*     */     //   143: aload_0
/*     */     //   144: getfield enabledProtocols : [Ljava/lang/String;
/*     */     //   147: ifnull -> 180
/*     */     //   150: aload_1
/*     */     //   151: getfield enabledProtocols : [Ljava/lang/String;
/*     */     //   154: ifnull -> 180
/*     */     //   157: aload_1
/*     */     //   158: getfield enabledProtocols : [Ljava/lang/String;
/*     */     //   161: invokestatic asList : ([Ljava/lang/Object;)Ljava/util/List;
/*     */     //   164: astore_2
/*     */     //   165: aload_0
/*     */     //   166: getfield enabledProtocolsList : Ljava/util/List;
/*     */     //   169: aload_2
/*     */     //   170: invokeinterface equals : (Ljava/lang/Object;)Z
/*     */     //   175: ifne -> 180
/*     */     //   178: iconst_0
/*     */     //   179: ireturn
/*     */     //   180: iconst_1
/*     */     //   181: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #302	-> 0
/*     */     //   #303	-> 31
/*     */     //   #307	-> 33
/*     */     //   #308	-> 44
/*     */     //   #312	-> 46
/*     */     //   #314	-> 74
/*     */     //   #315	-> 76
/*     */     //   #316	-> 90
/*     */     //   #317	-> 94
/*     */     //   #318	-> 98
/*     */     //   #319	-> 111
/*     */     //   #324	-> 113
/*     */     //   #326	-> 141
/*     */     //   #327	-> 143
/*     */     //   #328	-> 157
/*     */     //   #329	-> 161
/*     */     //   #330	-> 165
/*     */     //   #331	-> 178
/*     */     //   #334	-> 180
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
/*     */   
/*     */   public int hashCode() {
/* 345 */     return getClass().hashCode() + ((this.context == null) ? 0 : this.context
/* 346 */       .hashCode()) + (this.needClientAuth ? Boolean.TRUE
/* 347 */       .hashCode() : Boolean.FALSE.hashCode()) + ((this.enabledCipherSuites == null) ? 0 : this.enabledCipherSuitesList
/* 348 */       .hashCode()) + ((this.enabledProtocols == null) ? 0 : this.enabledProtocolsList
/* 349 */       .hashCode());
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
/* 363 */   private static SSLSocketFactory defaultSSLSocketFactory = null; private final String[] enabledCipherSuites;
/*     */   
/*     */   private static synchronized SSLSocketFactory getDefaultSSLSocketFactory() {
/* 366 */     if (defaultSSLSocketFactory == null)
/*     */     {
/* 368 */       defaultSSLSocketFactory = (SSLSocketFactory)SSLSocketFactory.getDefault(); } 
/* 369 */     return defaultSSLSocketFactory;
/*     */   }
/*     */   
/*     */   private final String[] enabledProtocols;
/*     */   private final boolean needClientAuth;
/*     */   private List<String> enabledCipherSuitesList;
/*     */   private List<String> enabledProtocolsList;
/*     */   private SSLContext context;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/rmi/ssl/SslRMIServerSocketFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */