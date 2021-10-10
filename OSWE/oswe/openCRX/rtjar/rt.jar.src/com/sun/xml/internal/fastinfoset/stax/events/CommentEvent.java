/*    */ package com.sun.xml.internal.fastinfoset.stax.events;
/*    */ 
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
/*    */ public class CommentEvent
/*    */   extends EventBase
/*    */   implements Comment
/*    */ {
/*    */   private String _text;
/*    */   
/*    */   public CommentEvent() {
/* 39 */     super(5);
/*    */   }
/*    */   
/*    */   public CommentEvent(String text) {
/* 43 */     this();
/* 44 */     this._text = text;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 52 */     return "<!--" + this._text + "-->";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getText() {
/* 60 */     return this._text;
/*    */   }
/*    */   
/*    */   public void setText(String text) {
/* 64 */     this._text = text;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/stax/events/CommentEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */