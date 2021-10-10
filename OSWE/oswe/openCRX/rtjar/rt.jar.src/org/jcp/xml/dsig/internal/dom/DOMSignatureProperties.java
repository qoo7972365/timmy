/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.dom.DOMCryptoContext;
/*     */ import javax.xml.crypto.dsig.SignatureProperties;
/*     */ import javax.xml.crypto.dsig.SignatureProperty;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DOMSignatureProperties
/*     */   extends DOMStructure
/*     */   implements SignatureProperties
/*     */ {
/*     */   private final String id;
/*     */   private final List<SignatureProperty> properties;
/*     */   
/*     */   public DOMSignatureProperties(List<? extends SignatureProperty> paramList, String paramString) {
/*  70 */     if (paramList == null)
/*  71 */       throw new NullPointerException("properties cannot be null"); 
/*  72 */     if (paramList.isEmpty()) {
/*  73 */       throw new IllegalArgumentException("properties cannot be empty");
/*     */     }
/*  75 */     this.properties = Collections.unmodifiableList(new ArrayList<>(paramList)); byte b;
/*     */     int i;
/*  77 */     for (b = 0, i = this.properties.size(); b < i; b++) {
/*  78 */       if (!(this.properties.get(b) instanceof SignatureProperty)) {
/*  79 */         throw new ClassCastException("properties[" + b + "] is not a valid type");
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  84 */     this.id = paramString;
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
/*     */   public DOMSignatureProperties(Element paramElement, XMLCryptoContext paramXMLCryptoContext) throws MarshalException {
/*  97 */     Attr attr = paramElement.getAttributeNodeNS((String)null, "Id");
/*  98 */     if (attr != null) {
/*  99 */       this.id = attr.getValue();
/* 100 */       paramElement.setIdAttributeNode(attr, true);
/*     */     } else {
/* 102 */       this.id = null;
/*     */     } 
/*     */     
/* 105 */     NodeList nodeList = paramElement.getChildNodes();
/* 106 */     int i = nodeList.getLength();
/* 107 */     ArrayList<DOMSignatureProperty> arrayList = new ArrayList(i);
/*     */     
/* 109 */     for (byte b = 0; b < i; b++) {
/* 110 */       Node node = nodeList.item(b);
/* 111 */       if (node.getNodeType() == 1) {
/* 112 */         String str = node.getLocalName();
/* 113 */         if (!str.equals("SignatureProperty")) {
/* 114 */           throw new MarshalException("Invalid element name: " + str + ", expected SignatureProperty");
/*     */         }
/*     */         
/* 117 */         arrayList.add(new DOMSignatureProperty((Element)node, paramXMLCryptoContext));
/*     */       } 
/*     */     } 
/*     */     
/* 121 */     if (arrayList.isEmpty()) {
/* 122 */       throw new MarshalException("properties cannot be empty");
/*     */     }
/* 124 */     this.properties = Collections.unmodifiableList((List)arrayList);
/*     */   }
/*     */ 
/*     */   
/*     */   public List getProperties() {
/* 129 */     return this.properties;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 133 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
/* 139 */     Document document = DOMUtils.getOwnerDocument(paramNode);
/* 140 */     Element element = DOMUtils.createElement(document, "SignatureProperties", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     DOMUtils.setAttributeID(element, "Id", this.id);
/*     */ 
/*     */     
/* 149 */     for (SignatureProperty signatureProperty : this.properties) {
/* 150 */       ((DOMSignatureProperty)signatureProperty).marshal(element, paramString, paramDOMCryptoContext);
/*     */     }
/*     */ 
/*     */     
/* 154 */     paramNode.appendChild(element);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 159 */     if (this == paramObject) {
/* 160 */       return true;
/*     */     }
/*     */     
/* 163 */     if (!(paramObject instanceof SignatureProperties)) {
/* 164 */       return false;
/*     */     }
/* 166 */     SignatureProperties signatureProperties = (SignatureProperties)paramObject;
/*     */ 
/*     */     
/* 169 */     boolean bool = (this.id == null) ? ((signatureProperties.getId() == null) ? true : false) : this.id.equals(signatureProperties.getId());
/*     */     
/* 171 */     return (this.properties.equals(signatureProperties.getProperties()) && bool);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 176 */     int i = 17;
/* 177 */     if (this.id != null) {
/* 178 */       i = 31 * i + this.id.hashCode();
/*     */     }
/* 180 */     i = 31 * i + this.properties.hashCode();
/*     */     
/* 182 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMSignatureProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */