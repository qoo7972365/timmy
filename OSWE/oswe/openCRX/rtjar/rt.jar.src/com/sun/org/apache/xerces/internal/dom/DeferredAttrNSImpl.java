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
/*     */ public final class DeferredAttrNSImpl
/*     */   extends AttrNSImpl
/*     */   implements DeferredNode
/*     */ {
/*     */   static final long serialVersionUID = 6074924934945957154L;
/*     */   protected transient int fNodeIndex;
/*     */   
/*     */   DeferredAttrNSImpl(DeferredDocumentImpl ownerDocument, int nodeIndex) {
/*  67 */     super(ownerDocument, (String)null);
/*     */     
/*  69 */     this.fNodeIndex = nodeIndex;
/*  70 */     needsSyncData(true);
/*  71 */     needsSyncChildren(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNodeIndex() {
/*  81 */     return this.fNodeIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void synchronizeData() {
/*  92 */     needsSyncData(false);
/*     */ 
/*     */ 
/*     */     
/*  96 */     DeferredDocumentImpl ownerDocument = (DeferredDocumentImpl)ownerDocument();
/*  97 */     this.name = ownerDocument.getNodeName(this.fNodeIndex);
/*     */ 
/*     */     
/* 100 */     int index = this.name.indexOf(':');
/* 101 */     if (index < 0) {
/* 102 */       this.localName = this.name;
/*     */     } else {
/*     */       
/* 105 */       this.localName = this.name.substring(index + 1);
/*     */     } 
/*     */     
/* 108 */     int extra = ownerDocument.getNodeExtra(this.fNodeIndex);
/* 109 */     isSpecified(((extra & 0x20) != 0));
/* 110 */     isIdAttribute(((extra & 0x200) != 0));
/*     */     
/* 112 */     this.namespaceURI = ownerDocument.getNodeURI(this.fNodeIndex);
/*     */     
/* 114 */     int extraNode = ownerDocument.getLastChild(this.fNodeIndex);
/* 115 */     this.type = ownerDocument.getTypeInfo(extraNode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void synchronizeChildren() {
/* 126 */     DeferredDocumentImpl ownerDocument = (DeferredDocumentImpl)ownerDocument();
/* 127 */     ownerDocument.synchronizeChildren(this, this.fNodeIndex);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/dom/DeferredAttrNSImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */