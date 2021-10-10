/*    */ package com.sun.org.apache.xerces.internal.dom;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DeferredProcessingInstructionImpl
/*    */   extends ProcessingInstructionImpl
/*    */   implements DeferredNode
/*    */ {
/*    */   static final long serialVersionUID = -4643577954293565388L;
/*    */   protected transient int fNodeIndex;
/*    */   
/*    */   DeferredProcessingInstructionImpl(DeferredDocumentImpl ownerDocument, int nodeIndex) {
/* 60 */     super(ownerDocument, (String)null, (String)null);
/*    */     
/* 62 */     this.fNodeIndex = nodeIndex;
/* 63 */     needsSyncData(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getNodeIndex() {
/* 73 */     return this.fNodeIndex;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void synchronizeData() {
/* 84 */     needsSyncData(false);
/*    */ 
/*    */ 
/*    */     
/* 88 */     DeferredDocumentImpl ownerDocument = (DeferredDocumentImpl)ownerDocument();
/* 89 */     this.target = ownerDocument.getNodeName(this.fNodeIndex);
/* 90 */     this.data = ownerDocument.getNodeValueString(this.fNodeIndex);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/dom/DeferredProcessingInstructionImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */