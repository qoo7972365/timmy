/*     */ package sun.net.www.protocol.http;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.Authenticator;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.util.Base64;
/*     */ import java.util.HashMap;
/*     */ import sun.net.www.HeaderParser;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class NegotiateAuthentication
/*     */   extends AuthenticationInfo
/*     */ {
/*     */   private static final long serialVersionUID = 100L;
/*  49 */   private static final PlatformLogger logger = HttpURLConnection.getHttpLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final HttpCallerInfo hci;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   static HashMap<String, Boolean> supported = null;
/*  60 */   static ThreadLocal<HashMap<String, Negotiator>> cache = null;
/*     */   private static final boolean cacheSPNEGO;
/*     */   
/*     */   static {
/*  64 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("jdk.spnego.cache", "true"));
/*     */     
/*  66 */     cacheSPNEGO = Boolean.parseBoolean(str);
/*     */   }
/*     */ 
/*     */   
/*  70 */   private Negotiator negotiator = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NegotiateAuthentication(HttpCallerInfo paramHttpCallerInfo) {
/*  77 */     super((Authenticator.RequestorType.PROXY == paramHttpCallerInfo.authType) ? 112 : 115, 
/*  78 */         paramHttpCallerInfo.scheme.equalsIgnoreCase("Negotiate") ? AuthScheme.NEGOTIATE : AuthScheme.KERBEROS, paramHttpCallerInfo.url, "");
/*     */ 
/*     */     
/*  81 */     this.hci = paramHttpCallerInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean supportsPreemptiveAuthorization() {
/*  89 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSupported(HttpCallerInfo paramHttpCallerInfo) {
/*  97 */     ClassLoader classLoader = null;
/*     */     try {
/*  99 */       classLoader = Thread.currentThread().getContextClassLoader();
/* 100 */     } catch (SecurityException securityException) {
/* 101 */       if (logger.isLoggable(PlatformLogger.Level.FINER)) {
/* 102 */         logger.finer("NegotiateAuthentication: Attempt to get the context class loader failed - " + securityException);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 107 */     if (classLoader != null)
/*     */     {
/*     */       
/* 110 */       synchronized (classLoader) {
/* 111 */         return isSupportedImpl(paramHttpCallerInfo);
/*     */       } 
/*     */     }
/* 114 */     return isSupportedImpl(paramHttpCallerInfo);
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
/*     */   private static synchronized boolean isSupportedImpl(HttpCallerInfo paramHttpCallerInfo) {
/* 129 */     if (supported == null) {
/* 130 */       supported = new HashMap<>();
/*     */     }
/* 132 */     String str = paramHttpCallerInfo.host;
/* 133 */     str = str.toLowerCase();
/* 134 */     if (supported.containsKey(str)) {
/* 135 */       return ((Boolean)supported.get(str)).booleanValue();
/*     */     }
/*     */     
/* 138 */     Negotiator negotiator = Negotiator.getNegotiator(paramHttpCallerInfo);
/* 139 */     if (negotiator != null) {
/* 140 */       supported.put(str, Boolean.valueOf(true));
/*     */ 
/*     */       
/* 143 */       if (cache == null) {
/* 144 */         cache = new ThreadLocal<HashMap<String, Negotiator>>()
/*     */           {
/*     */             protected HashMap<String, Negotiator> initialValue() {
/* 147 */               return new HashMap<>();
/*     */             }
/*     */           };
/*     */       }
/* 151 */       ((HashMap<String, Negotiator>)cache.get()).put(str, negotiator);
/* 152 */       return true;
/*     */     } 
/* 154 */     supported.put(str, Boolean.valueOf(false));
/* 155 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static synchronized HashMap<String, Negotiator> getCache() {
/* 160 */     if (cache == null) return null; 
/* 161 */     return cache.get();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean useAuthCache() {
/* 166 */     return (super.useAuthCache() && cacheSPNEGO);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHeaderValue(URL paramURL, String paramString) {
/* 174 */     throw new RuntimeException("getHeaderValue not supported");
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
/*     */   public boolean isAuthorizationStale(String paramString) {
/* 187 */     return false;
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
/*     */   public synchronized boolean setHeaders(HttpURLConnection paramHttpURLConnection, HeaderParser paramHeaderParser, String paramString) {
/*     */     try {
/* 203 */       byte[] arrayOfByte = null;
/* 204 */       String[] arrayOfString = paramString.split("\\s+");
/* 205 */       if (arrayOfString.length > 1) {
/* 206 */         arrayOfByte = Base64.getDecoder().decode(arrayOfString[1]);
/*     */       }
/* 208 */       String str = this.hci.scheme + " " + Base64.getEncoder().encodeToString((arrayOfByte == null) ? 
/* 209 */           firstToken() : nextToken(arrayOfByte));
/*     */       
/* 211 */       paramHttpURLConnection.setAuthenticationProperty(getHeaderName(), str);
/* 212 */       return true;
/* 213 */     } catch (IOException iOException) {
/* 214 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] firstToken() throws IOException {
/* 225 */     this.negotiator = null;
/* 226 */     HashMap<String, Negotiator> hashMap = getCache();
/* 227 */     if (hashMap != null) {
/* 228 */       this.negotiator = hashMap.get(getHost());
/* 229 */       if (this.negotiator != null) {
/* 230 */         hashMap.remove(getHost());
/*     */       }
/*     */     } 
/* 233 */     if (this.negotiator == null) {
/* 234 */       this.negotiator = Negotiator.getNegotiator(this.hci);
/* 235 */       if (this.negotiator == null) {
/* 236 */         IOException iOException = new IOException("Cannot initialize Negotiator");
/* 237 */         throw iOException;
/*     */       } 
/*     */     } 
/*     */     
/* 241 */     return this.negotiator.firstToken();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] nextToken(byte[] paramArrayOfbyte) throws IOException {
/* 252 */     return this.negotiator.nextToken(paramArrayOfbyte);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/protocol/http/NegotiateAuthentication.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */