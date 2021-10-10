/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.Base64;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.security.cert.CRLException;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.X509CRL;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.dom.DOMCryptoContext;
/*     */ import javax.xml.crypto.dom.DOMStructure;
/*     */ import javax.xml.crypto.dsig.keyinfo.X509Data;
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
/*     */ public final class DOMX509Data
/*     */   extends DOMStructure
/*     */   implements X509Data
/*     */ {
/*     */   private final List<Object> content;
/*     */   private CertificateFactory cf;
/*     */   
/*     */   public DOMX509Data(List<?> paramList) {
/*  75 */     if (paramList == null) {
/*  76 */       throw new NullPointerException("content cannot be null");
/*     */     }
/*  78 */     ArrayList<Object> arrayList = new ArrayList(paramList);
/*  79 */     if (arrayList.isEmpty())
/*  80 */       throw new IllegalArgumentException("content cannot be empty");  byte b;
/*     */     int i;
/*  82 */     for (b = 0, i = arrayList.size(); b < i; b++) {
/*  83 */       String str = (String)arrayList.get(b);
/*  84 */       if (str instanceof String) {
/*  85 */         new X500Principal(str);
/*  86 */       } else if (!(str instanceof byte[]) && !(str instanceof X509Certificate) && !(str instanceof X509CRL) && !(str instanceof javax.xml.crypto.XMLStructure)) {
/*     */ 
/*     */ 
/*     */         
/*  90 */         throw new ClassCastException("content[" + b + "] is not a valid X509Data type");
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     this.content = Collections.unmodifiableList(arrayList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DOMX509Data(Element paramElement) throws MarshalException {
/* 105 */     NodeList nodeList = paramElement.getChildNodes();
/* 106 */     int i = nodeList.getLength();
/* 107 */     ArrayList<X509Certificate> arrayList = new ArrayList(i);
/* 108 */     for (byte b = 0; b < i; b++) {
/* 109 */       Node node = nodeList.item(b);
/*     */       
/* 111 */       if (node.getNodeType() == 1) {
/*     */ 
/*     */ 
/*     */         
/* 115 */         Element element = (Element)node;
/* 116 */         String str = element.getLocalName();
/* 117 */         if (str.equals("X509Certificate")) {
/* 118 */           arrayList.add(unmarshalX509Certificate(element));
/* 119 */         } else if (str.equals("X509IssuerSerial")) {
/* 120 */           arrayList.add(new DOMX509IssuerSerial(element));
/* 121 */         } else if (str.equals("X509SubjectName")) {
/* 122 */           arrayList.add(element.getFirstChild().getNodeValue());
/* 123 */         } else if (str.equals("X509SKI")) {
/*     */           try {
/* 125 */             arrayList.add(Base64.decode(element));
/* 126 */           } catch (Base64DecodingException base64DecodingException) {
/* 127 */             throw new MarshalException("cannot decode X509SKI", base64DecodingException);
/*     */           } 
/* 129 */         } else if (str.equals("X509CRL")) {
/* 130 */           arrayList.add(unmarshalX509CRL(element));
/*     */         } else {
/* 132 */           arrayList.add(new DOMStructure(element));
/*     */         } 
/*     */       } 
/* 135 */     }  this.content = Collections.unmodifiableList(arrayList);
/*     */   }
/*     */   
/*     */   public List getContent() {
/* 139 */     return this.content;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
/* 145 */     Document document = DOMUtils.getOwnerDocument(paramNode);
/* 146 */     Element element = DOMUtils.createElement(document, "X509Data", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */     
/*     */     byte b;
/*     */     int i;
/* 150 */     for (b = 0, i = this.content.size(); b < i; b++) {
/* 151 */       Object object = this.content.get(b);
/* 152 */       if (object instanceof X509Certificate) {
/* 153 */         marshalCert((X509Certificate)object, element, document, paramString);
/* 154 */       } else if (object instanceof javax.xml.crypto.XMLStructure) {
/* 155 */         if (object instanceof javax.xml.crypto.dsig.keyinfo.X509IssuerSerial) {
/* 156 */           ((DOMX509IssuerSerial)object)
/* 157 */             .marshal(element, paramString, paramDOMCryptoContext);
/*     */         } else {
/* 159 */           DOMStructure dOMStructure = (DOMStructure)object;
/*     */           
/* 161 */           DOMUtils.appendChild(element, dOMStructure.getNode());
/*     */         } 
/* 163 */       } else if (object instanceof byte[]) {
/* 164 */         marshalSKI((byte[])object, element, document, paramString);
/* 165 */       } else if (object instanceof String) {
/* 166 */         marshalSubjectName((String)object, element, document, paramString);
/* 167 */       } else if (object instanceof X509CRL) {
/* 168 */         marshalCRL((X509CRL)object, element, document, paramString);
/*     */       } 
/*     */     } 
/*     */     
/* 172 */     paramNode.appendChild(element);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void marshalSKI(byte[] paramArrayOfbyte, Node paramNode, Document paramDocument, String paramString) {
/* 178 */     Element element = DOMUtils.createElement(paramDocument, "X509SKI", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */     
/* 180 */     element.appendChild(paramDocument.createTextNode(Base64.encode(paramArrayOfbyte)));
/* 181 */     paramNode.appendChild(element);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void marshalSubjectName(String paramString1, Node paramNode, Document paramDocument, String paramString2) {
/* 187 */     Element element = DOMUtils.createElement(paramDocument, "X509SubjectName", "http://www.w3.org/2000/09/xmldsig#", paramString2);
/*     */     
/* 189 */     element.appendChild(paramDocument.createTextNode(paramString1));
/* 190 */     paramNode.appendChild(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void marshalCert(X509Certificate paramX509Certificate, Node paramNode, Document paramDocument, String paramString) throws MarshalException {
/* 197 */     Element element = DOMUtils.createElement(paramDocument, "X509Certificate", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */     
/*     */     try {
/* 200 */       element.appendChild(paramDocument
/* 201 */           .createTextNode(Base64.encode(paramX509Certificate.getEncoded())));
/* 202 */     } catch (CertificateEncodingException certificateEncodingException) {
/* 203 */       throw new MarshalException("Error encoding X509Certificate", certificateEncodingException);
/*     */     } 
/* 205 */     paramNode.appendChild(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void marshalCRL(X509CRL paramX509CRL, Node paramNode, Document paramDocument, String paramString) throws MarshalException {
/* 212 */     Element element = DOMUtils.createElement(paramDocument, "X509CRL", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */     
/*     */     try {
/* 215 */       element.appendChild(paramDocument
/* 216 */           .createTextNode(Base64.encode(paramX509CRL.getEncoded())));
/* 217 */     } catch (CRLException cRLException) {
/* 218 */       throw new MarshalException("Error encoding X509CRL", cRLException);
/*     */     } 
/* 220 */     paramNode.appendChild(element);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private X509Certificate unmarshalX509Certificate(Element paramElement) throws MarshalException {
/*     */     try {
/* 227 */       ByteArrayInputStream byteArrayInputStream = unmarshalBase64Binary(paramElement);
/* 228 */       return (X509Certificate)this.cf.generateCertificate(byteArrayInputStream);
/* 229 */     } catch (CertificateException certificateException) {
/* 230 */       throw new MarshalException("Cannot create X509Certificate", certificateException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private X509CRL unmarshalX509CRL(Element paramElement) throws MarshalException {
/*     */     try {
/* 236 */       ByteArrayInputStream byteArrayInputStream = unmarshalBase64Binary(paramElement);
/* 237 */       return (X509CRL)this.cf.generateCRL(byteArrayInputStream);
/* 238 */     } catch (CRLException cRLException) {
/* 239 */       throw new MarshalException("Cannot create X509CRL", cRLException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ByteArrayInputStream unmarshalBase64Binary(Element paramElement) throws MarshalException {
/*     */     try {
/* 246 */       if (this.cf == null) {
/* 247 */         this.cf = CertificateFactory.getInstance("X.509");
/*     */       }
/* 249 */       return new ByteArrayInputStream(Base64.decode(paramElement));
/* 250 */     } catch (CertificateException certificateException) {
/* 251 */       throw new MarshalException("Cannot create CertificateFactory", certificateException);
/* 252 */     } catch (Base64DecodingException base64DecodingException) {
/* 253 */       throw new MarshalException("Cannot decode Base64-encoded val", base64DecodingException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 259 */     if (this == paramObject) {
/* 260 */       return true;
/*     */     }
/*     */     
/* 263 */     if (!(paramObject instanceof X509Data)) {
/* 264 */       return false;
/*     */     }
/* 266 */     X509Data x509Data = (X509Data)paramObject;
/*     */     
/* 268 */     List<?> list = x509Data.getContent();
/* 269 */     int i = this.content.size();
/* 270 */     if (i != list.size()) {
/* 271 */       return false;
/*     */     }
/*     */     
/* 274 */     for (byte b = 0; b < i; b++) {
/* 275 */       Object object1 = this.content.get(b);
/* 276 */       Object object2 = list.get(b);
/* 277 */       if (object1 instanceof byte[]) {
/* 278 */         if (!(object2 instanceof byte[]) || 
/* 279 */           !Arrays.equals((byte[])object1, (byte[])object2)) {
/* 280 */           return false;
/*     */         }
/*     */       }
/* 283 */       else if (!object1.equals(object2)) {
/* 284 */         return false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 289 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 294 */     int i = 17;
/* 295 */     i = 31 * i + this.content.hashCode();
/*     */     
/* 297 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMX509Data.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */