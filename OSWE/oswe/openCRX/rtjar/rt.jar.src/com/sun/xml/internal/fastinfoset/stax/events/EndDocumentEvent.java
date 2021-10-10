/*    */ package com.sun.xml.internal.fastinfoset.stax.events;
/*    */ 
/*    */ import javax.xml.stream.events.EndDocument;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EndDocumentEvent
/*    */   extends EventBase
/*    */   implements EndDocument
/*    */ {
/*    */   public EndDocumentEvent() {
/* 36 */     super(8);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 40 */     return "<? EndDocument ?>";
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/stax/events/EndDocumentEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */