/*    */ package com.sun.xml.internal.ws.client.sei;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.concurrent.Future;
/*    */ import javax.xml.ws.AsyncHandler;
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
/*    */ final class CallbackMethodHandler
/*    */   extends AsyncMethodHandler
/*    */ {
/*    */   private final int handlerPos;
/*    */   
/*    */   CallbackMethodHandler(SEIStub owner, Method m, int handlerPos) {
/* 49 */     super(owner, m);
/* 50 */     this.handlerPos = handlerPos;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   Future<?> invoke(Object proxy, Object[] args) throws WebServiceException {
/* 60 */     AsyncHandler handler = (AsyncHandler)args[this.handlerPos];
/*    */     
/* 62 */     return (Future<?>)doInvoke(proxy, args, handler);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/sei/CallbackMethodHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */