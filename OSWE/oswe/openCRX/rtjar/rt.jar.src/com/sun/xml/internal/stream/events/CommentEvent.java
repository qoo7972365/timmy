/*    */ package com.sun.xml.internal.stream.events;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import javax.xml.stream.events.Comment;
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
/*    */ public class CommentEvent
/*    */   extends DummyEvent
/*    */   implements Comment
/*    */ {
/*    */   private String fText;
/*    */   
/*    */   public CommentEvent() {
/* 42 */     init();
/*    */   }
/*    */   
/*    */   public CommentEvent(String text) {
/* 46 */     init();
/* 47 */     this.fText = text;
/*    */   }
/*    */   
/*    */   protected void init() {
/* 51 */     setEventType(5);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 58 */     return "<!--" + getText() + "-->";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getText() {
/* 67 */     return this.fText;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeAsEncodedUnicodeEx(Writer writer) throws IOException {
/* 73 */     writer.write("<!--" + getText() + "-->");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/stream/events/CommentEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */