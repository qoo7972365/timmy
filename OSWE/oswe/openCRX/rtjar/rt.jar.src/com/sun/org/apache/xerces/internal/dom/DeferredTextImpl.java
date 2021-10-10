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
/*     */ public class DeferredTextImpl
/*     */   extends TextImpl
/*     */   implements DeferredNode
/*     */ {
/*     */   static final long serialVersionUID = 2310613872100393425L;
/*     */   protected transient int fNodeIndex;
/*     */   
/*     */   DeferredTextImpl(DeferredDocumentImpl ownerDocument, int nodeIndex) {
/*  67 */     super(ownerDocument, (String)null);
/*     */     
/*  69 */     this.fNodeIndex = nodeIndex;
/*  70 */     needsSyncData(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNodeIndex() {
/*  80 */     return this.fNodeIndex;
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
/*  91 */     needsSyncData(false);
/*     */ 
/*     */ 
/*     */     
/*  95 */     DeferredDocumentImpl ownerDocument = (DeferredDocumentImpl)ownerDocument();
/*  96 */     this.data = ownerDocument.getNodeValueString(this.fNodeIndex);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     isIgnorableWhitespace((ownerDocument.getNodeExtra(this.fNodeIndex) == 1));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/dom/DeferredTextImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */