/*     */ package com.sun.org.apache.xml.internal.security.signature;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
/*     */ import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
/*     */ import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.KeyInfo;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.X509Data;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.Transforms;
/*     */ import com.sun.org.apache.xml.internal.security.utils.Base64;
/*     */ import com.sun.org.apache.xml.internal.security.utils.I18n;
/*     */ import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
/*     */ import com.sun.org.apache.xml.internal.security.utils.SignerOutputStream;
/*     */ import com.sun.org.apache.xml.internal.security.utils.UnsyncBufferedOutputStream;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolver;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverSpi;
/*     */ import java.io.IOException;
/*     */ import java.security.Key;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.crypto.SecretKey;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.Text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class XMLSignature
/*     */   extends SignatureElementProxy
/*     */ {
/*     */   public static final String ALGO_ID_MAC_HMAC_SHA1 = "http://www.w3.org/2000/09/xmldsig#hmac-sha1";
/*     */   public static final String ALGO_ID_SIGNATURE_DSA = "http://www.w3.org/2000/09/xmldsig#dsa-sha1";
/*     */   public static final String ALGO_ID_SIGNATURE_DSA_SHA256 = "http://www.w3.org/2009/xmldsig11#dsa-sha256";
/*     */   public static final String ALGO_ID_SIGNATURE_RSA = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
/*     */   public static final String ALGO_ID_SIGNATURE_RSA_SHA1 = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
/*     */   public static final String ALGO_ID_SIGNATURE_NOT_RECOMMENDED_RSA_MD5 = "http://www.w3.org/2001/04/xmldsig-more#rsa-md5";
/*     */   public static final String ALGO_ID_SIGNATURE_RSA_RIPEMD160 = "http://www.w3.org/2001/04/xmldsig-more#rsa-ripemd160";
/*     */   public static final String ALGO_ID_SIGNATURE_RSA_SHA256 = "http://www.w3.org/2001/04/xmldsig-more#rsa-sha256";
/*     */   public static final String ALGO_ID_SIGNATURE_RSA_SHA384 = "http://www.w3.org/2001/04/xmldsig-more#rsa-sha384";
/*     */   public static final String ALGO_ID_SIGNATURE_RSA_SHA512 = "http://www.w3.org/2001/04/xmldsig-more#rsa-sha512";
/*     */   public static final String ALGO_ID_MAC_HMAC_NOT_RECOMMENDED_MD5 = "http://www.w3.org/2001/04/xmldsig-more#hmac-md5";
/*     */   public static final String ALGO_ID_MAC_HMAC_RIPEMD160 = "http://www.w3.org/2001/04/xmldsig-more#hmac-ripemd160";
/*     */   public static final String ALGO_ID_MAC_HMAC_SHA256 = "http://www.w3.org/2001/04/xmldsig-more#hmac-sha256";
/*     */   public static final String ALGO_ID_MAC_HMAC_SHA384 = "http://www.w3.org/2001/04/xmldsig-more#hmac-sha384";
/*     */   public static final String ALGO_ID_MAC_HMAC_SHA512 = "http://www.w3.org/2001/04/xmldsig-more#hmac-sha512";
/*     */   public static final String ALGO_ID_SIGNATURE_ECDSA_SHA1 = "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1";
/*     */   public static final String ALGO_ID_SIGNATURE_ECDSA_SHA256 = "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256";
/*     */   public static final String ALGO_ID_SIGNATURE_ECDSA_SHA384 = "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha384";
/*     */   public static final String ALGO_ID_SIGNATURE_ECDSA_SHA512 = "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha512";
/* 158 */   private static Logger log = Logger.getLogger(XMLSignature.class.getName());
/*     */ 
/*     */   
/*     */   private SignedInfo signedInfo;
/*     */ 
/*     */   
/*     */   private KeyInfo keyInfo;
/*     */ 
/*     */   
/*     */   private boolean followManifestsDuringValidation = false;
/*     */ 
/*     */   
/*     */   private Element signatureValueElement;
/*     */ 
/*     */   
/*     */   private static final int MODE_SIGN = 0;
/*     */   
/*     */   private static final int MODE_VERIFY = 1;
/*     */   
/* 177 */   private int state = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSignature(Document paramDocument, String paramString1, String paramString2) throws XMLSecurityException {
/* 193 */     this(paramDocument, paramString1, paramString2, 0, "http://www.w3.org/TR/2001/REC-xml-c14n-20010315");
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
/*     */   public XMLSignature(Document paramDocument, String paramString1, String paramString2, int paramInt) throws XMLSecurityException {
/* 207 */     this(paramDocument, paramString1, paramString2, paramInt, "http://www.w3.org/TR/2001/REC-xml-c14n-20010315");
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
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSignature(Document paramDocument, String paramString1, String paramString2, String paramString3) throws XMLSecurityException {
/* 229 */     this(paramDocument, paramString1, paramString2, 0, paramString3);
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
/*     */   
/*     */   public XMLSignature(Document paramDocument, String paramString1, String paramString2, int paramInt, String paramString3) throws XMLSecurityException {
/* 249 */     super(paramDocument);
/*     */     
/* 251 */     String str = getDefaultPrefix("http://www.w3.org/2000/09/xmldsig#");
/* 252 */     if (str == null || str.length() == 0) {
/* 253 */       this.constructionElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "http://www.w3.org/2000/09/xmldsig#");
/*     */     }
/*     */     else {
/*     */       
/* 257 */       this.constructionElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + str, "http://www.w3.org/2000/09/xmldsig#");
/*     */     } 
/*     */ 
/*     */     
/* 261 */     XMLUtils.addReturnToElement(this.constructionElement);
/*     */     
/* 263 */     this.baseURI = paramString1;
/* 264 */     this.signedInfo = new SignedInfo(this.doc, paramString2, paramInt, paramString3);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 269 */     this.constructionElement.appendChild(this.signedInfo.getElement());
/* 270 */     XMLUtils.addReturnToElement(this.constructionElement);
/*     */ 
/*     */     
/* 273 */     this
/* 274 */       .signatureValueElement = XMLUtils.createElementInSignatureSpace(this.doc, "SignatureValue");
/*     */     
/* 276 */     this.constructionElement.appendChild(this.signatureValueElement);
/* 277 */     XMLUtils.addReturnToElement(this.constructionElement);
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
/*     */   public XMLSignature(Document paramDocument, String paramString, Element paramElement1, Element paramElement2) throws XMLSecurityException {
/* 294 */     super(paramDocument);
/*     */     
/* 296 */     String str = getDefaultPrefix("http://www.w3.org/2000/09/xmldsig#");
/* 297 */     if (str == null || str.length() == 0) {
/* 298 */       this.constructionElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "http://www.w3.org/2000/09/xmldsig#");
/*     */     }
/*     */     else {
/*     */       
/* 302 */       this.constructionElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + str, "http://www.w3.org/2000/09/xmldsig#");
/*     */     } 
/*     */ 
/*     */     
/* 306 */     XMLUtils.addReturnToElement(this.constructionElement);
/*     */     
/* 308 */     this.baseURI = paramString;
/* 309 */     this.signedInfo = new SignedInfo(this.doc, paramElement1, paramElement2);
/*     */ 
/*     */     
/* 312 */     this.constructionElement.appendChild(this.signedInfo.getElement());
/* 313 */     XMLUtils.addReturnToElement(this.constructionElement);
/*     */ 
/*     */     
/* 316 */     this
/* 317 */       .signatureValueElement = XMLUtils.createElementInSignatureSpace(this.doc, "SignatureValue");
/*     */     
/* 319 */     this.constructionElement.appendChild(this.signatureValueElement);
/* 320 */     XMLUtils.addReturnToElement(this.constructionElement);
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
/*     */   public XMLSignature(Element paramElement, String paramString) throws XMLSignatureException, XMLSecurityException {
/* 334 */     this(paramElement, paramString, false);
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
/*     */   public XMLSignature(Element paramElement, String paramString, boolean paramBoolean) throws XMLSignatureException, XMLSecurityException {
/* 349 */     super(paramElement, paramString);
/*     */ 
/*     */     
/* 352 */     Element element1 = XMLUtils.getNextElement(paramElement.getFirstChild());
/*     */ 
/*     */     
/* 355 */     if (element1 == null) {
/* 356 */       Object[] arrayOfObject = { "SignedInfo", "Signature" };
/* 357 */       throw new XMLSignatureException("xml.WrongContent", arrayOfObject);
/*     */     } 
/*     */ 
/*     */     
/* 361 */     this.signedInfo = new SignedInfo(element1, paramString, paramBoolean);
/*     */     
/* 363 */     element1 = XMLUtils.getNextElement(paramElement.getFirstChild());
/*     */ 
/*     */     
/* 366 */     this
/* 367 */       .signatureValueElement = XMLUtils.getNextElement(element1.getNextSibling());
/*     */ 
/*     */     
/* 370 */     if (this.signatureValueElement == null) {
/* 371 */       Object[] arrayOfObject = { "SignatureValue", "Signature" };
/* 372 */       throw new XMLSignatureException("xml.WrongContent", arrayOfObject);
/*     */     } 
/* 374 */     Attr attr = this.signatureValueElement.getAttributeNodeNS((String)null, "Id");
/* 375 */     if (attr != null) {
/* 376 */       this.signatureValueElement.setIdAttributeNode(attr, true);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 381 */     Element element2 = XMLUtils.getNextElement(this.signatureValueElement.getNextSibling());
/*     */ 
/*     */     
/* 384 */     if (element2 != null && element2
/* 385 */       .getNamespaceURI().equals("http://www.w3.org/2000/09/xmldsig#") && element2
/* 386 */       .getLocalName().equals("KeyInfo")) {
/* 387 */       this.keyInfo = new KeyInfo(element2, paramString);
/* 388 */       this.keyInfo.setSecureValidation(paramBoolean);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 393 */     Element element3 = XMLUtils.getNextElement(this.signatureValueElement.getNextSibling());
/* 394 */     while (element3 != null) {
/* 395 */       Attr attr1 = element3.getAttributeNodeNS((String)null, "Id");
/* 396 */       if (attr1 != null) {
/* 397 */         element3.setIdAttributeNode(attr1, true);
/*     */       }
/*     */       
/* 400 */       NodeList nodeList = element3.getChildNodes();
/* 401 */       int i = nodeList.getLength();
/*     */       
/* 403 */       for (byte b = 0; b < i; b++) {
/* 404 */         Node node = nodeList.item(b);
/* 405 */         if (node.getNodeType() == 1) {
/* 406 */           Element element = (Element)node;
/* 407 */           String str = element.getLocalName();
/* 408 */           if (str.equals("Manifest")) {
/* 409 */             new Manifest(element, paramString);
/* 410 */           } else if (str.equals("SignatureProperties")) {
/* 411 */             new SignatureProperties(element, paramString);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 416 */       element3 = XMLUtils.getNextElement(element3.getNextSibling());
/*     */     } 
/*     */     
/* 419 */     this.state = 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(String paramString) {
/* 428 */     if (paramString != null) {
/* 429 */       this.constructionElement.setAttributeNS((String)null, "Id", paramString);
/* 430 */       this.constructionElement.setIdAttributeNS((String)null, "Id", true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 440 */     return this.constructionElement.getAttributeNS((String)null, "Id");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignedInfo getSignedInfo() {
/* 449 */     return this.signedInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getSignatureValue() throws XMLSignatureException {
/*     */     try {
/* 461 */       return Base64.decode(this.signatureValueElement);
/* 462 */     } catch (Base64DecodingException base64DecodingException) {
/* 463 */       throw new XMLSignatureException("empty", base64DecodingException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setSignatureValueElement(byte[] paramArrayOfbyte) {
/* 475 */     while (this.signatureValueElement.hasChildNodes()) {
/* 476 */       this.signatureValueElement.removeChild(this.signatureValueElement.getFirstChild());
/*     */     }
/*     */     
/* 479 */     String str = Base64.encode(paramArrayOfbyte);
/*     */     
/* 481 */     if (str.length() > 76 && !XMLUtils.ignoreLineBreaks()) {
/* 482 */       str = "\n" + str + "\n";
/*     */     }
/*     */     
/* 485 */     Text text = this.doc.createTextNode(str);
/* 486 */     this.signatureValueElement.appendChild(text);
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
/*     */   public KeyInfo getKeyInfo() {
/* 499 */     if (this.state == 0 && this.keyInfo == null) {
/*     */ 
/*     */       
/* 502 */       this.keyInfo = new KeyInfo(this.doc);
/*     */ 
/*     */       
/* 505 */       Element element1 = this.keyInfo.getElement();
/*     */       
/* 507 */       Element element2 = XMLUtils.selectDsNode(this.constructionElement
/* 508 */           .getFirstChild(), "Object", 0);
/*     */ 
/*     */       
/* 511 */       if (element2 != null) {
/*     */         
/* 513 */         this.constructionElement.insertBefore(element1, element2);
/* 514 */         XMLUtils.addReturnBeforeChild(this.constructionElement, element2);
/*     */       } else {
/*     */         
/* 517 */         this.constructionElement.appendChild(element1);
/* 518 */         XMLUtils.addReturnToElement(this.constructionElement);
/*     */       } 
/*     */     } 
/*     */     
/* 522 */     return this.keyInfo;
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
/*     */   public void appendObject(ObjectContainer paramObjectContainer) throws XMLSignatureException {
/* 540 */     this.constructionElement.appendChild(paramObjectContainer.getElement());
/* 541 */     XMLUtils.addReturnToElement(this.constructionElement);
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
/*     */   public ObjectContainer getObjectItem(int paramInt) {
/* 557 */     Element element = XMLUtils.selectDsNode(this.constructionElement
/* 558 */         .getFirstChild(), "Object", paramInt);
/*     */ 
/*     */     
/*     */     try {
/* 562 */       return new ObjectContainer(element, this.baseURI);
/* 563 */     } catch (XMLSecurityException xMLSecurityException) {
/* 564 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getObjectLength() {
/* 574 */     return length("http://www.w3.org/2000/09/xmldsig#", "Object");
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
/*     */   public void sign(Key paramKey) throws XMLSignatureException {
/* 587 */     if (paramKey instanceof PublicKey) {
/* 588 */       throw new IllegalArgumentException(
/* 589 */           I18n.translate("algorithms.operationOnlyVerification"));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 595 */       SignedInfo signedInfo = getSignedInfo();
/* 596 */       SignatureAlgorithm signatureAlgorithm = signedInfo.getSignatureAlgorithm();
/* 597 */       UnsyncBufferedOutputStream unsyncBufferedOutputStream = null;
/*     */       
/*     */       try {
/* 600 */         signatureAlgorithm.initSign(paramKey);
/*     */ 
/*     */         
/* 603 */         signedInfo.generateDigestValues();
/* 604 */         unsyncBufferedOutputStream = new UnsyncBufferedOutputStream(new SignerOutputStream(signatureAlgorithm));
/*     */         
/* 606 */         signedInfo.signInOctetStream(unsyncBufferedOutputStream);
/* 607 */       } catch (XMLSecurityException xMLSecurityException) {
/* 608 */         throw xMLSecurityException;
/*     */       } finally {
/* 610 */         if (unsyncBufferedOutputStream != null) {
/*     */           try {
/* 612 */             unsyncBufferedOutputStream.close();
/* 613 */           } catch (IOException iOException) {
/* 614 */             if (log.isLoggable(Level.FINE)) {
/* 615 */               log.log(Level.FINE, iOException.getMessage(), iOException);
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 622 */       setSignatureValueElement(signatureAlgorithm.sign());
/* 623 */     } catch (XMLSignatureException xMLSignatureException) {
/* 624 */       throw xMLSignatureException;
/* 625 */     } catch (CanonicalizationException canonicalizationException) {
/* 626 */       throw new XMLSignatureException("empty", canonicalizationException);
/* 627 */     } catch (InvalidCanonicalizerException invalidCanonicalizerException) {
/* 628 */       throw new XMLSignatureException("empty", invalidCanonicalizerException);
/* 629 */     } catch (XMLSecurityException xMLSecurityException) {
/* 630 */       throw new XMLSignatureException("empty", xMLSecurityException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addResourceResolver(ResourceResolver paramResourceResolver) {
/* 640 */     getSignedInfo().addResourceResolver(paramResourceResolver);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addResourceResolver(ResourceResolverSpi paramResourceResolverSpi) {
/* 649 */     getSignedInfo().addResourceResolver(paramResourceResolverSpi);
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
/*     */   public boolean checkSignatureValue(X509Certificate paramX509Certificate) throws XMLSignatureException {
/* 666 */     if (paramX509Certificate != null)
/*     */     {
/* 668 */       return checkSignatureValue(paramX509Certificate.getPublicKey());
/*     */     }
/*     */     
/* 671 */     Object[] arrayOfObject = { "Didn't get a certificate" };
/* 672 */     throw new XMLSignatureException("empty", arrayOfObject);
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
/*     */   public boolean checkSignatureValue(Key paramKey) throws XMLSignatureException {
/* 688 */     if (paramKey == null) {
/* 689 */       Object[] arrayOfObject = { "Didn't get a key" };
/* 690 */       throw new XMLSignatureException("empty", arrayOfObject);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 698 */       SignedInfo signedInfo = getSignedInfo();
/*     */ 
/*     */       
/* 701 */       SignatureAlgorithm signatureAlgorithm = signedInfo.getSignatureAlgorithm();
/* 702 */       if (log.isLoggable(Level.FINE)) {
/* 703 */         log.log(Level.FINE, "signatureMethodURI = " + signatureAlgorithm.getAlgorithmURI());
/* 704 */         log.log(Level.FINE, "jceSigAlgorithm    = " + signatureAlgorithm.getJCEAlgorithmString());
/* 705 */         log.log(Level.FINE, "jceSigProvider     = " + signatureAlgorithm.getJCEProviderName());
/* 706 */         log.log(Level.FINE, "PublicKey = " + paramKey);
/*     */       } 
/* 708 */       byte[] arrayOfByte = null;
/*     */       try {
/* 710 */         signatureAlgorithm.initVerify(paramKey);
/*     */ 
/*     */         
/* 713 */         SignerOutputStream signerOutputStream = new SignerOutputStream(signatureAlgorithm);
/* 714 */         UnsyncBufferedOutputStream unsyncBufferedOutputStream = new UnsyncBufferedOutputStream(signerOutputStream);
/*     */         
/* 716 */         signedInfo.signInOctetStream(unsyncBufferedOutputStream);
/* 717 */         unsyncBufferedOutputStream.close();
/*     */         
/* 719 */         arrayOfByte = getSignatureValue();
/* 720 */       } catch (IOException iOException) {
/* 721 */         if (log.isLoggable(Level.FINE)) {
/* 722 */           log.log(Level.FINE, iOException.getMessage(), iOException);
/*     */         }
/*     */       }
/* 725 */       catch (XMLSecurityException xMLSecurityException) {
/* 726 */         throw xMLSecurityException;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 731 */       if (!signatureAlgorithm.verify(arrayOfByte)) {
/* 732 */         log.log(Level.WARNING, "Signature verification failed.");
/* 733 */         return false;
/*     */       } 
/*     */       
/* 736 */       return signedInfo.verify(this.followManifestsDuringValidation);
/* 737 */     } catch (XMLSignatureException xMLSignatureException) {
/* 738 */       throw xMLSignatureException;
/* 739 */     } catch (XMLSecurityException xMLSecurityException) {
/* 740 */       throw new XMLSignatureException("empty", xMLSecurityException);
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addDocument(String paramString1, Transforms paramTransforms, String paramString2, String paramString3, String paramString4) throws XMLSignatureException {
/* 764 */     this.signedInfo.addDocument(this.baseURI, paramString1, paramTransforms, paramString2, paramString3, paramString4);
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
/*     */   public void addDocument(String paramString1, Transforms paramTransforms, String paramString2) throws XMLSignatureException {
/* 783 */     this.signedInfo.addDocument(this.baseURI, paramString1, paramTransforms, paramString2, null, null);
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
/*     */   public void addDocument(String paramString, Transforms paramTransforms) throws XMLSignatureException {
/* 796 */     this.signedInfo.addDocument(this.baseURI, paramString, paramTransforms, "http://www.w3.org/2000/09/xmldsig#sha1", null, null);
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
/*     */   public void addDocument(String paramString) throws XMLSignatureException {
/* 809 */     this.signedInfo.addDocument(this.baseURI, paramString, null, "http://www.w3.org/2000/09/xmldsig#sha1", null, null);
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
/*     */   public void addKeyInfo(X509Certificate paramX509Certificate) throws XMLSecurityException {
/* 823 */     X509Data x509Data = new X509Data(this.doc);
/*     */     
/* 825 */     x509Data.addCertificate(paramX509Certificate);
/* 826 */     getKeyInfo().add(x509Data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addKeyInfo(PublicKey paramPublicKey) {
/* 836 */     getKeyInfo().add(paramPublicKey);
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
/*     */   public SecretKey createSecretKey(byte[] paramArrayOfbyte) {
/* 849 */     return getSignedInfo().createSecretKey(paramArrayOfbyte);
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
/*     */   public void setFollowNestedManifests(boolean paramBoolean) {
/* 863 */     this.followManifestsDuringValidation = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseLocalName() {
/* 872 */     return "Signature";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/signature/XMLSignature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */