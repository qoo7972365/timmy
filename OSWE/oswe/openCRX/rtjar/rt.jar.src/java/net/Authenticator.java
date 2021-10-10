/*     */ package java.net;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Authenticator
/*     */ {
/*     */   private static Authenticator theAuthenticator;
/*     */   private String requestingHost;
/*     */   private InetAddress requestingSite;
/*     */   private int requestingPort;
/*     */   private String requestingProtocol;
/*     */   private String requestingPrompt;
/*     */   private String requestingScheme;
/*     */   private URL requestingURL;
/*     */   private RequestorType requestingAuthType;
/*     */   
/*     */   public enum RequestorType
/*     */   {
/*  83 */     PROXY,
/*     */ 
/*     */ 
/*     */     
/*  87 */     SERVER;
/*     */   }
/*     */   
/*     */   private void reset() {
/*  91 */     this.requestingHost = null;
/*  92 */     this.requestingSite = null;
/*  93 */     this.requestingPort = -1;
/*  94 */     this.requestingProtocol = null;
/*  95 */     this.requestingPrompt = null;
/*  96 */     this.requestingScheme = null;
/*  97 */     this.requestingURL = null;
/*  98 */     this.requestingAuthType = RequestorType.SERVER;
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
/*     */   public static synchronized void setDefault(Authenticator paramAuthenticator) {
/* 123 */     SecurityManager securityManager = System.getSecurityManager();
/* 124 */     if (securityManager != null) {
/* 125 */       NetPermission netPermission = new NetPermission("setDefaultAuthenticator");
/*     */       
/* 127 */       securityManager.checkPermission(netPermission);
/*     */     } 
/*     */     
/* 130 */     theAuthenticator = paramAuthenticator;
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
/*     */   public static PasswordAuthentication requestPasswordAuthentication(InetAddress paramInetAddress, int paramInt, String paramString1, String paramString2, String paramString3) {
/* 167 */     SecurityManager securityManager = System.getSecurityManager();
/* 168 */     if (securityManager != null) {
/* 169 */       NetPermission netPermission = new NetPermission("requestPasswordAuthentication");
/*     */       
/* 171 */       securityManager.checkPermission(netPermission);
/*     */     } 
/*     */     
/* 174 */     Authenticator authenticator = theAuthenticator;
/* 175 */     if (authenticator == null) {
/* 176 */       return null;
/*     */     }
/* 178 */     synchronized (authenticator) {
/* 179 */       authenticator.reset();
/* 180 */       authenticator.requestingSite = paramInetAddress;
/* 181 */       authenticator.requestingPort = paramInt;
/* 182 */       authenticator.requestingProtocol = paramString1;
/* 183 */       authenticator.requestingPrompt = paramString2;
/* 184 */       authenticator.requestingScheme = paramString3;
/* 185 */       return authenticator.getPasswordAuthentication();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PasswordAuthentication requestPasswordAuthentication(String paramString1, InetAddress paramInetAddress, int paramInt, String paramString2, String paramString3, String paramString4) {
/* 229 */     SecurityManager securityManager = System.getSecurityManager();
/* 230 */     if (securityManager != null) {
/* 231 */       NetPermission netPermission = new NetPermission("requestPasswordAuthentication");
/*     */       
/* 233 */       securityManager.checkPermission(netPermission);
/*     */     } 
/*     */     
/* 236 */     Authenticator authenticator = theAuthenticator;
/* 237 */     if (authenticator == null) {
/* 238 */       return null;
/*     */     }
/* 240 */     synchronized (authenticator) {
/* 241 */       authenticator.reset();
/* 242 */       authenticator.requestingHost = paramString1;
/* 243 */       authenticator.requestingSite = paramInetAddress;
/* 244 */       authenticator.requestingPort = paramInt;
/* 245 */       authenticator.requestingProtocol = paramString2;
/* 246 */       authenticator.requestingPrompt = paramString3;
/* 247 */       authenticator.requestingScheme = paramString4;
/* 248 */       return authenticator.getPasswordAuthentication();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PasswordAuthentication requestPasswordAuthentication(String paramString1, InetAddress paramInetAddress, int paramInt, String paramString2, String paramString3, String paramString4, URL paramURL, RequestorType paramRequestorType) {
/* 296 */     SecurityManager securityManager = System.getSecurityManager();
/* 297 */     if (securityManager != null) {
/* 298 */       NetPermission netPermission = new NetPermission("requestPasswordAuthentication");
/*     */       
/* 300 */       securityManager.checkPermission(netPermission);
/*     */     } 
/*     */     
/* 303 */     Authenticator authenticator = theAuthenticator;
/* 304 */     if (authenticator == null) {
/* 305 */       return null;
/*     */     }
/* 307 */     synchronized (authenticator) {
/* 308 */       authenticator.reset();
/* 309 */       authenticator.requestingHost = paramString1;
/* 310 */       authenticator.requestingSite = paramInetAddress;
/* 311 */       authenticator.requestingPort = paramInt;
/* 312 */       authenticator.requestingProtocol = paramString2;
/* 313 */       authenticator.requestingPrompt = paramString3;
/* 314 */       authenticator.requestingScheme = paramString4;
/* 315 */       authenticator.requestingURL = paramURL;
/* 316 */       authenticator.requestingAuthType = paramRequestorType;
/* 317 */       return authenticator.getPasswordAuthentication();
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
/*     */   protected final String getRequestingHost() {
/* 332 */     return this.requestingHost;
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
/*     */   protected final InetAddress getRequestingSite() {
/* 344 */     return this.requestingSite;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int getRequestingPort() {
/* 353 */     return this.requestingPort;
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
/*     */   protected final String getRequestingProtocol() {
/* 367 */     return this.requestingProtocol;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final String getRequestingPrompt() {
/* 377 */     return this.requestingPrompt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final String getRequestingScheme() {
/* 388 */     return this.requestingScheme;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PasswordAuthentication getPasswordAuthentication() {
/* 398 */     return null;
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
/*     */   protected URL getRequestingURL() {
/* 411 */     return this.requestingURL;
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
/*     */   protected RequestorType getRequestorType() {
/* 423 */     return this.requestingAuthType;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/Authenticator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */