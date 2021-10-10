/*    */ package org.w3c.dom.events;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EventException
/*    */   extends RuntimeException
/*    */ {
/*    */   public short code;
/*    */   public static final short UNSPECIFIED_EVENT_TYPE_ERR = 0;
/*    */   
/*    */   public EventException(short code, String message) {
/* 52 */     super(message);
/* 53 */     this.code = code;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/w3c/dom/events/EventException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */