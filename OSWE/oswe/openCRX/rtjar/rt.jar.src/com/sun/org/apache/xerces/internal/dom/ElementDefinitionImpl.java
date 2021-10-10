/*     */ package com.sun.org.apache.xerces.internal.dom;
/*     */ 
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ElementDefinitionImpl
/*     */   extends ParentNode
/*     */ {
/*     */   static final long serialVersionUID = -8373890672670022714L;
/*     */   protected String name;
/*     */   protected NamedNodeMapImpl attributes;
/*     */   
/*     */   public ElementDefinitionImpl(CoreDocumentImpl ownerDocument, String name) {
/*  62 */     super(ownerDocument);
/*  63 */     this.name = name;
/*  64 */     this.attributes = new NamedNodeMapImpl(ownerDocument);
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
/*     */   public short getNodeType() {
/*  76 */     return 21;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNodeName() {
/*  83 */     if (needsSyncData()) {
/*  84 */       synchronizeData();
/*     */     }
/*  86 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node cloneNode(boolean deep) {
/*  95 */     ElementDefinitionImpl newnode = (ElementDefinitionImpl)super.cloneNode(deep);
/*     */     
/*  97 */     newnode.attributes = this.attributes.cloneMap(newnode);
/*  98 */     return newnode;
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
/*     */ 
/*     */   
/*     */   public NamedNodeMap getAttributes() {
/* 114 */     if (needsSyncChildren()) {
/* 115 */       synchronizeChildren();
/*     */     }
/* 117 */     return this.attributes;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/dom/ElementDefinitionImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */