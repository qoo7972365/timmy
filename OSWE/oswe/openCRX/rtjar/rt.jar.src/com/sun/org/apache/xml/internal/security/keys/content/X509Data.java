/*     */ package com.sun.org.apache.xml.internal.security.keys.content;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509CRL;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509Certificate;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509Digest;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509IssuerSerial;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509SKI;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509SubjectName;
/*     */ import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import java.math.BigInteger;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class X509Data
/*     */   extends SignatureElementProxy
/*     */   implements KeyInfoContent
/*     */ {
/*  46 */   private static Logger log = Logger.getLogger(X509Data.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509Data(Document paramDocument) {
/*  54 */     super(paramDocument);
/*     */     
/*  56 */     XMLUtils.addReturnToElement(this.constructionElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509Data(Element paramElement, String paramString) throws XMLSecurityException {
/*  67 */     super(paramElement, paramString);
/*     */     
/*  69 */     Node node = this.constructionElement.getFirstChild();
/*  70 */     while (node != null) {
/*  71 */       if (node.getNodeType() != 1) {
/*  72 */         node = node.getNextSibling();
/*     */         
/*     */         continue;
/*     */       } 
/*     */       return;
/*     */     } 
/*  78 */     Object[] arrayOfObject = { "Elements", "X509Data" };
/*  79 */     throw new XMLSecurityException("xml.WrongContent", arrayOfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addIssuerSerial(String paramString, BigInteger paramBigInteger) {
/*  89 */     add(new XMLX509IssuerSerial(this.doc, paramString, paramBigInteger));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addIssuerSerial(String paramString1, String paramString2) {
/*  99 */     add(new XMLX509IssuerSerial(this.doc, paramString1, paramString2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addIssuerSerial(String paramString, int paramInt) {
/* 109 */     add(new XMLX509IssuerSerial(this.doc, paramString, paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(XMLX509IssuerSerial paramXMLX509IssuerSerial) {
/* 119 */     this.constructionElement.appendChild(paramXMLX509IssuerSerial.getElement());
/* 120 */     XMLUtils.addReturnToElement(this.constructionElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSKI(byte[] paramArrayOfbyte) {
/* 129 */     add(new XMLX509SKI(this.doc, paramArrayOfbyte));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSKI(X509Certificate paramX509Certificate) throws XMLSecurityException {
/* 140 */     add(new XMLX509SKI(this.doc, paramX509Certificate));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(XMLX509SKI paramXMLX509SKI) {
/* 149 */     this.constructionElement.appendChild(paramXMLX509SKI.getElement());
/* 150 */     XMLUtils.addReturnToElement(this.constructionElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSubjectName(String paramString) {
/* 159 */     add(new XMLX509SubjectName(this.doc, paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSubjectName(X509Certificate paramX509Certificate) {
/* 168 */     add(new XMLX509SubjectName(this.doc, paramX509Certificate));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(XMLX509SubjectName paramXMLX509SubjectName) {
/* 177 */     this.constructionElement.appendChild(paramXMLX509SubjectName.getElement());
/* 178 */     XMLUtils.addReturnToElement(this.constructionElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCertificate(X509Certificate paramX509Certificate) throws XMLSecurityException {
/* 189 */     add(new XMLX509Certificate(this.doc, paramX509Certificate));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCertificate(byte[] paramArrayOfbyte) {
/* 198 */     add(new XMLX509Certificate(this.doc, paramArrayOfbyte));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(XMLX509Certificate paramXMLX509Certificate) {
/* 207 */     this.constructionElement.appendChild(paramXMLX509Certificate.getElement());
/* 208 */     XMLUtils.addReturnToElement(this.constructionElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCRL(byte[] paramArrayOfbyte) {
/* 217 */     add(new XMLX509CRL(this.doc, paramArrayOfbyte));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(XMLX509CRL paramXMLX509CRL) {
/* 226 */     this.constructionElement.appendChild(paramXMLX509CRL.getElement());
/* 227 */     XMLUtils.addReturnToElement(this.constructionElement);
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
/*     */   public void addDigest(X509Certificate paramX509Certificate, String paramString) throws XMLSecurityException {
/* 239 */     add(new XMLX509Digest(this.doc, paramX509Certificate, paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addDigest(byte[] paramArrayOfbyte, String paramString) {
/* 249 */     add(new XMLX509Digest(this.doc, paramArrayOfbyte, paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(XMLX509Digest paramXMLX509Digest) {
/* 258 */     this.constructionElement.appendChild(paramXMLX509Digest.getElement());
/* 259 */     XMLUtils.addReturnToElement(this.constructionElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addUnknownElement(Element paramElement) {
/* 268 */     this.constructionElement.appendChild(paramElement);
/* 269 */     XMLUtils.addReturnToElement(this.constructionElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int lengthIssuerSerial() {
/* 278 */     return length("http://www.w3.org/2000/09/xmldsig#", "X509IssuerSerial");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int lengthSKI() {
/* 287 */     return length("http://www.w3.org/2000/09/xmldsig#", "X509SKI");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int lengthSubjectName() {
/* 296 */     return length("http://www.w3.org/2000/09/xmldsig#", "X509SubjectName");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int lengthCertificate() {
/* 305 */     return length("http://www.w3.org/2000/09/xmldsig#", "X509Certificate");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int lengthCRL() {
/* 314 */     return length("http://www.w3.org/2000/09/xmldsig#", "X509CRL");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int lengthDigest() {
/* 323 */     return length("http://www.w3.org/2009/xmldsig11#", "X509Digest");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int lengthUnknownElement() {
/* 332 */     byte b = 0;
/* 333 */     Node node = this.constructionElement.getFirstChild();
/* 334 */     while (node != null) {
/* 335 */       if (node.getNodeType() == 1 && 
/* 336 */         !node.getNamespaceURI().equals("http://www.w3.org/2000/09/xmldsig#")) {
/* 337 */         b++;
/*     */       }
/* 339 */       node = node.getNextSibling();
/*     */     } 
/*     */     
/* 342 */     return b;
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
/*     */   public XMLX509IssuerSerial itemIssuerSerial(int paramInt) throws XMLSecurityException {
/* 354 */     Element element = XMLUtils.selectDsNode(this.constructionElement
/* 355 */         .getFirstChild(), "X509IssuerSerial", paramInt);
/*     */     
/* 357 */     if (element != null) {
/* 358 */       return new XMLX509IssuerSerial(element, this.baseURI);
/*     */     }
/* 360 */     return null;
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
/*     */   public XMLX509SKI itemSKI(int paramInt) throws XMLSecurityException {
/* 373 */     Element element = XMLUtils.selectDsNode(this.constructionElement
/* 374 */         .getFirstChild(), "X509SKI", paramInt);
/*     */     
/* 376 */     if (element != null) {
/* 377 */       return new XMLX509SKI(element, this.baseURI);
/*     */     }
/* 379 */     return null;
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
/*     */   public XMLX509SubjectName itemSubjectName(int paramInt) throws XMLSecurityException {
/* 392 */     Element element = XMLUtils.selectDsNode(this.constructionElement
/* 393 */         .getFirstChild(), "X509SubjectName", paramInt);
/*     */     
/* 395 */     if (element != null) {
/* 396 */       return new XMLX509SubjectName(element, this.baseURI);
/*     */     }
/* 398 */     return null;
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
/*     */   public XMLX509Certificate itemCertificate(int paramInt) throws XMLSecurityException {
/* 411 */     Element element = XMLUtils.selectDsNode(this.constructionElement
/* 412 */         .getFirstChild(), "X509Certificate", paramInt);
/*     */     
/* 414 */     if (element != null) {
/* 415 */       return new XMLX509Certificate(element, this.baseURI);
/*     */     }
/* 417 */     return null;
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
/*     */   public XMLX509CRL itemCRL(int paramInt) throws XMLSecurityException {
/* 430 */     Element element = XMLUtils.selectDsNode(this.constructionElement
/* 431 */         .getFirstChild(), "X509CRL", paramInt);
/*     */     
/* 433 */     if (element != null) {
/* 434 */       return new XMLX509CRL(element, this.baseURI);
/*     */     }
/* 436 */     return null;
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
/*     */   public XMLX509Digest itemDigest(int paramInt) throws XMLSecurityException {
/* 449 */     Element element = XMLUtils.selectDs11Node(this.constructionElement
/* 450 */         .getFirstChild(), "X509Digest", paramInt);
/*     */     
/* 452 */     if (element != null) {
/* 453 */       return new XMLX509Digest(element, this.baseURI);
/*     */     }
/* 455 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element itemUnknownElement(int paramInt) {
/* 466 */     if (log.isLoggable(Level.FINE)) {
/* 467 */       log.log(Level.FINE, "itemUnknownElement not implemented:" + paramInt);
/*     */     }
/* 469 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsIssuerSerial() {
/* 478 */     return (lengthIssuerSerial() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsSKI() {
/* 487 */     return (lengthSKI() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsSubjectName() {
/* 496 */     return (lengthSubjectName() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsCertificate() {
/* 505 */     return (lengthCertificate() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsDigest() {
/* 514 */     return (lengthDigest() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsCRL() {
/* 523 */     return (lengthCRL() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsUnknownElement() {
/* 532 */     return (lengthUnknownElement() > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBaseLocalName() {
/* 537 */     return "X509Data";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/content/X509Data.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */