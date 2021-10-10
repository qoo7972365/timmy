/*     */ package com.sun.org.apache.xerces.internal.dom;
/*     */ 
/*     */ import org.w3c.dom.DocumentFragment;
/*     */ import org.w3c.dom.Text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DocumentFragmentImpl
/*     */   extends ParentNode
/*     */   implements DocumentFragment
/*     */ {
/*     */   static final long serialVersionUID = -7596449967279236746L;
/*     */   
/*     */   public DocumentFragmentImpl(CoreDocumentImpl ownerDoc) {
/*  85 */     super(ownerDoc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocumentFragmentImpl() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getNodeType() {
/* 100 */     return 11;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNodeName() {
/* 105 */     return "#document-fragment";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void normalize() {
/* 115 */     if (isNormalized()) {
/*     */       return;
/*     */     }
/* 118 */     if (needsSyncChildren()) {
/* 119 */       synchronizeChildren();
/*     */     }
/*     */ 
/*     */     
/* 123 */     for (ChildNode kid = this.firstChild; kid != null; kid = next) {
/* 124 */       ChildNode next = kid.nextSibling;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 131 */       if (kid.getNodeType() == 3)
/*     */       {
/*     */         
/* 134 */         if (next != null && next.getNodeType() == 3) {
/*     */           
/* 136 */           ((Text)kid).appendData(next.getNodeValue());
/* 137 */           removeChild(next);
/* 138 */           next = kid;
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 143 */         else if (kid.getNodeValue() == null || kid.getNodeValue().length() == 0) {
/* 144 */           removeChild(kid);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 149 */       kid.normalize();
/*     */     } 
/*     */     
/* 152 */     isNormalized(true);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/dom/DocumentFragmentImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */