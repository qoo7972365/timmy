/*    */ package com.sun.xml.internal.ws.addressing.model;
/*    */ 
/*    */ import com.sun.xml.internal.ws.resources.AddressingMessages;
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
/*    */ public class ActionNotSupportedException
/*    */   extends WebServiceException
/*    */ {
/*    */   private String action;
/*    */   
/*    */   public ActionNotSupportedException(String action) {
/* 39 */     super(AddressingMessages.ACTION_NOT_SUPPORTED_EXCEPTION(action));
/* 40 */     this.action = action;
/*    */   }
/*    */   
/*    */   public String getAction() {
/* 44 */     return this.action;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/addressing/model/ActionNotSupportedException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */