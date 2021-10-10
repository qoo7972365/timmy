/*    */ package com.sun.xml.internal.stream.events;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EndDocumentEvent
/*    */   extends DummyEvent
/*    */   implements EndDocument
/*    */ {
/*    */   public EndDocumentEvent() {
/* 43 */     init();
/*    */   }
/*    */   
/*    */   protected void init() {
/* 47 */     setEventType(8);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 51 */     return "ENDDOCUMENT";
/*    */   }
/*    */   
/*    */   protected void writeAsEncodedUnicodeEx(Writer writer) throws IOException {}
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/stream/events/EndDocumentEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */