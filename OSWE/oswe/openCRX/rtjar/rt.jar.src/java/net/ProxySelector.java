/*     */ package java.net;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
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
/*     */ public abstract class ProxySelector
/*     */ {
/*     */   private static ProxySelector theProxySelector;
/*     */   
/*     */   static {
/*     */     try {
/*  72 */       Class<?> clazz = Class.forName("sun.net.spi.DefaultProxySelector");
/*  73 */       if (clazz != null && ProxySelector.class.isAssignableFrom(clazz)) {
/*  74 */         theProxySelector = (ProxySelector)clazz.newInstance();
/*     */       }
/*  76 */     } catch (Exception exception) {
/*  77 */       theProxySelector = null;
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
/*     */   public static ProxySelector getDefault() {
/*  92 */     SecurityManager securityManager = System.getSecurityManager();
/*  93 */     if (securityManager != null) {
/*  94 */       securityManager.checkPermission(SecurityConstants.GET_PROXYSELECTOR_PERMISSION);
/*     */     }
/*  96 */     return theProxySelector;
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
/*     */   public static void setDefault(ProxySelector paramProxySelector) {
/* 115 */     SecurityManager securityManager = System.getSecurityManager();
/* 116 */     if (securityManager != null) {
/* 117 */       securityManager.checkPermission(SecurityConstants.SET_PROXYSELECTOR_PERMISSION);
/*     */     }
/* 119 */     theProxySelector = paramProxySelector;
/*     */   }
/*     */   
/*     */   public abstract List<Proxy> select(URI paramURI);
/*     */   
/*     */   public abstract void connectFailed(URI paramURI, SocketAddress paramSocketAddress, IOException paramIOException);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/ProxySelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */