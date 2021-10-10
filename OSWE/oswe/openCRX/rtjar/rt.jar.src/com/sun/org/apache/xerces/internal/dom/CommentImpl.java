/*    */ package com.sun.org.apache.xerces.internal.dom;
/*    */ 
/*    */ import org.w3c.dom.CharacterData;
/*    */ import org.w3c.dom.Comment;
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
/*    */ public class CommentImpl
/*    */   extends CharacterDataImpl
/*    */   implements CharacterData, Comment
/*    */ {
/*    */   static final long serialVersionUID = -2685736833408134044L;
/*    */   
/*    */   public CommentImpl(CoreDocumentImpl ownerDoc, String data) {
/* 51 */     super(ownerDoc, data);
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
/*    */   public short getNodeType() {
/* 63 */     return 8;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getNodeName() {
/* 68 */     return "#comment";
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/dom/CommentImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */