/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.XMLStructure;
/*     */ import javax.xml.crypto.dom.DOMCryptoContext;
/*     */ import javax.xml.crypto.dom.DOMStructure;
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
/*     */ public final class DOMSignatureProperty
/*     */   extends DOMStructure
/*     */   implements SignatureProperty
/*     */ {
/*     */   private final String id;
/*     */   private final String target;
/*     */   private final List<XMLStructure> content;
/*     */   
/*     */   public DOMSignatureProperty(List<? extends XMLStructure> paramList, String paramString1, String paramString2) {
/*  72 */     if (paramString1 == null)
/*  73 */       throw new NullPointerException("target cannot be null"); 
/*  74 */     if (paramList == null)
/*  75 */       throw new NullPointerException("content cannot be null"); 
/*  76 */     if (paramList.isEmpty()) {
/*  77 */       throw new IllegalArgumentException("content cannot be empty");
/*     */     }
/*  79 */     this.content = Collections.unmodifiableList(new ArrayList<>(paramList)); byte b;
/*     */     int i;
/*  81 */     for (b = 0, i = this.content.size(); b < i; b++) {
/*  82 */       if (!(this.content.get(b) instanceof XMLStructure)) {
/*  83 */         throw new ClassCastException("content[" + b + "] is not a valid type");
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  88 */     this.target = paramString1;
/*  89 */     this.id = paramString2;
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
/*     */   public DOMSignatureProperty(Element paramElement, XMLCryptoContext paramXMLCryptoContext) throws MarshalException {
/* 101 */     this.target = DOMUtils.getAttributeValue(paramElement, "Target");
/* 102 */     if (this.target == null) {
/* 103 */       throw new MarshalException("target cannot be null");
/*     */     }
/* 105 */     Attr attr = paramElement.getAttributeNodeNS((String)null, "Id");
/* 106 */     if (attr != null) {
/* 107 */       this.id = attr.getValue();
/* 108 */       paramElement.setIdAttributeNode(attr, true);
/*     */     } else {
/* 110 */       this.id = null;
/*     */     } 
/*     */     
/* 113 */     NodeList nodeList = paramElement.getChildNodes();
/* 114 */     int i = nodeList.getLength();
/* 115 */     ArrayList<DOMStructure> arrayList = new ArrayList(i);
/* 116 */     for (byte b = 0; b < i; b++) {
/* 117 */       arrayList.add(new DOMStructure(nodeList.item(b)));
/*     */     }
/* 119 */     if (arrayList.isEmpty()) {
/* 120 */       throw new MarshalException("content cannot be empty");
/*     */     }
/* 122 */     this.content = Collections.unmodifiableList((List)arrayList);
/*     */   }
/*     */ 
/*     */   
/*     */   public List getContent() {
/* 127 */     return this.content;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 131 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getTarget() {
/* 135 */     return this.target;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
/* 141 */     Document document = DOMUtils.getOwnerDocument(paramNode);
/* 142 */     Element element = DOMUtils.createElement(document, "SignatureProperty", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */ 
/*     */ 
/*     */     
/* 146 */     DOMUtils.setAttributeID(element, "Id", this.id);
/* 147 */     DOMUtils.setAttribute(element, "Target", this.target);
/*     */ 
/*     */     
/* 150 */     for (XMLStructure xMLStructure : this.content) {
/* 151 */       DOMUtils.appendChild(element, ((DOMStructure)xMLStructure)
/* 152 */           .getNode());
/*     */     }
/*     */     
/* 155 */     paramNode.appendChild(element);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 160 */     if (this == paramObject) {
/* 161 */       return true;
/*     */     }
/*     */     
/* 164 */     if (!(paramObject instanceof SignatureProperty)) {
/* 165 */       return false;
/*     */     }
/* 167 */     SignatureProperty signatureProperty = (SignatureProperty)paramObject;
/*     */ 
/*     */     
/* 170 */     boolean bool = (this.id == null) ? ((signatureProperty.getId() == null) ? true : false) : this.id.equals(signatureProperty.getId());
/*     */ 
/*     */     
/* 173 */     List<XMLStructure> list = signatureProperty.getContent();
/* 174 */     return (equalsContent(list) && this.target
/* 175 */       .equals(signatureProperty.getTarget()) && bool);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 180 */     int i = 17;
/* 181 */     if (this.id != null) {
/* 182 */       i = 31 * i + this.id.hashCode();
/*     */     }
/* 184 */     i = 31 * i + this.target.hashCode();
/* 185 */     i = 31 * i + this.content.hashCode();
/*     */     
/* 187 */     return i;
/*     */   }
/*     */   
/*     */   private boolean equalsContent(List<XMLStructure> paramList) {
/* 191 */     int i = paramList.size();
/* 192 */     if (this.content.size() != i) {
/* 193 */       return false;
/*     */     }
/* 195 */     for (byte b = 0; b < i; b++) {
/* 196 */       XMLStructure xMLStructure1 = paramList.get(b);
/* 197 */       XMLStructure xMLStructure2 = this.content.get(b);
/* 198 */       if (xMLStructure1 instanceof DOMStructure) {
/* 199 */         if (!(xMLStructure2 instanceof DOMStructure)) {
/* 200 */           return false;
/*     */         }
/* 202 */         Node node1 = ((DOMStructure)xMLStructure1).getNode();
/* 203 */         Node node2 = ((DOMStructure)xMLStructure2).getNode();
/* 204 */         if (!DOMUtils.nodesEqual(node2, node1)) {
/* 205 */           return false;
/*     */         }
/*     */       }
/* 208 */       else if (!xMLStructure2.equals(xMLStructure1)) {
/* 209 */         return false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 214 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMSignatureProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */