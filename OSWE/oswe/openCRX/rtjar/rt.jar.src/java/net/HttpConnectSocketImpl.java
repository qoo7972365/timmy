/*     */ package java.net;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class HttpConnectSocketImpl
/*     */   extends PlainSocketImpl
/*     */ {
/*     */   private static final String httpURLClazzStr = "sun.net.www.protocol.http.HttpURLConnection";
/*     */   private static final String netClientClazzStr = "sun.net.NetworkClient";
/*     */   private static final String doTunnelingStr = "doTunneling";
/*     */   private static final Field httpField;
/*     */   private static final Field serverSocketField;
/*     */   private static final Method doTunneling;
/*     */   private final String server;
/*     */   private InetSocketAddress external_address;
/*  56 */   private HashMap<Integer, Object> optionsMap = new HashMap<>();
/*     */   
/*     */   static {
/*     */     try {
/*  60 */       Class<?> clazz1 = Class.forName("sun.net.www.protocol.http.HttpURLConnection", true, null);
/*  61 */       httpField = clazz1.getDeclaredField("http");
/*  62 */       doTunneling = clazz1.getDeclaredMethod("doTunneling", new Class[0]);
/*  63 */       Class<?> clazz2 = Class.forName("sun.net.NetworkClient", true, null);
/*  64 */       serverSocketField = clazz2.getDeclaredField("serverSocket");
/*     */       
/*  66 */       AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */           {
/*     */             public Void run() {
/*  69 */               HttpConnectSocketImpl.httpField.setAccessible(true);
/*  70 */               HttpConnectSocketImpl.serverSocketField.setAccessible(true);
/*  71 */               return null;
/*     */             }
/*     */           });
/*  74 */     } catch (ReflectiveOperationException reflectiveOperationException) {
/*  75 */       throw new InternalError("Should not reach here", reflectiveOperationException);
/*     */     } 
/*     */   }
/*     */   
/*     */   HttpConnectSocketImpl(String paramString, int paramInt) {
/*  80 */     this.server = paramString;
/*  81 */     this.port = paramInt;
/*     */   }
/*     */   
/*     */   HttpConnectSocketImpl(Proxy paramProxy) {
/*  85 */     SocketAddress socketAddress = paramProxy.address();
/*  86 */     if (!(socketAddress instanceof InetSocketAddress)) {
/*  87 */       throw new IllegalArgumentException("Unsupported address type");
/*     */     }
/*  89 */     InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
/*  90 */     this.server = inetSocketAddress.getHostString();
/*  91 */     this.port = inetSocketAddress.getPort();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void connect(SocketAddress paramSocketAddress, int paramInt) throws IOException {
/*  98 */     if (paramSocketAddress == null || !(paramSocketAddress instanceof InetSocketAddress))
/*  99 */       throw new IllegalArgumentException("Unsupported address type"); 
/* 100 */     InetSocketAddress inetSocketAddress = (InetSocketAddress)paramSocketAddress;
/*     */     
/* 102 */     String str1 = inetSocketAddress.isUnresolved() ? inetSocketAddress.getHostName() : inetSocketAddress.getAddress().getHostAddress();
/* 103 */     int i = inetSocketAddress.getPort();
/*     */     
/* 105 */     SecurityManager securityManager = System.getSecurityManager();
/* 106 */     if (securityManager != null) {
/* 107 */       securityManager.checkConnect(str1, i);
/*     */     }
/*     */     
/* 110 */     String str2 = "http://" + str1 + ":" + i;
/* 111 */     Socket socket = privilegedDoTunnel(str2, paramInt);
/*     */ 
/*     */     
/* 114 */     this.external_address = inetSocketAddress;
/*     */ 
/*     */     
/* 117 */     close();
/*     */ 
/*     */     
/* 120 */     AbstractPlainSocketImpl abstractPlainSocketImpl = (AbstractPlainSocketImpl)socket.impl;
/* 121 */     (getSocket()).impl = abstractPlainSocketImpl;
/*     */ 
/*     */     
/* 124 */     Set<Map.Entry<Integer, Object>> set = this.optionsMap.entrySet();
/*     */     try {
/* 126 */       for (Map.Entry<Integer, Object> entry : set) {
/* 127 */         abstractPlainSocketImpl.setOption(((Integer)entry.getKey()).intValue(), entry.getValue());
/*     */       }
/* 129 */     } catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOption(int paramInt, Object paramObject) throws SocketException {
/* 134 */     super.setOption(paramInt, paramObject);
/*     */     
/* 136 */     if (this.external_address != null) {
/*     */       return;
/*     */     }
/*     */     
/* 140 */     this.optionsMap.put(Integer.valueOf(paramInt), paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Socket privilegedDoTunnel(final String urlString, final int timeout) throws IOException {
/*     */     try {
/* 148 */       return AccessController.<Socket>doPrivileged(new PrivilegedExceptionAction<Socket>()
/*     */           {
/*     */             public Socket run() throws IOException {
/* 151 */               return HttpConnectSocketImpl.this.doTunnel(urlString, timeout);
/*     */             }
/*     */           });
/* 154 */     } catch (PrivilegedActionException privilegedActionException) {
/* 155 */       throw (IOException)privilegedActionException.getException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Socket doTunnel(String paramString, int paramInt) throws IOException {
/* 162 */     Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(this.server, this.port));
/* 163 */     URL uRL = new URL(paramString);
/* 164 */     HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection(proxy);
/* 165 */     httpURLConnection.setConnectTimeout(paramInt);
/* 166 */     httpURLConnection.setReadTimeout(this.timeout);
/* 167 */     httpURLConnection.connect();
/* 168 */     doTunneling(httpURLConnection);
/*     */     try {
/* 170 */       Object object = httpField.get(httpURLConnection);
/* 171 */       return (Socket)serverSocketField.get(object);
/* 172 */     } catch (IllegalAccessException illegalAccessException) {
/* 173 */       throw new InternalError("Should not reach here", illegalAccessException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void doTunneling(HttpURLConnection paramHttpURLConnection) {
/*     */     try {
/* 179 */       doTunneling.invoke(paramHttpURLConnection, new Object[0]);
/* 180 */     } catch (ReflectiveOperationException reflectiveOperationException) {
/* 181 */       throw new InternalError("Should not reach here", reflectiveOperationException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected InetAddress getInetAddress() {
/* 187 */     if (this.external_address != null) {
/* 188 */       return this.external_address.getAddress();
/*     */     }
/* 190 */     return super.getInetAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getPort() {
/* 195 */     if (this.external_address != null) {
/* 196 */       return this.external_address.getPort();
/*     */     }
/* 198 */     return super.getPort();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getLocalPort() {
/* 203 */     if (this.socket != null)
/* 204 */       return super.getLocalPort(); 
/* 205 */     if (this.external_address != null) {
/* 206 */       return this.external_address.getPort();
/*     */     }
/* 208 */     return super.getLocalPort();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/HttpConnectSocketImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */