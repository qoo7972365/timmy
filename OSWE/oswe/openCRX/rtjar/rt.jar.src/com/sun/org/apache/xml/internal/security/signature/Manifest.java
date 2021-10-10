/*     */ package com.sun.org.apache.xml.internal.security.signature;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
/*     */ import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.Transforms;
/*     */ import com.sun.org.apache.xml.internal.security.utils.I18n;
/*     */ import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolver;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverSpi;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.DOMException;
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
/*     */ 
/*     */ public class Manifest
/*     */   extends SignatureElementProxy
/*     */ {
/*     */   public static final int MAXIMUM_REFERENCE_COUNT = 30;
/*  65 */   private static Logger log = Logger.getLogger(Manifest.class.getName());
/*     */ 
/*     */   
/*     */   private List<Reference> references;
/*     */   
/*     */   private Element[] referencesEl;
/*     */   
/*  72 */   private boolean[] verificationResults = null;
/*     */ 
/*     */   
/*  75 */   private Map<String, String> resolverProperties = null;
/*     */ 
/*     */   
/*  78 */   private List<ResourceResolver> perManifestResolvers = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean secureValidation;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Manifest(Document paramDocument) {
/*  88 */     super(paramDocument);
/*     */     
/*  90 */     XMLUtils.addReturnToElement(this.constructionElement);
/*     */     
/*  92 */     this.references = new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Manifest(Element paramElement, String paramString) throws XMLSecurityException {
/* 103 */     this(paramElement, paramString, false);
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
/*     */   public Manifest(Element paramElement, String paramString, boolean paramBoolean) throws XMLSecurityException {
/* 117 */     super(paramElement, paramString);
/*     */     
/* 119 */     Attr attr = paramElement.getAttributeNodeNS((String)null, "Id");
/* 120 */     if (attr != null) {
/* 121 */       paramElement.setIdAttributeNode(attr, true);
/*     */     }
/* 123 */     this.secureValidation = paramBoolean;
/*     */ 
/*     */     
/* 126 */     this
/* 127 */       .referencesEl = XMLUtils.selectDsNodes(this.constructionElement
/* 128 */         .getFirstChild(), "Reference");
/*     */     
/* 130 */     int i = this.referencesEl.length;
/* 131 */     if (i == 0) {
/*     */       
/* 133 */       Object[] arrayOfObject = { "Reference", "Manifest" };
/*     */       
/* 135 */       throw new DOMException((short)4, 
/* 136 */           I18n.translate("xml.WrongContent", arrayOfObject));
/*     */     } 
/*     */     
/* 139 */     if (paramBoolean && i > 30) {
/* 140 */       Object[] arrayOfObject = { Integer.valueOf(i), Integer.valueOf(30) };
/*     */       
/* 142 */       throw new XMLSecurityException("signature.tooManyReferences", arrayOfObject);
/*     */     } 
/*     */ 
/*     */     
/* 146 */     this.references = new ArrayList<>(i);
/*     */     
/* 148 */     for (byte b = 0; b < i; b++) {
/* 149 */       Element element = this.referencesEl[b];
/* 150 */       Attr attr1 = element.getAttributeNodeNS((String)null, "Id");
/* 151 */       if (attr1 != null) {
/* 152 */         element.setIdAttributeNode(attr1, true);
/*     */       }
/* 154 */       this.references.add(null);
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
/*     */   public void addDocument(String paramString1, String paramString2, Transforms paramTransforms, String paramString3, String paramString4, String paramString5) throws XMLSignatureException {
/* 178 */     Reference reference = new Reference(this.doc, paramString1, paramString2, this, paramTransforms, paramString3);
/*     */ 
/*     */     
/* 181 */     if (paramString4 != null) {
/* 182 */       reference.setId(paramString4);
/*     */     }
/*     */     
/* 185 */     if (paramString5 != null) {
/* 186 */       reference.setType(paramString5);
/*     */     }
/*     */ 
/*     */     
/* 190 */     this.references.add(reference);
/*     */ 
/*     */     
/* 193 */     this.constructionElement.appendChild(reference.getElement());
/* 194 */     XMLUtils.addReturnToElement(this.constructionElement);
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
/*     */   public void generateDigestValues() throws XMLSignatureException, ReferenceNotInitializedException {
/* 207 */     for (byte b = 0; b < getLength(); b++) {
/*     */       
/* 209 */       Reference reference = this.references.get(b);
/* 210 */       reference.generateDigestValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLength() {
/* 220 */     return this.references.size();
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
/*     */   public Reference item(int paramInt) throws XMLSecurityException {
/* 232 */     if (this.references.get(paramInt) == null) {
/*     */       
/* 234 */       Reference reference = new Reference(this.referencesEl[paramInt], this.baseURI, this, this.secureValidation);
/*     */ 
/*     */       
/* 237 */       this.references.set(paramInt, reference);
/*     */     } 
/*     */     
/* 240 */     return this.references.get(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(String paramString) {
/* 249 */     if (paramString != null) {
/* 250 */       this.constructionElement.setAttributeNS((String)null, "Id", paramString);
/* 251 */       this.constructionElement.setIdAttributeNS((String)null, "Id", true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 261 */     return this.constructionElement.getAttributeNS((String)null, "Id");
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
/*     */   
/*     */   public boolean verifyReferences() throws MissingResourceFailureException, XMLSecurityException {
/* 285 */     return verifyReferences(false);
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
/*     */ 
/*     */   
/*     */   public boolean verifyReferences(boolean paramBoolean) throws MissingResourceFailureException, XMLSecurityException {
/* 310 */     if (this.referencesEl == null) {
/* 311 */       this
/* 312 */         .referencesEl = XMLUtils.selectDsNodes(this.constructionElement
/* 313 */           .getFirstChild(), "Reference");
/*     */     }
/*     */     
/* 316 */     if (log.isLoggable(Level.FINE)) {
/* 317 */       log.log(Level.FINE, "verify " + this.referencesEl.length + " References");
/* 318 */       log.log(Level.FINE, "I am " + (paramBoolean ? "" : "not") + " requested to follow nested Manifests");
/*     */     } 
/*     */     
/* 321 */     if (this.referencesEl.length == 0) {
/* 322 */       throw new XMLSecurityException("empty");
/*     */     }
/* 324 */     if (this.secureValidation && this.referencesEl.length > 30) {
/* 325 */       Object[] arrayOfObject = { Integer.valueOf(this.referencesEl.length), Integer.valueOf(30) };
/*     */       
/* 327 */       throw new XMLSecurityException("signature.tooManyReferences", arrayOfObject);
/*     */     } 
/*     */     
/* 330 */     this.verificationResults = new boolean[this.referencesEl.length];
/* 331 */     boolean bool = true;
/* 332 */     for (byte b = 0; b < this.referencesEl.length; b++) {
/* 333 */       Reference reference = new Reference(this.referencesEl[b], this.baseURI, this, this.secureValidation);
/*     */ 
/*     */       
/* 336 */       this.references.set(b, reference);
/*     */ 
/*     */       
/*     */       try {
/* 340 */         boolean bool1 = reference.verify();
/*     */         
/* 342 */         setVerificationResult(b, bool1);
/*     */         
/* 344 */         if (!bool1) {
/* 345 */           bool = false;
/*     */         }
/* 347 */         if (log.isLoggable(Level.FINE)) {
/* 348 */           log.log(Level.FINE, "The Reference has Type " + reference.getType());
/*     */         }
/*     */ 
/*     */         
/* 352 */         if (bool && paramBoolean && reference.typeIsReferenceToManifest()) {
/* 353 */           if (log.isLoggable(Level.FINE)) {
/* 354 */             log.log(Level.FINE, "We have to follow a nested Manifest");
/*     */           }
/*     */ 
/*     */           
/*     */           try {
/* 359 */             XMLSignatureInput xMLSignatureInput = reference.dereferenceURIandPerformTransforms((OutputStream)null);
/* 360 */             Set<Node> set = xMLSignatureInput.getNodeSet();
/* 361 */             Manifest manifest = null;
/* 362 */             Iterator<Node> iterator = set.iterator();
/*     */             
/* 364 */             while (iterator.hasNext()) {
/* 365 */               Node node = iterator.next();
/*     */               
/* 367 */               if (node.getNodeType() == 1 && ((Element)node)
/* 368 */                 .getNamespaceURI().equals("http://www.w3.org/2000/09/xmldsig#") && ((Element)node)
/* 369 */                 .getLocalName().equals("Manifest")) {
/*     */                 
/*     */                 try {
/*     */ 
/*     */                   
/* 374 */                   manifest = new Manifest((Element)node, xMLSignatureInput.getSourceURI(), this.secureValidation);
/*     */                   
/*     */                   break;
/* 377 */                 } catch (XMLSecurityException xMLSecurityException) {
/* 378 */                   if (log.isLoggable(Level.FINE)) {
/* 379 */                     log.log(Level.FINE, xMLSecurityException.getMessage(), xMLSecurityException);
/*     */                   }
/*     */                 } 
/*     */               }
/*     */             } 
/*     */ 
/*     */             
/* 386 */             if (manifest == null)
/*     */             {
/*     */               
/* 389 */               throw new MissingResourceFailureException("empty", reference);
/*     */             }
/*     */             
/* 392 */             manifest.perManifestResolvers = this.perManifestResolvers;
/* 393 */             manifest.resolverProperties = this.resolverProperties;
/*     */ 
/*     */             
/* 396 */             boolean bool2 = manifest.verifyReferences(paramBoolean);
/*     */             
/* 398 */             if (!bool2) {
/* 399 */               bool = false;
/*     */               
/* 401 */               log.log(Level.WARNING, "The nested Manifest was invalid (bad)");
/*     */             }
/* 403 */             else if (log.isLoggable(Level.FINE)) {
/* 404 */               log.log(Level.FINE, "The nested Manifest was valid (good)");
/*     */             }
/*     */           
/* 407 */           } catch (IOException iOException) {
/* 408 */             throw new ReferenceNotInitializedException("empty", iOException);
/* 409 */           } catch (ParserConfigurationException parserConfigurationException) {
/* 410 */             throw new ReferenceNotInitializedException("empty", parserConfigurationException);
/* 411 */           } catch (SAXException sAXException) {
/* 412 */             throw new ReferenceNotInitializedException("empty", sAXException);
/*     */           } 
/*     */         } 
/* 415 */       } catch (ReferenceNotInitializedException referenceNotInitializedException) {
/* 416 */         Object[] arrayOfObject = { reference.getURI() };
/*     */         
/* 418 */         throw new MissingResourceFailureException("signature.Verification.Reference.NoInput", arrayOfObject, referenceNotInitializedException, reference);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 424 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setVerificationResult(int paramInt, boolean paramBoolean) {
/* 434 */     if (this.verificationResults == null) {
/* 435 */       this.verificationResults = new boolean[getLength()];
/*     */     }
/*     */     
/* 438 */     this.verificationResults[paramInt] = paramBoolean;
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
/*     */   public boolean getVerificationResult(int paramInt) throws XMLSecurityException {
/* 451 */     if (paramInt < 0 || paramInt > getLength() - 1) {
/* 452 */       Object[] arrayOfObject = { Integer.toString(paramInt), Integer.toString(getLength()) };
/*     */ 
/*     */       
/* 455 */       IndexOutOfBoundsException indexOutOfBoundsException = new IndexOutOfBoundsException(I18n.translate("signature.Verification.IndexOutOfBounds", arrayOfObject));
/*     */ 
/*     */       
/* 458 */       throw new XMLSecurityException("generic.EmptyMessage", indexOutOfBoundsException);
/*     */     } 
/*     */     
/* 461 */     if (this.verificationResults == null) {
/*     */       try {
/* 463 */         verifyReferences();
/* 464 */       } catch (Exception exception) {
/* 465 */         throw new XMLSecurityException("generic.EmptyMessage", exception);
/*     */       } 
/*     */     }
/*     */     
/* 469 */     return this.verificationResults[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addResourceResolver(ResourceResolver paramResourceResolver) {
/* 480 */     if (paramResourceResolver == null) {
/*     */       return;
/*     */     }
/* 483 */     if (this.perManifestResolvers == null) {
/* 484 */       this.perManifestResolvers = new ArrayList<>();
/*     */     }
/* 486 */     this.perManifestResolvers.add(paramResourceResolver);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addResourceResolver(ResourceResolverSpi paramResourceResolverSpi) {
/* 497 */     if (paramResourceResolverSpi == null) {
/*     */       return;
/*     */     }
/* 500 */     if (this.perManifestResolvers == null) {
/* 501 */       this.perManifestResolvers = new ArrayList<>();
/*     */     }
/* 503 */     this.perManifestResolvers.add(new ResourceResolver(paramResourceResolverSpi));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ResourceResolver> getPerManifestResolvers() {
/* 511 */     return this.perManifestResolvers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> getResolverProperties() {
/* 519 */     return this.resolverProperties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setResolverProperty(String paramString1, String paramString2) {
/* 530 */     if (this.resolverProperties == null) {
/* 531 */       this.resolverProperties = new HashMap<>(10);
/*     */     }
/* 533 */     this.resolverProperties.put(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getResolverProperty(String paramString) {
/* 543 */     return this.resolverProperties.get(paramString);
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
/*     */   public byte[] getSignedContentItem(int paramInt) throws XMLSignatureException {
/*     */     try {
/* 556 */       return getReferencedContentAfterTransformsItem(paramInt).getBytes();
/* 557 */     } catch (IOException iOException) {
/* 558 */       throw new XMLSignatureException("empty", iOException);
/* 559 */     } catch (CanonicalizationException canonicalizationException) {
/* 560 */       throw new XMLSignatureException("empty", canonicalizationException);
/* 561 */     } catch (InvalidCanonicalizerException invalidCanonicalizerException) {
/* 562 */       throw new XMLSignatureException("empty", invalidCanonicalizerException);
/* 563 */     } catch (XMLSecurityException xMLSecurityException) {
/* 564 */       throw new XMLSignatureException("empty", xMLSecurityException);
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
/*     */   public XMLSignatureInput getReferencedContentBeforeTransformsItem(int paramInt) throws XMLSecurityException {
/* 577 */     return item(paramInt).getContentsBeforeTransformation();
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
/*     */   public XMLSignatureInput getReferencedContentAfterTransformsItem(int paramInt) throws XMLSecurityException {
/* 589 */     return item(paramInt).getContentsAfterTransformation();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSignedContentLength() {
/* 598 */     return getLength();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseLocalName() {
/* 607 */     return "Manifest";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/signature/Manifest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */