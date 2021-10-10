/*    */ package com.sun.xml.internal.ws.transport.http.server;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.server.WSEndpoint;
/*    */ import com.sun.xml.internal.ws.transport.http.HttpAdapter;
/*    */ import com.sun.xml.internal.ws.transport.http.HttpAdapterList;
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
/*    */ public class ServerAdapterList
/*    */   extends HttpAdapterList<ServerAdapter>
/*    */ {
/*    */   protected ServerAdapter createHttpAdapter(String name, String urlPattern, WSEndpoint<?> endpoint) {
/* 34 */     return new ServerAdapter(name, urlPattern, endpoint, this);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/transport/http/server/ServerAdapterList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */