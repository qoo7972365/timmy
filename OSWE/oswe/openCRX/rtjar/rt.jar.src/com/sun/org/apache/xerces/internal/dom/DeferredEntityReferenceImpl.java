/*     */ package com.sun.org.apache.xerces.internal.dom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeferredEntityReferenceImpl
/*     */   extends EntityReferenceImpl
/*     */   implements DeferredNode
/*     */ {
/*     */   static final long serialVersionUID = 390319091370032223L;
/*     */   protected transient int fNodeIndex;
/*     */   
/*     */   DeferredEntityReferenceImpl(DeferredDocumentImpl ownerDocument, int nodeIndex) {
/* 103 */     super(ownerDocument, (String)null);
/*     */     
/* 105 */     this.fNodeIndex = nodeIndex;
/* 106 */     needsSyncData(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNodeIndex() {
/* 116 */     return this.fNodeIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void synchronizeData() {
/* 130 */     needsSyncData(false);
/*     */ 
/*     */     
/* 133 */     DeferredDocumentImpl ownerDocument = (DeferredDocumentImpl)this.ownerDocument;
/*     */     
/* 135 */     this.name = ownerDocument.getNodeName(this.fNodeIndex);
/* 136 */     this.baseURI = ownerDocument.getNodeValue(this.fNodeIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void synchronizeChildren() {
/* 144 */     needsSyncChildren(false);
/*     */ 
/*     */     
/* 147 */     isReadOnly(false);
/*     */     
/* 149 */     DeferredDocumentImpl ownerDocument = (DeferredDocumentImpl)ownerDocument();
/* 150 */     ownerDocument.synchronizeChildren(this, this.fNodeIndex);
/* 151 */     setReadOnly(true, true);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/dom/DeferredEntityReferenceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */