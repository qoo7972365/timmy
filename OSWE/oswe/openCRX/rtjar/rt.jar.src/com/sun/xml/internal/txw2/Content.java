/*    */ package com.sun.xml.internal.txw2;
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
/*    */ abstract class Content
/*    */ {
/*    */   private Content next;
/*    */   
/*    */   final Content getNext() {
/* 38 */     return this.next;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   final void setNext(Document doc, Content next) {
/* 50 */     assert next != null;
/* 51 */     assert this.next == null : "next of " + this + " is already set to " + this.next;
/* 52 */     this.next = next;
/* 53 */     doc.run();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   boolean isReadyToCommit() {
/* 60 */     return true;
/*    */   }
/*    */   
/*    */   abstract boolean concludesPendingStartTag();
/*    */   
/*    */   abstract void accept(ContentVisitor paramContentVisitor);
/*    */   
/*    */   public void written() {}
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/txw2/Content.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */