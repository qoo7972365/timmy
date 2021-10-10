/*    */ package com.sun.xml.internal.ws.client;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AsyncInvoker
/*    */   implements Runnable
/*    */ {
/*    */   protected AsyncResponseImpl responseImpl;
/*    */   protected boolean nonNullAsyncHandlerGiven;
/*    */   
/*    */   public void setReceiver(AsyncResponseImpl responseImpl) {
/* 50 */     this.responseImpl = responseImpl;
/*    */   }
/*    */   
/*    */   public AsyncResponseImpl getResponseImpl() {
/* 54 */     return this.responseImpl;
/*    */   }
/*    */   
/*    */   public void setResponseImpl(AsyncResponseImpl responseImpl) {
/* 58 */     this.responseImpl = responseImpl;
/*    */   }
/*    */   
/*    */   public boolean isNonNullAsyncHandlerGiven() {
/* 62 */     return this.nonNullAsyncHandlerGiven;
/*    */   }
/*    */   
/*    */   public void setNonNullAsyncHandlerGiven(boolean nonNullAsyncHandlerGiven) {
/* 66 */     this.nonNullAsyncHandlerGiven = nonNullAsyncHandlerGiven;
/*    */   }
/*    */   
/*    */   public void run() {
/*    */     try {
/* 71 */       do_run();
/* 72 */     } catch (WebServiceException e) {
/* 73 */       throw e;
/* 74 */     } catch (Throwable t) {
/*    */       
/* 76 */       throw new WebServiceException(t);
/*    */     } 
/*    */   }
/*    */   
/*    */   public abstract void do_run();
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/AsyncInvoker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */