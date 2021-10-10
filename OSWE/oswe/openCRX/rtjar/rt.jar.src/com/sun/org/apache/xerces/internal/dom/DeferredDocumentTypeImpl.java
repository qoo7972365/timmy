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
/*     */ public class DeferredDocumentTypeImpl
/*     */   extends DocumentTypeImpl
/*     */   implements DeferredNode
/*     */ {
/*     */   static final long serialVersionUID = -2172579663227313509L;
/*     */   protected transient int fNodeIndex;
/*     */   
/*     */   DeferredDocumentTypeImpl(DeferredDocumentImpl ownerDocument, int nodeIndex) {
/*  73 */     super(ownerDocument, (String)null);
/*     */     
/*  75 */     this.fNodeIndex = nodeIndex;
/*  76 */     needsSyncData(true);
/*  77 */     needsSyncChildren(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNodeIndex() {
/*  87 */     return this.fNodeIndex;
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
/*  98 */     needsSyncData(false);
/*     */ 
/*     */     
/* 101 */     DeferredDocumentImpl ownerDocument = (DeferredDocumentImpl)this.ownerDocument;
/*     */     
/* 103 */     this.name = ownerDocument.getNodeName(this.fNodeIndex);
/*     */ 
/*     */     
/* 106 */     this.publicID = ownerDocument.getNodeValue(this.fNodeIndex);
/* 107 */     this.systemID = ownerDocument.getNodeURI(this.fNodeIndex);
/* 108 */     int extraDataIndex = ownerDocument.getNodeExtra(this.fNodeIndex);
/* 109 */     this.internalSubset = ownerDocument.getNodeValue(extraDataIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void synchronizeChildren() {
/* 116 */     boolean orig = ownerDocument().getMutationEvents();
/* 117 */     ownerDocument().setMutationEvents(false);
/*     */ 
/*     */     
/* 120 */     needsSyncChildren(false);
/*     */ 
/*     */     
/* 123 */     DeferredDocumentImpl ownerDocument = (DeferredDocumentImpl)this.ownerDocument;
/*     */ 
/*     */     
/* 126 */     this.entities = new NamedNodeMapImpl(this);
/* 127 */     this.notations = new NamedNodeMapImpl(this);
/* 128 */     this.elements = new NamedNodeMapImpl(this);
/*     */ 
/*     */     
/* 131 */     DeferredNode last = null;
/* 132 */     int index = ownerDocument.getLastChild(this.fNodeIndex);
/* 133 */     for (; index != -1; 
/* 134 */       index = ownerDocument.getPrevSibling(index)) {
/*     */       
/* 136 */       DeferredNode node = ownerDocument.getNodeObject(index);
/* 137 */       int type = node.getNodeType();
/* 138 */       switch (type) {
/*     */ 
/*     */         
/*     */         case 6:
/* 142 */           this.entities.setNamedItem(node);
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case 12:
/* 148 */           this.notations.setNamedItem(node);
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case 21:
/* 154 */           this.elements.setNamedItem(node);
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case 1:
/* 160 */           if (((DocumentImpl)getOwnerDocument()).allowGrammarAccess) {
/* 161 */             insertBefore(node, last);
/* 162 */             last = node;
/*     */             break;
/*     */           } 
/*     */ 
/*     */ 
/*     */         
/*     */         default:
/* 169 */           System.out.println("DeferredDocumentTypeImpl#synchronizeInfo: node.getNodeType() = " + node
/*     */ 
/*     */               
/* 172 */               .getNodeType() + ", class = " + node
/*     */               
/* 174 */               .getClass().getName());
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 180 */     ownerDocument().setMutationEvents(orig);
/*     */ 
/*     */     
/* 183 */     setReadOnly(true, false);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/dom/DeferredDocumentTypeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */