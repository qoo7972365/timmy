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
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyInfo;
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
/*     */ public final class DOMKeyInfo
/*     */   extends DOMStructure
/*     */   implements KeyInfo
/*     */ {
/*     */   private final String id;
/*     */   private final List<XMLStructure> keyInfoTypes;
/*     */   
/*     */   public DOMKeyInfo(List<? extends XMLStructure> paramList, String paramString) {
/*  68 */     if (paramList == null) {
/*  69 */       throw new NullPointerException("content cannot be null");
/*     */     }
/*  71 */     this
/*  72 */       .keyInfoTypes = Collections.unmodifiableList(new ArrayList<>(paramList));
/*  73 */     if (this.keyInfoTypes.isEmpty())
/*  74 */       throw new IllegalArgumentException("content cannot be empty");  byte b;
/*     */     int i;
/*  76 */     for (b = 0, i = this.keyInfoTypes.size(); b < i; b++) {
/*  77 */       if (!(this.keyInfoTypes.get(b) instanceof XMLStructure)) {
/*  78 */         throw new ClassCastException("content[" + b + "] is not a valid KeyInfo type");
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
/*     */   
/*     */   public DOMKeyInfo(Element paramElement, XMLCryptoContext paramXMLCryptoContext, Provider paramProvider) throws MarshalException {
/*  95 */     Attr attr = paramElement.getAttributeNodeNS((String)null, "Id");
/*  96 */     if (attr != null) {
/*  97 */       this.id = attr.getValue();
/*  98 */       paramElement.setIdAttributeNode(attr, true);
/*     */     } else {
/* 100 */       this.id = null;
/*     */     } 
/*     */ 
/*     */     
/* 104 */     NodeList nodeList = paramElement.getChildNodes();
/* 105 */     int i = nodeList.getLength();
/* 106 */     if (i < 1) {
/* 107 */       throw new MarshalException("KeyInfo must contain at least one type");
/*     */     }
/*     */     
/* 110 */     ArrayList<DOMX509Data> arrayList = new ArrayList(i);
/* 111 */     for (byte b = 0; b < i; b++) {
/* 112 */       Node node = nodeList.item(b);
/*     */       
/* 114 */       if (node.getNodeType() == 1) {
/*     */ 
/*     */         
/* 117 */         Element element = (Element)node;
/* 118 */         String str = element.getLocalName();
/* 119 */         if (str.equals("X509Data")) {
/* 120 */           arrayList.add(new DOMX509Data(element));
/* 121 */         } else if (str.equals("KeyName")) {
/* 122 */           arrayList.add(new DOMKeyName(element));
/* 123 */         } else if (str.equals("KeyValue")) {
/* 124 */           arrayList.add(DOMKeyValue.unmarshal(element));
/* 125 */         } else if (str.equals("RetrievalMethod")) {
/* 126 */           arrayList.add(new DOMRetrievalMethod(element, paramXMLCryptoContext, paramProvider));
/*     */         }
/* 128 */         else if (str.equals("PGPData")) {
/* 129 */           arrayList.add(new DOMPGPData(element));
/*     */         } else {
/* 131 */           arrayList.add(new DOMStructure(element));
/*     */         } 
/*     */       } 
/* 134 */     }  this.keyInfoTypes = Collections.unmodifiableList((List)arrayList);
/*     */   }
/*     */   
/*     */   public String getId() {
/* 138 */     return this.id;
/*     */   }
/*     */   
/*     */   public List getContent() {
/* 142 */     return this.keyInfoTypes;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(XMLStructure paramXMLStructure, XMLCryptoContext paramXMLCryptoContext) throws MarshalException {
/* 148 */     if (paramXMLStructure == null) {
/* 149 */       throw new NullPointerException("parent is null");
/*     */     }
/* 151 */     if (!(paramXMLStructure instanceof DOMStructure)) {
/* 152 */       throw new ClassCastException("parent must be of type DOMStructure");
/*     */     }
/*     */     
/* 155 */     Node node = ((DOMStructure)paramXMLStructure).getNode();
/* 156 */     String str = DOMUtils.getSignaturePrefix(paramXMLCryptoContext);
/*     */     
/* 158 */     Element element = DOMUtils.createElement(DOMUtils.getOwnerDocument(node), "KeyInfo", "http://www.w3.org/2000/09/xmldsig#", str);
/*     */     
/* 160 */     if (str == null || str.length() == 0) {
/* 161 */       element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "http://www.w3.org/2000/09/xmldsig#");
/*     */     } else {
/*     */       
/* 164 */       element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + str, "http://www.w3.org/2000/09/xmldsig#");
/*     */     } 
/*     */     
/* 167 */     marshal(node, element, null, str, (DOMCryptoContext)paramXMLCryptoContext);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
/* 174 */     marshal(paramNode, null, paramString, paramDOMCryptoContext);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(Node paramNode1, Node paramNode2, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
/* 181 */     Document document = DOMUtils.getOwnerDocument(paramNode1);
/* 182 */     Element element = DOMUtils.createElement(document, "KeyInfo", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */     
/* 184 */     marshal(paramNode1, element, paramNode2, paramString, paramDOMCryptoContext);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void marshal(Node paramNode1, Element paramElement, Node paramNode2, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
/* 192 */     for (XMLStructure xMLStructure : this.keyInfoTypes) {
/* 193 */       if (xMLStructure instanceof DOMStructure) {
/* 194 */         ((DOMStructure)xMLStructure).marshal(paramElement, paramString, paramDOMCryptoContext); continue;
/*     */       } 
/* 196 */       DOMUtils.appendChild(paramElement, ((DOMStructure)xMLStructure)
/* 197 */           .getNode());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 202 */     DOMUtils.setAttributeID(paramElement, "Id", this.id);
/*     */     
/* 204 */     paramNode1.insertBefore(paramElement, paramNode2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 209 */     if (this == paramObject) {
/* 210 */       return true;
/*     */     }
/*     */     
/* 213 */     if (!(paramObject instanceof KeyInfo)) {
/* 214 */       return false;
/*     */     }
/* 216 */     KeyInfo keyInfo = (KeyInfo)paramObject;
/*     */ 
/*     */     
/* 219 */     boolean bool = (this.id == null) ? ((keyInfo.getId() == null) ? true : false) : this.id.equals(keyInfo.getId());
/*     */     
/* 221 */     return (this.keyInfoTypes.equals(keyInfo.getContent()) && bool);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 226 */     int i = 17;
/* 227 */     if (this.id != null) {
/* 228 */       i = 31 * i + this.id.hashCode();
/*     */     }
/* 230 */     i = 31 * i + this.keyInfoTypes.hashCode();
/*     */     
/* 232 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMKeyInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */