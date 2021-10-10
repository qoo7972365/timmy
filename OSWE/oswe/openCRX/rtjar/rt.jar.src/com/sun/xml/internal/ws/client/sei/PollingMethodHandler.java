/*    */ package com.sun.xml.internal.ws.client.sei;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import javax.xml.ws.Response;
/*    */ import javax.xml.ws.WebServiceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class PollingMethodHandler
/*    */   extends AsyncMethodHandler
/*    */ {
/*    */   PollingMethodHandler(SEIStub owner, Method m) {
/* 46 */     super(owner, m);
/*    */   }
/*    */   
/*    */   Response<?> invoke(Object proxy, Object[] args) throws WebServiceException {
/* 50 */     return doInvoke(proxy, args, null);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/sei/PollingMethodHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */