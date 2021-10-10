/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.security.Provider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.NodeSetData;
/*     */ import javax.xml.crypto.URIDereferencer;
/*     */ import javax.xml.crypto.URIReferenceException;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.XMLStructure;
/*     */ import javax.xml.crypto.dom.DOMCryptoContext;
/*     */ import javax.xml.crypto.dom.DOMURIReference;
/*     */ import javax.xml.crypto.dsig.Transform;
/*     */ import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DOMRetrievalMethod
/*     */   extends DOMStructure
/*     */   implements RetrievalMethod, DOMURIReference
/*     */ {
/*     */   private final List<Transform> transforms;
/*     */   private String uri;
/*     */   private String type;
/*     */   private Attr here;
/*     */   
/*     */   public DOMRetrievalMethod(String paramString1, String paramString2, List<? extends Transform> paramList) {
/*  90 */     if (paramString1 == null) {
/*  91 */       throw new NullPointerException("uri cannot be null");
/*     */     }
/*  93 */     if (paramList == null || paramList.isEmpty()) {
/*  94 */       this.transforms = Collections.emptyList();
/*     */     } else {
/*  96 */       this.transforms = Collections.unmodifiableList(new ArrayList<>(paramList)); byte b;
/*     */       int i;
/*  98 */       for (b = 0, i = this.transforms.size(); b < i; b++) {
/*  99 */         if (!(this.transforms.get(b) instanceof Transform)) {
/* 100 */           throw new ClassCastException("transforms[" + b + "] is not a valid type");
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 105 */     this.uri = paramString1;
/* 106 */     if (!paramString1.equals("")) {
/*     */       try {
/* 108 */         new URI(paramString1);
/* 109 */       } catch (URISyntaxException uRISyntaxException) {
/* 110 */         throw new IllegalArgumentException(uRISyntaxException.getMessage());
/*     */       } 
/*     */     }
/*     */     
/* 114 */     this.type = paramString2;
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
/*     */   public DOMRetrievalMethod(Element paramElement, XMLCryptoContext paramXMLCryptoContext, Provider paramProvider) throws MarshalException {
/* 127 */     this.uri = DOMUtils.getAttributeValue(paramElement, "URI");
/* 128 */     this.type = DOMUtils.getAttributeValue(paramElement, "Type");
/*     */ 
/*     */     
/* 131 */     this.here = paramElement.getAttributeNodeNS((String)null, "URI");
/*     */     
/* 133 */     boolean bool = Utils.secureValidation(paramXMLCryptoContext);
/*     */ 
/*     */     
/* 136 */     ArrayList<DOMTransform> arrayList = new ArrayList();
/* 137 */     Element element = DOMUtils.getFirstChildElement(paramElement);
/*     */     
/* 139 */     if (element != null) {
/* 140 */       String str = element.getLocalName();
/* 141 */       if (!str.equals("Transforms")) {
/* 142 */         throw new MarshalException("Invalid element name: " + str + ", expected Transforms");
/*     */       }
/*     */ 
/*     */       
/* 146 */       Element element1 = DOMUtils.getFirstChildElement(element, "Transform");
/* 147 */       arrayList.add(new DOMTransform(element1, paramXMLCryptoContext, paramProvider));
/* 148 */       element1 = DOMUtils.getNextSiblingElement(element1);
/* 149 */       while (element1 != null) {
/* 150 */         String str1 = element1.getLocalName();
/* 151 */         if (!str1.equals("Transform")) {
/* 152 */           throw new MarshalException("Invalid element name: " + str1 + ", expected Transform");
/*     */         }
/*     */         
/* 155 */         arrayList
/* 156 */           .add(new DOMTransform(element1, paramXMLCryptoContext, paramProvider));
/* 157 */         if (bool && Policy.restrictNumTransforms(arrayList.size())) {
/* 158 */           String str2 = "A maximum of " + Policy.maxTransforms() + " transforms per Reference are allowed when secure validation is enabled";
/*     */ 
/*     */           
/* 161 */           throw new MarshalException(str2);
/*     */         } 
/* 163 */         element1 = DOMUtils.getNextSiblingElement(element1);
/*     */       } 
/*     */     } 
/* 166 */     if (arrayList.isEmpty()) {
/* 167 */       this.transforms = Collections.emptyList();
/*     */     } else {
/* 169 */       this.transforms = Collections.unmodifiableList((List)arrayList);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getURI() {
/* 174 */     return this.uri;
/*     */   }
/*     */   
/*     */   public String getType() {
/* 178 */     return this.type;
/*     */   }
/*     */   
/*     */   public List getTransforms() {
/* 182 */     return this.transforms;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
/* 188 */     Document document = DOMUtils.getOwnerDocument(paramNode);
/* 189 */     Element element = DOMUtils.createElement(document, "RetrievalMethod", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */ 
/*     */ 
/*     */     
/* 193 */     DOMUtils.setAttribute(element, "URI", this.uri);
/* 194 */     DOMUtils.setAttribute(element, "Type", this.type);
/*     */ 
/*     */     
/* 197 */     if (!this.transforms.isEmpty()) {
/* 198 */       Element element1 = DOMUtils.createElement(document, "Transforms", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */ 
/*     */ 
/*     */       
/* 202 */       element.appendChild(element1);
/* 203 */       for (Transform transform : this.transforms) {
/* 204 */         ((DOMTransform)transform).marshal(element1, paramString, paramDOMCryptoContext);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 209 */     paramNode.appendChild(element);
/*     */ 
/*     */     
/* 212 */     this.here = element.getAttributeNodeNS((String)null, "URI");
/*     */   }
/*     */   
/*     */   public Node getHere() {
/* 216 */     return this.here;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Data dereference(XMLCryptoContext paramXMLCryptoContext) throws URIReferenceException {
/* 222 */     if (paramXMLCryptoContext == null) {
/* 223 */       throw new NullPointerException("context cannot be null");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 230 */     URIDereferencer uRIDereferencer = paramXMLCryptoContext.getURIDereferencer();
/* 231 */     if (uRIDereferencer == null) {
/* 232 */       uRIDereferencer = DOMURIDereferencer.INSTANCE;
/*     */     }
/*     */     
/* 235 */     Data data = uRIDereferencer.dereference(this, paramXMLCryptoContext);
/*     */ 
/*     */     
/*     */     try {
/* 239 */       for (Transform transform : this.transforms) {
/* 240 */         data = ((DOMTransform)transform).transform(data, paramXMLCryptoContext);
/*     */       }
/* 242 */     } catch (Exception exception) {
/* 243 */       throw new URIReferenceException(exception);
/*     */     } 
/*     */ 
/*     */     
/* 247 */     if (data instanceof NodeSetData && Utils.secureValidation(paramXMLCryptoContext) && 
/* 248 */       Policy.restrictRetrievalMethodLoops()) {
/* 249 */       NodeSetData nodeSetData = (NodeSetData)data;
/* 250 */       Iterator<Node> iterator = nodeSetData.iterator();
/* 251 */       if (iterator.hasNext()) {
/* 252 */         Node node = iterator.next();
/* 253 */         if ("RetrievalMethod".equals(node.getLocalName())) {
/* 254 */           throw new URIReferenceException("It is forbidden to have one RetrievalMethod point to another when secure validation is enabled");
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 261 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStructure dereferenceAsXMLStructure(XMLCryptoContext paramXMLCryptoContext) throws URIReferenceException {
/*     */     try {
/* 268 */       ApacheData apacheData = (ApacheData)dereference(paramXMLCryptoContext);
/* 269 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 270 */       documentBuilderFactory.setNamespaceAware(true);
/* 271 */       documentBuilderFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE.booleanValue());
/* 272 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 273 */       Document document = documentBuilder.parse(new ByteArrayInputStream(apacheData
/* 274 */             .getXMLSignatureInput().getBytes()));
/* 275 */       Element element = document.getDocumentElement();
/* 276 */       if (element.getLocalName().equals("X509Data")) {
/* 277 */         return new DOMX509Data(element);
/*     */       }
/* 279 */       return null;
/*     */     }
/* 281 */     catch (Exception exception) {
/* 282 */       throw new URIReferenceException(exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 288 */     if (this == paramObject) {
/* 289 */       return true;
/*     */     }
/* 291 */     if (!(paramObject instanceof RetrievalMethod)) {
/* 292 */       return false;
/*     */     }
/* 294 */     RetrievalMethod retrievalMethod = (RetrievalMethod)paramObject;
/*     */ 
/*     */     
/* 297 */     boolean bool = (this.type == null) ? ((retrievalMethod.getType() == null) ? true : false) : this.type.equals(retrievalMethod.getType());
/*     */     
/* 299 */     return (this.uri.equals(retrievalMethod.getURI()) && this.transforms
/* 300 */       .equals(retrievalMethod.getTransforms()) && bool);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 305 */     int i = 17;
/* 306 */     if (this.type != null) {
/* 307 */       i = 31 * i + this.type.hashCode();
/*     */     }
/* 309 */     i = 31 * i + this.uri.hashCode();
/* 310 */     i = 31 * i + this.transforms.hashCode();
/*     */     
/* 312 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMRetrievalMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */