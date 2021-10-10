/*     */ package com.sun.org.apache.xml.internal.security.signature;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.algorithms.MessageDigestAlgorithm;
/*     */ import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
/*     */ import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.signature.reference.ReferenceData;
/*     */ import com.sun.org.apache.xml.internal.security.signature.reference.ReferenceNodeSetData;
/*     */ import com.sun.org.apache.xml.internal.security.signature.reference.ReferenceOctetStreamData;
/*     */ import com.sun.org.apache.xml.internal.security.signature.reference.ReferenceSubTreeData;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.InvalidTransformException;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.Transform;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.TransformationException;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.Transforms;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.params.InclusiveNamespaces;
/*     */ import com.sun.org.apache.xml.internal.security.utils.Base64;
/*     */ import com.sun.org.apache.xml.internal.security.utils.DigesterOutputStream;
/*     */ import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
/*     */ import com.sun.org.apache.xml.internal.security.utils.UnsyncBufferedOutputStream;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolver;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverException;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.SortedSet;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
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
/*     */ public class Reference
/*     */   extends SignatureElementProxy
/*     */ {
/*     */   public static final String OBJECT_URI = "http://www.w3.org/2000/09/xmldsig#Object";
/*     */   public static final String MANIFEST_URI = "http://www.w3.org/2000/09/xmldsig#Manifest";
/*     */   public static final int MAXIMUM_TRANSFORM_COUNT = 5;
/*     */   private boolean secureValidation;
/*     */   
/* 128 */   private static boolean useC14N11 = ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() {
/*     */         public Boolean run() {
/* 130 */           return Boolean.valueOf(Boolean.getBoolean("com.sun.org.apache.xml.internal.security.useC14N11"));
/*     */         }
/* 132 */       })).booleanValue();
/*     */ 
/*     */ 
/*     */   
/* 136 */   private static final Logger log = Logger.getLogger(Reference.class.getName());
/*     */ 
/*     */ 
/*     */   
/*     */   private Manifest manifest;
/*     */ 
/*     */ 
/*     */   
/*     */   private XMLSignatureInput transformsOutput;
/*     */ 
/*     */ 
/*     */   
/*     */   private Transforms transforms;
/*     */ 
/*     */ 
/*     */   
/*     */   private Element digestMethodElem;
/*     */ 
/*     */ 
/*     */   
/*     */   private Element digestValueElement;
/*     */ 
/*     */ 
/*     */   
/*     */   private ReferenceData referenceData;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Reference(Document paramDocument, String paramString1, String paramString2, Manifest paramManifest, Transforms paramTransforms, String paramString3) throws XMLSignatureException {
/* 166 */     super(paramDocument);
/*     */     
/* 168 */     XMLUtils.addReturnToElement(this.constructionElement);
/*     */     
/* 170 */     this.baseURI = paramString1;
/* 171 */     this.manifest = paramManifest;
/*     */     
/* 173 */     setURI(paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     if (paramTransforms != null) {
/* 181 */       this.transforms = paramTransforms;
/* 182 */       this.constructionElement.appendChild(paramTransforms.getElement());
/* 183 */       XMLUtils.addReturnToElement(this.constructionElement);
/*     */     } 
/*     */     
/* 186 */     MessageDigestAlgorithm messageDigestAlgorithm = MessageDigestAlgorithm.getInstance(this.doc, paramString3);
/*     */     
/* 188 */     this.digestMethodElem = messageDigestAlgorithm.getElement();
/* 189 */     this.constructionElement.appendChild(this.digestMethodElem);
/* 190 */     XMLUtils.addReturnToElement(this.constructionElement);
/*     */     
/* 192 */     this
/* 193 */       .digestValueElement = XMLUtils.createElementInSignatureSpace(this.doc, "DigestValue");
/*     */     
/* 195 */     this.constructionElement.appendChild(this.digestValueElement);
/* 196 */     XMLUtils.addReturnToElement(this.constructionElement);
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
/*     */   protected Reference(Element paramElement, String paramString, Manifest paramManifest) throws XMLSecurityException {
/* 211 */     this(paramElement, paramString, paramManifest, false);
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
/*     */   protected Reference(Element paramElement, String paramString, Manifest paramManifest, boolean paramBoolean) throws XMLSecurityException {
/* 227 */     super(paramElement, paramString);
/* 228 */     this.secureValidation = paramBoolean;
/* 229 */     this.baseURI = paramString;
/* 230 */     Element element = XMLUtils.getNextElement(paramElement.getFirstChild());
/* 231 */     if ("Transforms".equals(element.getLocalName()) && "http://www.w3.org/2000/09/xmldsig#"
/* 232 */       .equals(element.getNamespaceURI())) {
/* 233 */       this.transforms = new Transforms(element, this.baseURI);
/* 234 */       this.transforms.setSecureValidation(paramBoolean);
/* 235 */       if (paramBoolean && this.transforms.getLength() > 5) {
/* 236 */         Object[] arrayOfObject = { Integer.valueOf(this.transforms.getLength()), Integer.valueOf(5) };
/*     */         
/* 238 */         throw new XMLSecurityException("signature.tooManyTransforms", arrayOfObject);
/*     */       } 
/* 240 */       element = XMLUtils.getNextElement(element.getNextSibling());
/*     */     } 
/* 242 */     this.digestMethodElem = element;
/* 243 */     this.digestValueElement = XMLUtils.getNextElement(this.digestMethodElem.getNextSibling());
/* 244 */     this.manifest = paramManifest;
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
/*     */   public MessageDigestAlgorithm getMessageDigestAlgorithm() throws XMLSignatureException {
/* 256 */     if (this.digestMethodElem == null) {
/* 257 */       return null;
/*     */     }
/*     */     
/* 260 */     String str = this.digestMethodElem.getAttributeNS((String)null, "Algorithm");
/*     */     
/* 262 */     if (str == null) {
/* 263 */       return null;
/*     */     }
/*     */     
/* 266 */     if (this.secureValidation && "http://www.w3.org/2001/04/xmldsig-more#md5".equals(str)) {
/* 267 */       Object[] arrayOfObject = { str };
/*     */       
/* 269 */       throw new XMLSignatureException("signature.signatureAlgorithm", arrayOfObject);
/*     */     } 
/*     */     
/* 272 */     return MessageDigestAlgorithm.getInstance(this.doc, str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setURI(String paramString) {
/* 281 */     if (paramString != null) {
/* 282 */       this.constructionElement.setAttributeNS((String)null, "URI", paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getURI() {
/* 292 */     return this.constructionElement.getAttributeNS((String)null, "URI");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(String paramString) {
/* 301 */     if (paramString != null) {
/* 302 */       this.constructionElement.setAttributeNS((String)null, "Id", paramString);
/* 303 */       this.constructionElement.setIdAttributeNS((String)null, "Id", true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 313 */     return this.constructionElement.getAttributeNS((String)null, "Id");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setType(String paramString) {
/* 324 */     if (paramString != null) {
/* 325 */       this.constructionElement.setAttributeNS((String)null, "Type", paramString);
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
/*     */   public String getType() {
/* 337 */     return this.constructionElement.getAttributeNS((String)null, "Type");
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
/*     */   public boolean typeIsReferenceToObject() {
/* 350 */     if ("http://www.w3.org/2000/09/xmldsig#Object".equals(getType())) {
/* 351 */       return true;
/*     */     }
/*     */     
/* 354 */     return false;
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
/*     */   public boolean typeIsReferenceToManifest() {
/* 367 */     if ("http://www.w3.org/2000/09/xmldsig#Manifest".equals(getType())) {
/* 368 */       return true;
/*     */     }
/*     */     
/* 371 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setDigestValueElement(byte[] paramArrayOfbyte) {
/* 380 */     Node node = this.digestValueElement.getFirstChild();
/* 381 */     while (node != null) {
/* 382 */       this.digestValueElement.removeChild(node);
/* 383 */       node = node.getNextSibling();
/*     */     } 
/*     */     
/* 386 */     String str = Base64.encode(paramArrayOfbyte);
/* 387 */     Text text = this.doc.createTextNode(str);
/*     */     
/* 389 */     this.digestValueElement.appendChild(text);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void generateDigestValue() throws XMLSignatureException, ReferenceNotInitializedException {
/* 400 */     setDigestValueElement(calculateDigest(false));
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
/*     */   public XMLSignatureInput getContentsBeforeTransformation() throws ReferenceNotInitializedException {
/*     */     try {
/* 413 */       Attr attr = this.constructionElement.getAttributeNodeNS((String)null, "URI");
/*     */ 
/*     */       
/* 416 */       ResourceResolver resourceResolver = ResourceResolver.getInstance(attr, this.baseURI, this.manifest
/* 417 */           .getPerManifestResolvers(), this.secureValidation);
/*     */       
/* 419 */       resourceResolver.addProperties(this.manifest.getResolverProperties());
/*     */       
/* 421 */       return resourceResolver.resolve(attr, this.baseURI, this.secureValidation);
/* 422 */     } catch (ResourceResolverException resourceResolverException) {
/* 423 */       throw new ReferenceNotInitializedException("empty", resourceResolverException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private XMLSignatureInput getContentsAfterTransformation(XMLSignatureInput paramXMLSignatureInput, OutputStream paramOutputStream) throws XMLSignatureException {
/*     */     try {
/* 431 */       Transforms transforms = getTransforms();
/* 432 */       XMLSignatureInput xMLSignatureInput = null;
/*     */       
/* 434 */       if (transforms != null) {
/* 435 */         xMLSignatureInput = transforms.performTransforms(paramXMLSignatureInput, paramOutputStream);
/* 436 */         this.transformsOutput = xMLSignatureInput;
/*     */       }
/*     */       else {
/*     */         
/* 440 */         xMLSignatureInput = paramXMLSignatureInput;
/*     */       } 
/*     */       
/* 443 */       return xMLSignatureInput;
/* 444 */     } catch (ResourceResolverException resourceResolverException) {
/* 445 */       throw new XMLSignatureException("empty", resourceResolverException);
/* 446 */     } catch (CanonicalizationException canonicalizationException) {
/* 447 */       throw new XMLSignatureException("empty", canonicalizationException);
/* 448 */     } catch (InvalidCanonicalizerException invalidCanonicalizerException) {
/* 449 */       throw new XMLSignatureException("empty", invalidCanonicalizerException);
/* 450 */     } catch (TransformationException transformationException) {
/* 451 */       throw new XMLSignatureException("empty", transformationException);
/* 452 */     } catch (XMLSecurityException xMLSecurityException) {
/* 453 */       throw new XMLSignatureException("empty", xMLSecurityException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSignatureInput getContentsAfterTransformation() throws XMLSignatureException {
/* 464 */     XMLSignatureInput xMLSignatureInput = getContentsBeforeTransformation();
/* 465 */     cacheDereferencedElement(xMLSignatureInput);
/*     */     
/* 467 */     return getContentsAfterTransformation(xMLSignatureInput, (OutputStream)null);
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
/*     */   public XMLSignatureInput getNodesetBeforeFirstCanonicalization() throws XMLSignatureException {
/*     */     try {
/* 480 */       XMLSignatureInput xMLSignatureInput1 = getContentsBeforeTransformation();
/* 481 */       cacheDereferencedElement(xMLSignatureInput1);
/* 482 */       XMLSignatureInput xMLSignatureInput2 = xMLSignatureInput1;
/* 483 */       Transforms transforms = getTransforms();
/*     */       
/* 485 */       if (transforms != null) {
/* 486 */         for (byte b = 0; b < transforms.getLength(); b++) {
/* 487 */           Transform transform = transforms.item(b);
/* 488 */           String str = transform.getURI();
/*     */           
/* 490 */           if (str.equals("http://www.w3.org/2001/10/xml-exc-c14n#") || str
/* 491 */             .equals("http://www.w3.org/2001/10/xml-exc-c14n#WithComments") || str
/* 492 */             .equals("http://www.w3.org/TR/2001/REC-xml-c14n-20010315") || str
/* 493 */             .equals("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments")) {
/*     */             break;
/*     */           }
/*     */           
/* 497 */           xMLSignatureInput2 = transform.performTransform(xMLSignatureInput2, (OutputStream)null);
/*     */         } 
/*     */         
/* 500 */         xMLSignatureInput2.setSourceURI(xMLSignatureInput1.getSourceURI());
/*     */       } 
/* 502 */       return xMLSignatureInput2;
/* 503 */     } catch (IOException iOException) {
/* 504 */       throw new XMLSignatureException("empty", iOException);
/* 505 */     } catch (ResourceResolverException resourceResolverException) {
/* 506 */       throw new XMLSignatureException("empty", resourceResolverException);
/* 507 */     } catch (CanonicalizationException canonicalizationException) {
/* 508 */       throw new XMLSignatureException("empty", canonicalizationException);
/* 509 */     } catch (InvalidCanonicalizerException invalidCanonicalizerException) {
/* 510 */       throw new XMLSignatureException("empty", invalidCanonicalizerException);
/* 511 */     } catch (TransformationException transformationException) {
/* 512 */       throw new XMLSignatureException("empty", transformationException);
/* 513 */     } catch (XMLSecurityException xMLSecurityException) {
/* 514 */       throw new XMLSignatureException("empty", xMLSecurityException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHTMLRepresentation() throws XMLSignatureException {
/*     */     try {
/*     */       SortedSet<String> sortedSet;
/* 525 */       XMLSignatureInput xMLSignatureInput = getNodesetBeforeFirstCanonicalization();
/*     */       
/* 527 */       Transforms transforms = getTransforms();
/* 528 */       Transform transform = null;
/*     */       
/* 530 */       if (transforms != null) {
/* 531 */         for (byte b = 0; b < transforms.getLength(); b++) {
/* 532 */           Transform transform1 = transforms.item(b);
/* 533 */           String str = transform1.getURI();
/*     */           
/* 535 */           if (str.equals("http://www.w3.org/2001/10/xml-exc-c14n#") || str
/* 536 */             .equals("http://www.w3.org/2001/10/xml-exc-c14n#WithComments")) {
/* 537 */             transform = transform1;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/* 543 */       HashSet hashSet = new HashSet();
/* 544 */       if (transform != null && transform
/* 545 */         .length("http://www.w3.org/2001/10/xml-exc-c14n#", "InclusiveNamespaces") == 1) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 557 */         InclusiveNamespaces inclusiveNamespaces = new InclusiveNamespaces(XMLUtils.selectNode(transform.getElement().getFirstChild(), "http://www.w3.org/2001/10/xml-exc-c14n#", "InclusiveNamespaces", 0), getBaseURI());
/*     */ 
/*     */         
/* 560 */         sortedSet = InclusiveNamespaces.prefixStr2Set(inclusiveNamespaces.getInclusiveNamespaces());
/*     */       } 
/*     */       
/* 563 */       return xMLSignatureInput.getHTMLRepresentation(sortedSet);
/* 564 */     } catch (TransformationException transformationException) {
/* 565 */       throw new XMLSignatureException("empty", transformationException);
/* 566 */     } catch (InvalidTransformException invalidTransformException) {
/* 567 */       throw new XMLSignatureException("empty", invalidTransformException);
/* 568 */     } catch (XMLSecurityException xMLSecurityException) {
/* 569 */       throw new XMLSignatureException("empty", xMLSecurityException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSignatureInput getTransformsOutput() {
/* 578 */     return this.transformsOutput;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceData getReferenceData() {
/* 586 */     return this.referenceData;
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
/*     */   protected XMLSignatureInput dereferenceURIandPerformTransforms(OutputStream paramOutputStream) throws XMLSignatureException {
/*     */     try {
/* 601 */       XMLSignatureInput xMLSignatureInput1 = getContentsBeforeTransformation();
/* 602 */       cacheDereferencedElement(xMLSignatureInput1);
/*     */       
/* 604 */       XMLSignatureInput xMLSignatureInput2 = getContentsAfterTransformation(xMLSignatureInput1, paramOutputStream);
/* 605 */       this.transformsOutput = xMLSignatureInput2;
/* 606 */       return xMLSignatureInput2;
/* 607 */     } catch (XMLSecurityException xMLSecurityException) {
/* 608 */       throw new ReferenceNotInitializedException("empty", xMLSecurityException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void cacheDereferencedElement(XMLSignatureInput paramXMLSignatureInput) {
/* 616 */     if (paramXMLSignatureInput.isNodeSet()) {
/*     */       try {
/* 618 */         final Set<Node> s = paramXMLSignatureInput.getNodeSet();
/* 619 */         this.referenceData = new ReferenceNodeSetData() {
/*     */             public Iterator<Node> iterator() {
/* 621 */               return new Iterator<Node>()
/*     */                 {
/* 623 */                   Iterator<Node> sIterator = s.iterator();
/*     */                   
/*     */                   public boolean hasNext() {
/* 626 */                     return this.sIterator.hasNext();
/*     */                   }
/*     */                   
/*     */                   public Node next() {
/* 630 */                     return this.sIterator.next();
/*     */                   }
/*     */                   
/*     */                   public void remove() {
/* 634 */                     throw new UnsupportedOperationException();
/*     */                   }
/*     */                 };
/*     */             }
/*     */           };
/* 639 */       } catch (Exception exception) {
/*     */         
/* 641 */         log.log(Level.WARNING, "cannot cache dereferenced data: " + exception);
/*     */       } 
/* 643 */     } else if (paramXMLSignatureInput.isElement()) {
/* 644 */       this
/* 645 */         .referenceData = new ReferenceSubTreeData(paramXMLSignatureInput.getSubNode(), paramXMLSignatureInput.isExcludeComments());
/* 646 */     } else if (paramXMLSignatureInput.isOctetStream() || paramXMLSignatureInput.isByteArray()) {
/*     */       try {
/* 648 */         this
/*     */           
/* 650 */           .referenceData = new ReferenceOctetStreamData(paramXMLSignatureInput.getOctetStream(), paramXMLSignatureInput.getSourceURI(), paramXMLSignatureInput.getMIMEType());
/* 651 */       } catch (IOException iOException) {
/*     */         
/* 653 */         log.log(Level.WARNING, "cannot cache dereferenced data: " + iOException);
/*     */       } 
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
/*     */   public Transforms getTransforms() throws XMLSignatureException, InvalidTransformException, TransformationException, XMLSecurityException {
/* 670 */     return this.transforms;
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
/*     */   public byte[] getReferencedBytes() throws ReferenceNotInitializedException, XMLSignatureException {
/*     */     try {
/* 683 */       XMLSignatureInput xMLSignatureInput = dereferenceURIandPerformTransforms((OutputStream)null);
/* 684 */       return xMLSignatureInput.getBytes();
/* 685 */     } catch (IOException iOException) {
/* 686 */       throw new ReferenceNotInitializedException("empty", iOException);
/* 687 */     } catch (CanonicalizationException canonicalizationException) {
/* 688 */       throw new ReferenceNotInitializedException("empty", canonicalizationException);
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
/*     */   private byte[] calculateDigest(boolean paramBoolean) throws ReferenceNotInitializedException, XMLSignatureException {
/* 703 */     UnsyncBufferedOutputStream unsyncBufferedOutputStream = null;
/*     */     try {
/* 705 */       MessageDigestAlgorithm messageDigestAlgorithm = getMessageDigestAlgorithm();
/*     */       
/* 707 */       messageDigestAlgorithm.reset();
/* 708 */       DigesterOutputStream digesterOutputStream = new DigesterOutputStream(messageDigestAlgorithm);
/* 709 */       unsyncBufferedOutputStream = new UnsyncBufferedOutputStream(digesterOutputStream);
/* 710 */       XMLSignatureInput xMLSignatureInput = dereferenceURIandPerformTransforms(unsyncBufferedOutputStream);
/*     */ 
/*     */       
/* 713 */       if (useC14N11 && !paramBoolean && !xMLSignatureInput.isOutputStreamSet() && 
/* 714 */         !xMLSignatureInput.isOctetStream()) {
/* 715 */         if (this.transforms == null) {
/* 716 */           this.transforms = new Transforms(this.doc);
/* 717 */           this.transforms.setSecureValidation(this.secureValidation);
/* 718 */           this.constructionElement.insertBefore(this.transforms.getElement(), this.digestMethodElem);
/*     */         } 
/* 720 */         this.transforms.addTransform("http://www.w3.org/2006/12/xml-c14n11");
/* 721 */         xMLSignatureInput.updateOutputStream(unsyncBufferedOutputStream, true);
/*     */       } else {
/* 723 */         xMLSignatureInput.updateOutputStream(unsyncBufferedOutputStream);
/*     */       } 
/* 725 */       unsyncBufferedOutputStream.flush();
/*     */       
/* 727 */       if (xMLSignatureInput.getOctetStreamReal() != null) {
/* 728 */         xMLSignatureInput.getOctetStreamReal().close();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 734 */       return digesterOutputStream.getDigestValue();
/* 735 */     } catch (XMLSecurityException xMLSecurityException) {
/* 736 */       throw new ReferenceNotInitializedException("empty", xMLSecurityException);
/* 737 */     } catch (IOException iOException) {
/* 738 */       throw new ReferenceNotInitializedException("empty", iOException);
/*     */     } finally {
/* 740 */       if (unsyncBufferedOutputStream != null) {
/*     */         try {
/* 742 */           unsyncBufferedOutputStream.close();
/* 743 */         } catch (IOException iOException) {
/* 744 */           throw new ReferenceNotInitializedException("empty", iOException);
/*     */         } 
/*     */       }
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
/*     */   public byte[] getDigestValue() throws Base64DecodingException, XMLSecurityException {
/* 758 */     if (this.digestValueElement == null) {
/*     */       
/* 760 */       Object[] arrayOfObject = { "DigestValue", "http://www.w3.org/2000/09/xmldsig#" };
/* 761 */       throw new XMLSecurityException("signature.Verification.NoSignatureElement", arrayOfObject);
/*     */     } 
/*     */ 
/*     */     
/* 765 */     return Base64.decode(this.digestValueElement);
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
/*     */   public boolean verify() throws ReferenceNotInitializedException, XMLSecurityException {
/* 778 */     byte[] arrayOfByte1 = getDigestValue();
/* 779 */     byte[] arrayOfByte2 = calculateDigest(true);
/* 780 */     boolean bool = MessageDigestAlgorithm.isEqual(arrayOfByte1, arrayOfByte2);
/*     */     
/* 782 */     if (!bool) {
/* 783 */       log.log(Level.WARNING, "Verification failed for URI \"" + getURI() + "\"");
/* 784 */       log.log(Level.WARNING, "Expected Digest: " + Base64.encode(arrayOfByte1));
/* 785 */       log.log(Level.WARNING, "Actual Digest: " + Base64.encode(arrayOfByte2));
/*     */     }
/* 787 */     else if (log.isLoggable(Level.FINE)) {
/* 788 */       log.log(Level.FINE, "Verification successful for URI \"" + getURI() + "\"");
/*     */     } 
/*     */ 
/*     */     
/* 792 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseLocalName() {
/* 800 */     return "Reference";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/signature/Reference.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */