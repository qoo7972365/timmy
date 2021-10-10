/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import java.security.Provider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.XMLStructure;
/*     */ import javax.xml.crypto.dom.DOMCryptoContext;
/*     */ import javax.xml.crypto.dom.DOMStructure;
/*     */ import javax.xml.crypto.dsig.XMLObject;
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
/*     */ public final class DOMXMLObject
/*     */   extends DOMStructure
/*     */   implements XMLObject
/*     */ {
/*     */   private final String id;
/*     */   private final String mimeType;
/*     */   private final String encoding;
/*     */   private final List<XMLStructure> content;
/*     */   private Element objectElem;
/*     */   
/*     */   public DOMXMLObject(List<? extends XMLStructure> paramList, String paramString1, String paramString2, String paramString3) {
/*  73 */     if (paramList == null || paramList.isEmpty()) {
/*  74 */       this.content = Collections.emptyList();
/*     */     } else {
/*  76 */       this.content = Collections.unmodifiableList(new ArrayList<>(paramList)); byte b;
/*     */       int i;
/*  78 */       for (b = 0, i = this.content.size(); b < i; b++) {
/*  79 */         if (!(this.content.get(b) instanceof XMLStructure)) {
/*  80 */           throw new ClassCastException("content[" + b + "] is not a valid type");
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  85 */     this.id = paramString1;
/*  86 */     this.mimeType = paramString2;
/*  87 */     this.encoding = paramString3;
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
/*     */   public DOMXMLObject(Element paramElement, XMLCryptoContext paramXMLCryptoContext, Provider paramProvider) throws MarshalException {
/* 101 */     this.encoding = DOMUtils.getAttributeValue(paramElement, "Encoding");
/*     */     
/* 103 */     Attr attr = paramElement.getAttributeNodeNS((String)null, "Id");
/* 104 */     if (attr != null) {
/* 105 */       this.id = attr.getValue();
/* 106 */       paramElement.setIdAttributeNode(attr, true);
/*     */     } else {
/* 108 */       this.id = null;
/*     */     } 
/* 110 */     this.mimeType = DOMUtils.getAttributeValue(paramElement, "MimeType");
/*     */     
/* 112 */     NodeList nodeList = paramElement.getChildNodes();
/* 113 */     int i = nodeList.getLength();
/* 114 */     ArrayList<DOMManifest> arrayList = new ArrayList(i);
/* 115 */     for (byte b = 0; b < i; b++) {
/* 116 */       Node node = nodeList.item(b);
/* 117 */       if (node.getNodeType() == 1) {
/* 118 */         Element element = (Element)node;
/* 119 */         String str = element.getLocalName();
/* 120 */         if (str.equals("Manifest")) {
/* 121 */           arrayList.add(new DOMManifest(element, paramXMLCryptoContext, paramProvider)); continue;
/*     */         } 
/* 123 */         if (str.equals("SignatureProperties")) {
/* 124 */           arrayList.add(new DOMSignatureProperties(element, paramXMLCryptoContext)); continue;
/*     */         } 
/* 126 */         if (str.equals("X509Data")) {
/* 127 */           arrayList.add(new DOMX509Data(element));
/*     */           
/*     */           continue;
/*     */         } 
/*     */       } 
/* 132 */       arrayList.add(new DOMStructure(node)); continue;
/*     */     } 
/* 134 */     if (arrayList.isEmpty()) {
/* 135 */       this.content = Collections.emptyList();
/*     */     } else {
/* 137 */       this.content = Collections.unmodifiableList((List)arrayList);
/*     */     } 
/* 139 */     this.objectElem = paramElement;
/*     */   }
/*     */   
/*     */   public List getContent() {
/* 143 */     return this.content;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 147 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getMimeType() {
/* 151 */     return this.mimeType;
/*     */   }
/*     */   
/*     */   public String getEncoding() {
/* 155 */     return this.encoding;
/*     */   }
/*     */ 
/*     */   
/*     */   public void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
/* 160 */     Document document = DOMUtils.getOwnerDocument(paramNode);
/*     */     
/* 162 */     Element element = (this.objectElem != null) ? this.objectElem : null;
/* 163 */     if (element == null) {
/* 164 */       element = DOMUtils.createElement(document, "Object", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */ 
/*     */ 
/*     */       
/* 168 */       DOMUtils.setAttributeID(element, "Id", this.id);
/* 169 */       DOMUtils.setAttribute(element, "MimeType", this.mimeType);
/* 170 */       DOMUtils.setAttribute(element, "Encoding", this.encoding);
/*     */ 
/*     */       
/* 173 */       for (XMLStructure xMLStructure : this.content) {
/* 174 */         if (xMLStructure instanceof DOMStructure) {
/* 175 */           ((DOMStructure)xMLStructure).marshal(element, paramString, paramDOMCryptoContext); continue;
/*     */         } 
/* 177 */         DOMStructure dOMStructure = (DOMStructure)xMLStructure;
/*     */         
/* 179 */         DOMUtils.appendChild(element, dOMStructure.getNode());
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 184 */     paramNode.appendChild(element);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 189 */     if (this == paramObject) {
/* 190 */       return true;
/*     */     }
/*     */     
/* 193 */     if (!(paramObject instanceof XMLObject)) {
/* 194 */       return false;
/*     */     }
/* 196 */     XMLObject xMLObject = (XMLObject)paramObject;
/*     */ 
/*     */     
/* 199 */     boolean bool1 = (this.id == null) ? ((xMLObject.getId() == null) ? true : false) : this.id.equals(xMLObject.getId());
/*     */ 
/*     */     
/* 202 */     boolean bool2 = (this.encoding == null) ? ((xMLObject.getEncoding() == null) ? true : false) : this.encoding.equals(xMLObject.getEncoding());
/*     */ 
/*     */     
/* 205 */     boolean bool3 = (this.mimeType == null) ? ((xMLObject.getMimeType() == null) ? true : false) : this.mimeType.equals(xMLObject.getMimeType());
/*     */ 
/*     */     
/* 208 */     List<XMLStructure> list = xMLObject.getContent();
/* 209 */     return (bool1 && bool2 && bool3 && 
/* 210 */       equalsContent(list));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 215 */     int i = 17;
/* 216 */     if (this.id != null) {
/* 217 */       i = 31 * i + this.id.hashCode();
/*     */     }
/* 219 */     if (this.encoding != null) {
/* 220 */       i = 31 * i + this.encoding.hashCode();
/*     */     }
/* 222 */     if (this.mimeType != null) {
/* 223 */       i = 31 * i + this.mimeType.hashCode();
/*     */     }
/* 225 */     i = 31 * i + this.content.hashCode();
/*     */     
/* 227 */     return i;
/*     */   }
/*     */   
/*     */   private boolean equalsContent(List<XMLStructure> paramList) {
/* 231 */     if (this.content.size() != paramList.size())
/* 232 */       return false;  byte b;
/*     */     int i;
/* 234 */     for (b = 0, i = paramList.size(); b < i; b++) {
/* 235 */       XMLStructure xMLStructure1 = paramList.get(b);
/* 236 */       XMLStructure xMLStructure2 = this.content.get(b);
/* 237 */       if (xMLStructure1 instanceof DOMStructure) {
/* 238 */         if (!(xMLStructure2 instanceof DOMStructure)) {
/* 239 */           return false;
/*     */         }
/* 241 */         Node node1 = ((DOMStructure)xMLStructure1).getNode();
/* 242 */         Node node2 = ((DOMStructure)xMLStructure2).getNode();
/* 243 */         if (!DOMUtils.nodesEqual(node2, node1)) {
/* 244 */           return false;
/*     */         }
/*     */       }
/* 247 */       else if (!xMLStructure2.equals(xMLStructure1)) {
/* 248 */         return false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 253 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMXMLObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */