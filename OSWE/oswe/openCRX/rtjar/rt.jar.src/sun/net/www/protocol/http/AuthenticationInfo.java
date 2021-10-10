/*     */ package sun.net.www.protocol.http;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.net.PasswordAuthentication;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.util.HashMap;
/*     */ import sun.net.www.HeaderParser;
/*     */ import sun.security.action.GetBooleanAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AuthenticationInfo
/*     */   extends AuthCacheValue
/*     */   implements Cloneable
/*     */ {
/*     */   static final long serialVersionUID = -2588378268010453259L;
/*     */   public static final char SERVER_AUTHENTICATION = 's';
/*     */   public static final char PROXY_AUTHENTICATION = 'p';
/*  69 */   static final boolean serializeAuth = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("http.auth.serializeRequests")))
/*     */     
/*  71 */     .booleanValue();
/*     */ 
/*     */   
/*     */   protected transient PasswordAuthentication pw;
/*     */ 
/*     */ 
/*     */   
/*     */   public PasswordAuthentication credentials() {
/*  79 */     return this.pw;
/*     */   }
/*     */   
/*     */   public AuthCacheValue.Type getAuthType() {
/*  83 */     return (this.type == 's') ? AuthCacheValue.Type.Server : AuthCacheValue.Type.Proxy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   AuthScheme getAuthScheme() {
/*  89 */     return this.authScheme;
/*     */   }
/*     */   
/*     */   public String getHost() {
/*  93 */     return this.host;
/*     */   }
/*     */   public int getPort() {
/*  96 */     return this.port;
/*     */   }
/*     */   public String getRealm() {
/*  99 */     return this.realm;
/*     */   }
/*     */   public String getPath() {
/* 102 */     return this.path;
/*     */   }
/*     */   public String getProtocolScheme() {
/* 105 */     return this.protocol;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean useAuthCache() {
/* 115 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   private static HashMap<String, Thread> requests = new HashMap<>();
/*     */   char type;
/*     */   AuthScheme authScheme;
/*     */   String protocol;
/*     */   String host;
/*     */   
/*     */   private static boolean requestIsInProgress(String paramString) {
/* 132 */     if (!serializeAuth)
/*     */     {
/* 134 */       return false;
/*     */     }
/* 136 */     synchronized (requests) {
/*     */       
/* 138 */       Thread thread2 = Thread.currentThread(); Thread thread1;
/* 139 */       if ((thread1 = requests.get(paramString)) == null) {
/* 140 */         requests.put(paramString, thread2);
/* 141 */         return false;
/*     */       } 
/* 143 */       if (thread1 == thread2) {
/* 144 */         return false;
/*     */       }
/* 146 */       while (requests.containsKey(paramString)) {
/*     */         try {
/* 148 */           requests.wait();
/* 149 */         } catch (InterruptedException interruptedException) {}
/*     */       } 
/*     */     } 
/*     */     
/* 153 */     return true;
/*     */   }
/*     */   
/*     */   int port;
/*     */   String realm;
/*     */   
/*     */   private static void requestCompleted(String paramString) {
/* 160 */     synchronized (requests) {
/* 161 */       Thread thread = requests.get(paramString);
/* 162 */       if (thread != null && thread == Thread.currentThread()) {
/* 163 */         boolean bool = (requests.remove(paramString) != null) ? true : false;
/* 164 */         assert bool;
/*     */       } 
/* 166 */       requests.notifyAll();
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
/*     */   String path;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String s1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String s2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthenticationInfo(char paramChar, AuthScheme paramAuthScheme, String paramString1, int paramInt, String paramString2) {
/* 204 */     this.type = paramChar;
/* 205 */     this.authScheme = paramAuthScheme;
/* 206 */     this.protocol = "";
/* 207 */     this.host = paramString1.toLowerCase();
/* 208 */     this.port = paramInt;
/* 209 */     this.realm = paramString2;
/* 210 */     this.path = null;
/*     */   }
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 215 */       return super.clone();
/* 216 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 218 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthenticationInfo(char paramChar, AuthScheme paramAuthScheme, URL paramURL, String paramString) {
/* 227 */     this.type = paramChar;
/* 228 */     this.authScheme = paramAuthScheme;
/* 229 */     this.protocol = paramURL.getProtocol().toLowerCase();
/* 230 */     this.host = paramURL.getHost().toLowerCase();
/* 231 */     this.port = paramURL.getPort();
/* 232 */     if (this.port == -1) {
/* 233 */       this.port = paramURL.getDefaultPort();
/*     */     }
/* 235 */     this.realm = paramString;
/*     */     
/* 237 */     String str = paramURL.getPath();
/* 238 */     if (str.length() == 0) {
/* 239 */       this.path = str;
/*     */     } else {
/* 241 */       this.path = reducePath(str);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String reducePath(String paramString) {
/* 252 */     int i = paramString.lastIndexOf('/');
/* 253 */     int j = paramString.lastIndexOf('.');
/* 254 */     if (i != -1) {
/* 255 */       if (i < j) {
/* 256 */         return paramString.substring(0, i + 1);
/*     */       }
/* 258 */       return paramString;
/*     */     } 
/* 260 */     return paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static AuthenticationInfo getServerAuth(URL paramURL) {
/* 269 */     int i = paramURL.getPort();
/* 270 */     if (i == -1) {
/* 271 */       i = paramURL.getDefaultPort();
/*     */     }
/*     */     
/* 274 */     String str = "s:" + paramURL.getProtocol().toLowerCase() + ":" + paramURL.getHost().toLowerCase() + ":" + i;
/* 275 */     return getAuth(str, paramURL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getServerAuthKey(URL paramURL, String paramString, AuthScheme paramAuthScheme) {
/* 285 */     int i = paramURL.getPort();
/* 286 */     if (i == -1) {
/* 287 */       i = paramURL.getDefaultPort();
/*     */     }
/* 289 */     return "s:" + paramAuthScheme + ":" + paramURL.getProtocol().toLowerCase() + ":" + paramURL
/* 290 */       .getHost().toLowerCase() + ":" + i + ":" + paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   static AuthenticationInfo getServerAuth(String paramString) {
/* 295 */     AuthenticationInfo authenticationInfo = getAuth(paramString, null);
/* 296 */     if (authenticationInfo == null && requestIsInProgress(paramString))
/*     */     {
/* 298 */       authenticationInfo = getAuth(paramString, null);
/*     */     }
/* 300 */     return authenticationInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static AuthenticationInfo getAuth(String paramString, URL paramURL) {
/* 309 */     if (paramURL == null) {
/* 310 */       return (AuthenticationInfo)cache.get(paramString, null);
/*     */     }
/* 312 */     return (AuthenticationInfo)cache.get(paramString, paramURL.getPath());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static AuthenticationInfo getProxyAuth(String paramString, int paramInt) {
/* 322 */     String str = "p::" + paramString.toLowerCase() + ":" + paramInt;
/* 323 */     return (AuthenticationInfo)cache.get(str, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getProxyAuthKey(String paramString1, int paramInt, String paramString2, AuthScheme paramAuthScheme) {
/* 333 */     return "p:" + paramAuthScheme + "::" + paramString1.toLowerCase() + ":" + paramInt + ":" + paramString2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static AuthenticationInfo getProxyAuth(String paramString) {
/* 339 */     AuthenticationInfo authenticationInfo = (AuthenticationInfo)cache.get(paramString, null);
/* 340 */     if (authenticationInfo == null && requestIsInProgress(paramString))
/*     */     {
/* 342 */       authenticationInfo = (AuthenticationInfo)cache.get(paramString, null);
/*     */     }
/* 344 */     return authenticationInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addToCache() {
/* 352 */     String str = cacheKey(true);
/* 353 */     if (useAuthCache()) {
/* 354 */       cache.put(str, this);
/* 355 */       if (supportsPreemptiveAuthorization()) {
/* 356 */         cache.put(cacheKey(false), this);
/*     */       }
/*     */     } 
/* 359 */     endAuthRequest(str);
/*     */   }
/*     */   
/*     */   static void endAuthRequest(String paramString) {
/* 363 */     if (!serializeAuth) {
/*     */       return;
/*     */     }
/* 366 */     synchronized (requests) {
/* 367 */       requestCompleted(paramString);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void removeFromCache() {
/* 375 */     cache.remove(cacheKey(true), this);
/* 376 */     if (supportsPreemptiveAuthorization()) {
/* 377 */       cache.remove(cacheKey(false), this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean supportsPreemptiveAuthorization();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHeaderName() {
/* 391 */     if (this.type == 's') {
/* 392 */       return "Authorization";
/*     */     }
/* 394 */     return "Proxy-authorization";
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
/*     */   public abstract String getHeaderValue(URL paramURL, String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean setHeaders(HttpURLConnection paramHttpURLConnection, HeaderParser paramHeaderParser, String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean isAuthorizationStale(String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String cacheKey(boolean paramBoolean) {
/* 438 */     if (paramBoolean) {
/* 439 */       return this.type + ":" + this.authScheme + ":" + this.protocol + ":" + this.host + ":" + this.port + ":" + this.realm;
/*     */     }
/*     */     
/* 442 */     return this.type + ":" + this.protocol + ":" + this.host + ":" + this.port;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 451 */     paramObjectInputStream.defaultReadObject();
/* 452 */     this.pw = new PasswordAuthentication(this.s1, this.s2.toCharArray());
/* 453 */     this.s1 = null; this.s2 = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 459 */     this.s1 = this.pw.getUserName();
/* 460 */     this.s2 = new String(this.pw.getPassword());
/* 461 */     paramObjectOutputStream.defaultWriteObject();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/protocol/http/AuthenticationInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */