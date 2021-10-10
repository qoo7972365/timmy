/*     */ package java.net;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import sun.security.util.SecurityConstants;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ResponseCache
/*     */ {
/*     */   private static ResponseCache theResponseCache;
/*     */   
/*     */   public static synchronized ResponseCache getDefault() {
/*  84 */     SecurityManager securityManager = System.getSecurityManager();
/*  85 */     if (securityManager != null) {
/*  86 */       securityManager.checkPermission(SecurityConstants.GET_RESPONSECACHE_PERMISSION);
/*     */     }
/*  88 */     return theResponseCache;
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
/*     */   public static synchronized void setDefault(ResponseCache paramResponseCache) {
/* 107 */     SecurityManager securityManager = System.getSecurityManager();
/* 108 */     if (securityManager != null) {
/* 109 */       securityManager.checkPermission(SecurityConstants.SET_RESPONSECACHE_PERMISSION);
/*     */     }
/* 111 */     theResponseCache = paramResponseCache;
/*     */   }
/*     */   
/*     */   public abstract CacheResponse get(URI paramURI, String paramString, Map<String, List<String>> paramMap) throws IOException;
/*     */   
/*     */   public abstract CacheRequest put(URI paramURI, URLConnection paramURLConnection) throws IOException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/ResponseCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */