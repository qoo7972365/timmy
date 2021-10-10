/*    */ package com.sun.xml.internal.ws.binding;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.BindingID;
/*    */ import com.sun.xml.internal.ws.client.HandlerConfiguration;
/*    */ import com.sun.xml.internal.ws.resources.ClientMessages;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.xml.ws.WebServiceException;
/*    */ import javax.xml.ws.WebServiceFeature;
/*    */ import javax.xml.ws.handler.Handler;
/*    */ import javax.xml.ws.http.HTTPBinding;
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
/*    */ public class HTTPBindingImpl
/*    */   extends BindingImpl
/*    */   implements HTTPBinding
/*    */ {
/*    */   HTTPBindingImpl() {
/* 49 */     this(EMPTY_FEATURES);
/*    */   }
/*    */   
/*    */   HTTPBindingImpl(WebServiceFeature... features) {
/* 53 */     super(BindingID.XML_HTTP, features);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setHandlerChain(List<Handler> chain) {
/* 63 */     for (Handler handler : chain) {
/* 64 */       if (!(handler instanceof javax.xml.ws.handler.LogicalHandler)) {
/* 65 */         throw new WebServiceException(ClientMessages.NON_LOGICAL_HANDLER_SET(handler.getClass()));
/*    */       }
/*    */     } 
/* 68 */     setHandlerConfig(new HandlerConfiguration(Collections.emptySet(), chain));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/binding/HTTPBindingImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */