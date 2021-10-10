/*    */ package java.net;
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
/*    */ public interface CookiePolicy
/*    */ {
/* 42 */   public static final CookiePolicy ACCEPT_ALL = new CookiePolicy() {
/*    */       public boolean shouldAccept(URI param1URI, HttpCookie param1HttpCookie) {
/* 44 */         return true;
/*    */       }
/*    */     };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 51 */   public static final CookiePolicy ACCEPT_NONE = new CookiePolicy() {
/*    */       public boolean shouldAccept(URI param1URI, HttpCookie param1HttpCookie) {
/* 53 */         return false;
/*    */       }
/*    */     };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 60 */   public static final CookiePolicy ACCEPT_ORIGINAL_SERVER = new CookiePolicy() {
/*    */       public boolean shouldAccept(URI param1URI, HttpCookie param1HttpCookie) {
/* 62 */         if (param1URI == null || param1HttpCookie == null)
/* 63 */           return false; 
/* 64 */         return HttpCookie.domainMatches(param1HttpCookie.getDomain(), param1URI.getHost());
/*    */       }
/*    */     };
/*    */   
/*    */   boolean shouldAccept(URI paramURI, HttpCookie paramHttpCookie);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/CookiePolicy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */