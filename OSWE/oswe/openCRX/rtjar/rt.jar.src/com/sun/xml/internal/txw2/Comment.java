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
/*    */ 
/*    */ final class Comment
/*    */   extends Content
/*    */ {
/* 37 */   private final StringBuilder buffer = new StringBuilder();
/*    */   
/*    */   public Comment(Document document, NamespaceResolver nsResolver, Object obj) {
/* 40 */     document.writeValue(obj, nsResolver, this.buffer);
/*    */   }
/*    */   
/*    */   boolean concludesPendingStartTag() {
/* 44 */     return false;
/*    */   }
/*    */   
/*    */   void accept(ContentVisitor visitor) {
/* 48 */     visitor.onComment(this.buffer);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/txw2/Comment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */