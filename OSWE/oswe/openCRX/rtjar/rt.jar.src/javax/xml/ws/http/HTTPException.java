/*    */ package javax.xml.ws.http;
/*    */ 
/*    */ import javax.xml.ws.ProtocolException;
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
/*    */ public class HTTPException
/*    */   extends ProtocolException
/*    */ {
/*    */   private int statusCode;
/*    */   
/*    */   public HTTPException(int statusCode) {
/* 46 */     this.statusCode = statusCode;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getStatusCode() {
/* 54 */     return this.statusCode;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/ws/http/HTTPException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */