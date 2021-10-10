/*     */ package com.sun.org.apache.xerces.internal.dom;
/*     */ 
/*     */ import org.w3c.dom.NamedNodeMap;
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
/*     */ public class DeferredElementImpl
/*     */   extends ElementImpl
/*     */   implements DeferredNode
/*     */ {
/*     */   static final long serialVersionUID = -7670981133940934842L;
/*     */   protected transient int fNodeIndex;
/*     */   
/*     */   DeferredElementImpl(DeferredDocumentImpl ownerDoc, int nodeIndex) {
/*  79 */     super(ownerDoc, (String)null);
/*     */     
/*  81 */     this.fNodeIndex = nodeIndex;
/*  82 */     needsSyncChildren(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getNodeIndex() {
/*  92 */     return this.fNodeIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void synchronizeData() {
/* 103 */     needsSyncData(false);
/*     */ 
/*     */     
/* 106 */     DeferredDocumentImpl ownerDocument = (DeferredDocumentImpl)this.ownerDocument;
/*     */ 
/*     */ 
/*     */     
/* 110 */     boolean orig = ownerDocument.mutationEvents;
/* 111 */     ownerDocument.mutationEvents = false;
/*     */     
/* 113 */     this.name = ownerDocument.getNodeName(this.fNodeIndex);
/*     */ 
/*     */     
/* 116 */     setupDefaultAttributes();
/* 117 */     int index = ownerDocument.getNodeExtra(this.fNodeIndex);
/* 118 */     if (index != -1) {
/* 119 */       NamedNodeMap attrs = getAttributes();
/*     */       do {
/* 121 */         NodeImpl attr = (NodeImpl)ownerDocument.getNodeObject(index);
/* 122 */         attrs.setNamedItem(attr);
/* 123 */         index = ownerDocument.getPrevSibling(index);
/* 124 */       } while (index != -1);
/*     */     } 
/*     */ 
/*     */     
/* 128 */     ownerDocument.mutationEvents = orig;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void synchronizeChildren() {
/* 134 */     DeferredDocumentImpl ownerDocument = (DeferredDocumentImpl)ownerDocument();
/* 135 */     ownerDocument.synchronizeChildren(this, this.fNodeIndex);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/dom/DeferredElementImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */