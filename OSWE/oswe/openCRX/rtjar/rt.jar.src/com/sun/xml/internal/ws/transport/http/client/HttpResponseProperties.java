/*    */ package com.sun.xml.internal.ws.transport.http.client;
/*    */ 
/*    */ import com.oracle.webservices.internal.api.message.BasePropertySet;
/*    */ import com.oracle.webservices.internal.api.message.PropertySet.Property;
/*    */ import com.sun.istack.internal.NotNull;
/*    */ import java.util.List;
/*    */ import java.util.Map;
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
/*    */ final class HttpResponseProperties
/*    */   extends BasePropertySet
/*    */ {
/*    */   private final HttpClientTransport deferedCon;
/*    */   
/*    */   public HttpResponseProperties(@NotNull HttpClientTransport con) {
/* 48 */     this.deferedCon = con;
/*    */   }
/*    */   
/*    */   @Property({"javax.xml.ws.http.response.headers"})
/*    */   public Map<String, List<String>> getResponseHeaders() {
/* 53 */     return this.deferedCon.getHeaders();
/*    */   }
/*    */   
/*    */   @Property({"javax.xml.ws.http.response.code"})
/*    */   public int getResponseCode() {
/* 58 */     return this.deferedCon.statusCode;
/*    */   }
/*    */ 
/*    */   
/*    */   protected BasePropertySet.PropertyMap getPropertyMap() {
/* 63 */     return model;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 69 */   private static final BasePropertySet.PropertyMap model = parse(HttpResponseProperties.class);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/transport/http/client/HttpResponseProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */