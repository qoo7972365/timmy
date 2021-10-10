/*    */ package com.sun.org.apache.xml.internal.security.utils;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.w3c.dom.Document;
/*    */ import org.w3c.dom.Node;
/*    */ import org.w3c.dom.NodeList;
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
/*    */ public class HelperNodeList
/*    */   implements NodeList
/*    */ {
/* 38 */   List<Node> nodes = new ArrayList<>();
/*    */ 
/*    */   
/*    */   boolean allNodesMustHaveSameParent = false;
/*    */ 
/*    */   
/*    */   public HelperNodeList() {
/* 45 */     this(false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HelperNodeList(boolean paramBoolean) {
/* 53 */     this.allNodesMustHaveSameParent = paramBoolean;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Node item(int paramInt) {
/* 63 */     return this.nodes.get(paramInt);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLength() {
/* 72 */     return this.nodes.size();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void appendChild(Node paramNode) throws IllegalArgumentException {
/* 82 */     if (this.allNodesMustHaveSameParent && getLength() > 0 && 
/* 83 */       item(0).getParentNode() != paramNode.getParentNode()) {
/* 84 */       throw new IllegalArgumentException("Nodes have not the same Parent");
/*    */     }
/* 86 */     this.nodes.add(paramNode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Document getOwnerDocument() {
/* 93 */     if (getLength() == 0) {
/* 94 */       return null;
/*    */     }
/* 96 */     return XMLUtils.getOwnerDocument(item(0));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/HelperNodeList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */