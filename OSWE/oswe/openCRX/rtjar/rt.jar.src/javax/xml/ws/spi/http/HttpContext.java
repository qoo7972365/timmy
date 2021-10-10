/*    */ package javax.xml.ws.spi.http;
/*    */ 
/*    */ import java.util.Set;
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
/*    */ public abstract class HttpContext
/*    */ {
/*    */   protected HttpHandler handler;
/*    */   
/*    */   public void setHandler(HttpHandler handler) {
/* 55 */     this.handler = handler;
/*    */   }
/*    */   
/*    */   public abstract String getPath();
/*    */   
/*    */   public abstract Object getAttribute(String paramString);
/*    */   
/*    */   public abstract Set<String> getAttributeNames();
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/ws/spi/http/HttpContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */