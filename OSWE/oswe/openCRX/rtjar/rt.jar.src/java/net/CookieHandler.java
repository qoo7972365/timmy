/*    */ package java.net;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import sun.security.util.SecurityConstants;
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
/*    */ public abstract class CookieHandler
/*    */ {
/*    */   private static CookieHandler cookieHandler;
/*    */   
/*    */   public static synchronized CookieHandler getDefault() {
/* 73 */     SecurityManager securityManager = System.getSecurityManager();
/* 74 */     if (securityManager != null) {
/* 75 */       securityManager.checkPermission(SecurityConstants.GET_COOKIEHANDLER_PERMISSION);
/*    */     }
/* 77 */     return cookieHandler;
/*    */   }
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
/*    */   public static synchronized void setDefault(CookieHandler paramCookieHandler) {
/* 93 */     SecurityManager securityManager = System.getSecurityManager();
/* 94 */     if (securityManager != null) {
/* 95 */       securityManager.checkPermission(SecurityConstants.SET_COOKIEHANDLER_PERMISSION);
/*    */     }
/* 97 */     cookieHandler = paramCookieHandler;
/*    */   }
/*    */   
/*    */   public abstract Map<String, List<String>> get(URI paramURI, Map<String, List<String>> paramMap) throws IOException;
/*    */   
/*    */   public abstract void put(URI paramURI, Map<String, List<String>> paramMap) throws IOException;
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/CookieHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */