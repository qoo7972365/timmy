/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import java.security.Provider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.dom.DOMCryptoContext;
/*     */ import javax.xml.crypto.dsig.Manifest;
/*     */ import javax.xml.crypto.dsig.Reference;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
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
/*     */ public final class DOMManifest
/*     */   extends DOMStructure
/*     */   implements Manifest
/*     */ {
/*     */   private final List<Reference> references;
/*     */   private final String id;
/*     */   
/*     */   public DOMManifest(List<? extends Reference> paramList, String paramString) {
/*  67 */     if (paramList == null) {
/*  68 */       throw new NullPointerException("references cannot be null");
/*     */     }
/*  70 */     this
/*  71 */       .references = Collections.unmodifiableList(new ArrayList<>(paramList));
/*  72 */     if (this.references.isEmpty())
/*  73 */       throw new IllegalArgumentException("list of references must contain at least one entry"); 
/*     */     byte b;
/*     */     int i;
/*  76 */     for (b = 0, i = this.references.size(); b < i; b++) {
/*  77 */       if (!(this.references.get(b) instanceof Reference)) {
/*  78 */         throw new ClassCastException("references[" + b + "] is not a valid type");
/*     */       }
/*     */     } 
/*     */     
/*  82 */     this.id = paramString;
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
/*     */   public DOMManifest(Element paramElement, XMLCryptoContext paramXMLCryptoContext, Provider paramProvider) throws MarshalException {
/*  94 */     Attr attr = paramElement.getAttributeNodeNS((String)null, "Id");
/*  95 */     if (attr != null) {
/*  96 */       this.id = attr.getValue();
/*  97 */       paramElement.setIdAttributeNode(attr, true);
/*     */     } else {
/*  99 */       this.id = null;
/*     */     } 
/*     */     
/* 102 */     boolean bool = Utils.secureValidation(paramXMLCryptoContext);
/*     */     
/* 104 */     Element element = DOMUtils.getFirstChildElement(paramElement, "Reference");
/* 105 */     ArrayList<DOMReference> arrayList = new ArrayList();
/* 106 */     arrayList.add(new DOMReference(element, paramXMLCryptoContext, paramProvider));
/*     */     
/* 108 */     element = DOMUtils.getNextSiblingElement(element);
/* 109 */     while (element != null) {
/* 110 */       String str = element.getLocalName();
/* 111 */       if (!str.equals("Reference")) {
/* 112 */         throw new MarshalException("Invalid element name: " + str + ", expected Reference");
/*     */       }
/*     */       
/* 115 */       arrayList.add(new DOMReference(element, paramXMLCryptoContext, paramProvider));
/* 116 */       if (bool && Policy.restrictNumReferences(arrayList.size())) {
/* 117 */         String str1 = "A maximum of " + Policy.maxReferences() + " references per Manifest are allowed when secure validation is enabled";
/*     */ 
/*     */         
/* 120 */         throw new MarshalException(str1);
/*     */       } 
/* 122 */       element = DOMUtils.getNextSiblingElement(element);
/*     */     } 
/* 124 */     this.references = Collections.unmodifiableList((List)arrayList);
/*     */   }
/*     */   
/*     */   public String getId() {
/* 128 */     return this.id;
/*     */   }
/*     */   
/*     */   public List getReferences() {
/* 132 */     return this.references;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
/* 138 */     Document document = DOMUtils.getOwnerDocument(paramNode);
/* 139 */     Element element = DOMUtils.createElement(document, "Manifest", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */ 
/*     */     
/* 142 */     DOMUtils.setAttributeID(element, "Id", this.id);
/*     */ 
/*     */     
/* 145 */     for (Reference reference : this.references) {
/* 146 */       ((DOMReference)reference).marshal(element, paramString, paramDOMCryptoContext);
/*     */     }
/* 148 */     paramNode.appendChild(element);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 153 */     if (this == paramObject) {
/* 154 */       return true;
/*     */     }
/*     */     
/* 157 */     if (!(paramObject instanceof Manifest)) {
/* 158 */       return false;
/*     */     }
/* 160 */     Manifest manifest = (Manifest)paramObject;
/*     */ 
/*     */     
/* 163 */     boolean bool = (this.id == null) ? ((manifest.getId() == null) ? true : false) : this.id.equals(manifest.getId());
/*     */     
/* 165 */     return (bool && this.references.equals(manifest.getReferences()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 170 */     int i = 17;
/* 171 */     if (this.id != null) {
/* 172 */       i = 31 * i + this.id.hashCode();
/*     */     }
/* 174 */     i = 31 * i + this.references.hashCode();
/*     */     
/* 176 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMManifest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */