/*    */ package sun.security.jgss;
/*    */ 
/*    */ import sun.net.www.protocol.http.HttpCallerInfo;
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
/*    */ public class HttpCaller
/*    */   extends GSSCaller
/*    */ {
/*    */   private final HttpCallerInfo hci;
/*    */   
/*    */   public HttpCaller(HttpCallerInfo paramHttpCallerInfo) {
/* 38 */     super("HTTP_CLIENT");
/* 39 */     this.hci = paramHttpCallerInfo;
/*    */   }
/*    */   
/*    */   public HttpCallerInfo info() {
/* 43 */     return this.hci;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/HttpCaller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */