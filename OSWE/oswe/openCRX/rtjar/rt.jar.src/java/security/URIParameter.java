/*    */ package java.security;
/*    */ 
/*    */ import java.net.URI;
/*    */ import javax.security.auth.login.Configuration;
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
/*    */ public class URIParameter
/*    */   implements Policy.Parameters, Configuration.Parameters
/*    */ {
/*    */   private URI uri;
/*    */   
/*    */   public URIParameter(URI paramURI) {
/* 49 */     if (paramURI == null) {
/* 50 */       throw new NullPointerException("invalid null URI");
/*    */     }
/* 52 */     this.uri = paramURI;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public URI getURI() {
/* 61 */     return this.uri;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/URIParameter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */