/*    */ package com.sun.xml.internal.ws.api.message;
/*    */ 
/*    */ import com.sun.xml.internal.ws.util.exception.JAXWSExceptionBase;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ExceptionHasMessage
/*    */   extends JAXWSExceptionBase
/*    */ {
/*    */   public ExceptionHasMessage(String key, Object... args) {
/* 44 */     super(key, args);
/*    */   }
/*    */   
/*    */   public abstract Message getFaultMessage();
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/message/ExceptionHasMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */