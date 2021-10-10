/*     */ package com.sun.org.apache.xml.internal.security.signature;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
/*     */ import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
/*     */ import com.sun.org.apache.xml.internal.security.c14n.Canonicalizer;
/*     */ import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.params.InclusiveNamespaces;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SignedInfo
/*     */   extends Manifest
/*     */ {
/*  56 */   private SignatureAlgorithm signatureAlgorithm = null;
/*     */ 
/*     */   
/*  59 */   private byte[] c14nizedBytes = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private Element c14nMethod;
/*     */ 
/*     */ 
/*     */   
/*     */   private Element signatureMethod;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignedInfo(Document paramDocument) throws XMLSecurityException {
/*  73 */     this(paramDocument, "http://www.w3.org/2000/09/xmldsig#dsa-sha1", "http://www.w3.org/TR/2001/REC-xml-c14n-20010315");
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
/*     */ 
/*     */   
/*     */   public SignedInfo(Document paramDocument, String paramString1, String paramString2) throws XMLSecurityException {
/*  91 */     this(paramDocument, paramString1, 0, paramString2);
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
/*     */ 
/*     */   
/*     */   public SignedInfo(Document paramDocument, String paramString1, int paramInt, String paramString2) throws XMLSecurityException {
/* 109 */     super(paramDocument);
/*     */     
/* 111 */     this
/* 112 */       .c14nMethod = XMLUtils.createElementInSignatureSpace(this.doc, "CanonicalizationMethod");
/*     */     
/* 114 */     this.c14nMethod.setAttributeNS((String)null, "Algorithm", paramString2);
/* 115 */     this.constructionElement.appendChild(this.c14nMethod);
/* 116 */     XMLUtils.addReturnToElement(this.constructionElement);
/*     */     
/* 118 */     if (paramInt > 0) {
/* 119 */       this.signatureAlgorithm = new SignatureAlgorithm(this.doc, paramString1, paramInt);
/*     */     } else {
/*     */       
/* 122 */       this.signatureAlgorithm = new SignatureAlgorithm(this.doc, paramString1);
/*     */     } 
/*     */     
/* 125 */     this.signatureMethod = this.signatureAlgorithm.getElement();
/* 126 */     this.constructionElement.appendChild(this.signatureMethod);
/* 127 */     XMLUtils.addReturnToElement(this.constructionElement);
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
/*     */   public SignedInfo(Document paramDocument, Element paramElement1, Element paramElement2) throws XMLSecurityException {
/* 139 */     super(paramDocument);
/*     */     
/* 141 */     this.c14nMethod = paramElement2;
/* 142 */     this.constructionElement.appendChild(this.c14nMethod);
/* 143 */     XMLUtils.addReturnToElement(this.constructionElement);
/*     */     
/* 145 */     this.signatureAlgorithm = new SignatureAlgorithm(paramElement1, null);
/*     */ 
/*     */     
/* 148 */     this.signatureMethod = this.signatureAlgorithm.getElement();
/* 149 */     this.constructionElement.appendChild(this.signatureMethod);
/*     */     
/* 151 */     XMLUtils.addReturnToElement(this.constructionElement);
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
/*     */   public SignedInfo(Element paramElement, String paramString) throws XMLSecurityException {
/* 166 */     this(paramElement, paramString, false);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public SignedInfo(Element paramElement, String paramString, boolean paramBoolean) throws XMLSecurityException {
/* 185 */     super(reparseSignedInfoElem(paramElement), paramString, paramBoolean);
/*     */     
/* 187 */     this.c14nMethod = XMLUtils.getNextElement(paramElement.getFirstChild());
/* 188 */     this.signatureMethod = XMLUtils.getNextElement(this.c14nMethod.getNextSibling());
/* 189 */     this
/* 190 */       .signatureAlgorithm = new SignatureAlgorithm(this.signatureMethod, getBaseURI(), paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Element reparseSignedInfoElem(Element paramElement) throws XMLSecurityException {
/* 201 */     Element element = XMLUtils.getNextElement(paramElement.getFirstChild());
/*     */     
/* 203 */     String str = element.getAttributeNS((String)null, "Algorithm");
/* 204 */     if (!str.equals("http://www.w3.org/TR/2001/REC-xml-c14n-20010315") && 
/* 205 */       !str.equals("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments") && 
/* 206 */       !str.equals("http://www.w3.org/2001/10/xml-exc-c14n#") && 
/* 207 */       !str.equals("http://www.w3.org/2001/10/xml-exc-c14n#WithComments") && 
/* 208 */       !str.equals("http://www.w3.org/2006/12/xml-c14n11") && 
/* 209 */       !str.equals("http://www.w3.org/2006/12/xml-c14n11#WithComments")) {
/*     */       
/*     */       try {
/*     */ 
/*     */         
/* 214 */         Canonicalizer canonicalizer = Canonicalizer.getInstance(str);
/*     */         
/* 216 */         byte[] arrayOfByte = canonicalizer.canonicalizeSubtree(paramElement);
/*     */         
/* 218 */         DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 219 */         documentBuilderFactory.setNamespaceAware(true);
/* 220 */         documentBuilderFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE.booleanValue());
/* 221 */         DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*     */         
/* 223 */         Document document = documentBuilder.parse(new ByteArrayInputStream(arrayOfByte));
/*     */         
/* 225 */         Node node = paramElement.getOwnerDocument().importNode(document.getDocumentElement(), true);
/*     */         
/* 227 */         paramElement.getParentNode().replaceChild(node, paramElement);
/*     */         
/* 229 */         return (Element)node;
/* 230 */       } catch (ParserConfigurationException parserConfigurationException) {
/* 231 */         throw new XMLSecurityException("empty", parserConfigurationException);
/* 232 */       } catch (IOException iOException) {
/* 233 */         throw new XMLSecurityException("empty", iOException);
/* 234 */       } catch (SAXException sAXException) {
/* 235 */         throw new XMLSecurityException("empty", sAXException);
/*     */       } 
/*     */     }
/* 238 */     return paramElement;
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
/*     */   public boolean verify() throws MissingResourceFailureException, XMLSecurityException {
/* 250 */     return verifyReferences(false);
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
/*     */   public boolean verify(boolean paramBoolean) throws MissingResourceFailureException, XMLSecurityException {
/* 263 */     return verifyReferences(paramBoolean);
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
/*     */   public byte[] getCanonicalizedOctetStream() throws CanonicalizationException, InvalidCanonicalizerException, XMLSecurityException {
/* 276 */     if (this.c14nizedBytes == null) {
/*     */       
/* 278 */       Canonicalizer canonicalizer = Canonicalizer.getInstance(getCanonicalizationMethodURI());
/*     */       
/* 280 */       this
/* 281 */         .c14nizedBytes = canonicalizer.canonicalizeSubtree(this.constructionElement);
/*     */     } 
/*     */ 
/*     */     
/* 285 */     return (byte[])this.c14nizedBytes.clone();
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
/*     */   public void signInOctetStream(OutputStream paramOutputStream) throws CanonicalizationException, InvalidCanonicalizerException, XMLSecurityException {
/* 297 */     if (this.c14nizedBytes == null) {
/*     */       
/* 299 */       Canonicalizer canonicalizer = Canonicalizer.getInstance(getCanonicalizationMethodURI());
/* 300 */       canonicalizer.setWriter(paramOutputStream);
/* 301 */       String str = getInclusiveNamespaces();
/*     */       
/* 303 */       if (str == null) {
/* 304 */         canonicalizer.canonicalizeSubtree(this.constructionElement);
/*     */       } else {
/* 306 */         canonicalizer.canonicalizeSubtree(this.constructionElement, str);
/*     */       } 
/*     */     } else {
/*     */       try {
/* 310 */         paramOutputStream.write(this.c14nizedBytes);
/* 311 */       } catch (IOException iOException) {
/* 312 */         throw new RuntimeException(iOException);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCanonicalizationMethodURI() {
/* 323 */     return this.c14nMethod.getAttributeNS((String)null, "Algorithm");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSignatureMethodURI() {
/* 332 */     Element element = getSignatureMethodElement();
/*     */     
/* 334 */     if (element != null) {
/* 335 */       return element.getAttributeNS((String)null, "Algorithm");
/*     */     }
/*     */     
/* 338 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element getSignatureMethodElement() {
/* 347 */     return this.signatureMethod;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecretKey createSecretKey(byte[] paramArrayOfbyte) {
/* 358 */     return new SecretKeySpec(paramArrayOfbyte, this.signatureAlgorithm.getJCEAlgorithmString());
/*     */   }
/*     */   
/*     */   protected SignatureAlgorithm getSignatureAlgorithm() {
/* 362 */     return this.signatureAlgorithm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseLocalName() {
/* 371 */     return "SignedInfo";
/*     */   }
/*     */   
/*     */   public String getInclusiveNamespaces() {
/* 375 */     String str = this.c14nMethod.getAttributeNS((String)null, "Algorithm");
/* 376 */     if (!str.equals("http://www.w3.org/2001/10/xml-exc-c14n#") && 
/* 377 */       !str.equals("http://www.w3.org/2001/10/xml-exc-c14n#WithComments")) {
/* 378 */       return null;
/*     */     }
/*     */     
/* 381 */     Element element = XMLUtils.getNextElement(this.c14nMethod.getFirstChild());
/*     */     
/* 383 */     if (element != null) {
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */         
/* 389 */         return (new InclusiveNamespaces(element, "http://www.w3.org/2001/10/xml-exc-c14n#")).getInclusiveNamespaces();
/*     */       }
/* 391 */       catch (XMLSecurityException xMLSecurityException) {
/* 392 */         return null;
/*     */       } 
/*     */     }
/* 395 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/signature/SignedInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */