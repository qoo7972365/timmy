/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.Init;
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.Base64;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Key;
/*     */ import java.security.Provider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.crypto.KeySelector;
/*     */ import javax.xml.crypto.KeySelectorException;
/*     */ import javax.xml.crypto.KeySelectorResult;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.XMLStructure;
/*     */ import javax.xml.crypto.dom.DOMCryptoContext;
/*     */ import javax.xml.crypto.dsig.Manifest;
/*     */ import javax.xml.crypto.dsig.Reference;
/*     */ import javax.xml.crypto.dsig.SignatureMethod;
/*     */ import javax.xml.crypto.dsig.SignedInfo;
/*     */ import javax.xml.crypto.dsig.Transform;
/*     */ import javax.xml.crypto.dsig.XMLObject;
/*     */ import javax.xml.crypto.dsig.XMLSignContext;
/*     */ import javax.xml.crypto.dsig.XMLSignature;
/*     */ import javax.xml.crypto.dsig.XMLSignatureException;
/*     */ import javax.xml.crypto.dsig.XMLValidateContext;
/*     */ import javax.xml.crypto.dsig.dom.DOMSignContext;
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyInfo;
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
/*     */ public final class DOMXMLSignature
/*     */   extends DOMStructure
/*     */   implements XMLSignature
/*     */ {
/*  71 */   private static Logger log = Logger.getLogger("org.jcp.xml.dsig.internal.dom");
/*     */   private String id;
/*     */   private XMLSignature.SignatureValue sv;
/*     */   private KeyInfo ki;
/*     */   private List<XMLObject> objects;
/*     */   private SignedInfo si;
/*  77 */   private Document ownerDoc = null;
/*  78 */   private Element localSigElem = null;
/*  79 */   private Element sigElem = null;
/*     */   private boolean validationStatus;
/*     */   private boolean validated = false;
/*     */   private KeySelectorResult ksr;
/*     */   private HashMap<String, XMLStructure> signatureIdMap;
/*     */   
/*     */   static {
/*  86 */     Init.init();
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
/*     */   public DOMXMLSignature(SignedInfo paramSignedInfo, KeyInfo paramKeyInfo, List<? extends XMLObject> paramList, String paramString1, String paramString2) {
/* 106 */     if (paramSignedInfo == null) {
/* 107 */       throw new NullPointerException("signedInfo cannot be null");
/*     */     }
/* 109 */     this.si = paramSignedInfo;
/* 110 */     this.id = paramString1;
/* 111 */     this.sv = new DOMSignatureValue(paramString2);
/* 112 */     if (paramList == null) {
/* 113 */       this.objects = Collections.emptyList();
/*     */     } else {
/* 115 */       this
/* 116 */         .objects = Collections.unmodifiableList(new ArrayList<>(paramList)); byte b; int i;
/* 117 */       for (b = 0, i = this.objects.size(); b < i; b++) {
/* 118 */         if (!(this.objects.get(b) instanceof XMLObject)) {
/* 119 */           throw new ClassCastException("objs[" + b + "] is not an XMLObject");
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 124 */     this.ki = paramKeyInfo;
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
/*     */   public DOMXMLSignature(Element paramElement, XMLCryptoContext paramXMLCryptoContext, Provider paramProvider) throws MarshalException {
/* 137 */     this.localSigElem = paramElement;
/* 138 */     this.ownerDoc = this.localSigElem.getOwnerDocument();
/*     */ 
/*     */     
/* 141 */     this.id = DOMUtils.getAttributeValue(this.localSigElem, "Id");
/*     */ 
/*     */     
/* 144 */     Element element1 = DOMUtils.getFirstChildElement(this.localSigElem, "SignedInfo");
/*     */     
/* 146 */     this.si = new DOMSignedInfo(element1, paramXMLCryptoContext, paramProvider);
/*     */ 
/*     */     
/* 149 */     Element element2 = DOMUtils.getNextSiblingElement(element1, "SignatureValue");
/*     */     
/* 151 */     this.sv = new DOMSignatureValue(element2, paramXMLCryptoContext);
/*     */ 
/*     */     
/* 154 */     Element element3 = DOMUtils.getNextSiblingElement(element2);
/* 155 */     if (element3 != null && element3.getLocalName().equals("KeyInfo")) {
/* 156 */       this.ki = new DOMKeyInfo(element3, paramXMLCryptoContext, paramProvider);
/* 157 */       element3 = DOMUtils.getNextSiblingElement(element3);
/*     */     } 
/*     */ 
/*     */     
/* 161 */     if (element3 == null) {
/* 162 */       this.objects = Collections.emptyList();
/*     */     } else {
/* 164 */       ArrayList<DOMXMLObject> arrayList = new ArrayList();
/* 165 */       while (element3 != null) {
/* 166 */         String str = element3.getLocalName();
/* 167 */         if (!str.equals("Object")) {
/* 168 */           throw new MarshalException("Invalid element name: " + str + ", expected KeyInfo or Object");
/*     */         }
/*     */         
/* 171 */         arrayList.add(new DOMXMLObject(element3, paramXMLCryptoContext, paramProvider));
/*     */         
/* 173 */         element3 = DOMUtils.getNextSiblingElement(element3);
/*     */       } 
/* 175 */       this.objects = Collections.unmodifiableList((List)arrayList);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getId() {
/* 180 */     return this.id;
/*     */   }
/*     */   
/*     */   public KeyInfo getKeyInfo() {
/* 184 */     return this.ki;
/*     */   }
/*     */   
/*     */   public SignedInfo getSignedInfo() {
/* 188 */     return this.si;
/*     */   }
/*     */   
/*     */   public List getObjects() {
/* 192 */     return this.objects;
/*     */   }
/*     */   
/*     */   public XMLSignature.SignatureValue getSignatureValue() {
/* 196 */     return this.sv;
/*     */   }
/*     */   
/*     */   public KeySelectorResult getKeySelectorResult() {
/* 200 */     return this.ksr;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
/* 206 */     marshal(paramNode, null, paramString, paramDOMCryptoContext);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(Node paramNode1, Node paramNode2, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
/* 213 */     this.ownerDoc = DOMUtils.getOwnerDocument(paramNode1);
/* 214 */     this.sigElem = DOMUtils.createElement(this.ownerDoc, "Signature", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */ 
/*     */ 
/*     */     
/* 218 */     if (paramString == null || paramString.length() == 0) {
/* 219 */       this.sigElem.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "http://www.w3.org/2000/09/xmldsig#");
/*     */     } else {
/*     */       
/* 222 */       this.sigElem.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + paramString, "http://www.w3.org/2000/09/xmldsig#");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 227 */     ((DOMSignedInfo)this.si).marshal(this.sigElem, paramString, paramDOMCryptoContext);
/*     */ 
/*     */     
/* 230 */     ((DOMSignatureValue)this.sv).marshal(this.sigElem, paramString, paramDOMCryptoContext);
/*     */ 
/*     */     
/* 233 */     if (this.ki != null) {
/* 234 */       ((DOMKeyInfo)this.ki).marshal(this.sigElem, null, paramString, paramDOMCryptoContext);
/*     */     }
/*     */     byte b;
/*     */     int i;
/* 238 */     for (b = 0, i = this.objects.size(); b < i; b++) {
/* 239 */       ((DOMXMLObject)this.objects.get(b)).marshal(this.sigElem, paramString, paramDOMCryptoContext);
/*     */     }
/*     */ 
/*     */     
/* 243 */     DOMUtils.setAttributeID(this.sigElem, "Id", this.id);
/*     */     
/* 245 */     paramNode1.insertBefore(this.sigElem, paramNode2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean validate(XMLValidateContext paramXMLValidateContext) throws XMLSignatureException {
/*     */     int i;
/* 251 */     if (paramXMLValidateContext == null) {
/* 252 */       throw new NullPointerException("validateContext is null");
/*     */     }
/*     */     
/* 255 */     if (!(paramXMLValidateContext instanceof javax.xml.crypto.dsig.dom.DOMValidateContext)) {
/* 256 */       throw new ClassCastException("validateContext must be of type DOMValidateContext");
/*     */     }
/*     */ 
/*     */     
/* 260 */     if (this.validated) {
/* 261 */       return this.validationStatus;
/*     */     }
/*     */ 
/*     */     
/* 265 */     boolean bool1 = this.sv.validate(paramXMLValidateContext);
/* 266 */     if (!bool1) {
/* 267 */       this.validationStatus = false;
/* 268 */       this.validated = true;
/* 269 */       return this.validationStatus;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 274 */     List<Reference> list = this.si.getReferences();
/* 275 */     boolean bool2 = true; int j, k;
/* 276 */     for (j = 0, k = list.size(); bool2 && j < k; j++) {
/* 277 */       Reference reference = list.get(j);
/* 278 */       boolean bool = reference.validate(paramXMLValidateContext);
/* 279 */       if (log.isLoggable(Level.FINE)) {
/* 280 */         log.log(Level.FINE, "Reference[" + reference.getURI() + "] is valid: " + bool);
/*     */       }
/* 282 */       bool2 &= bool;
/*     */     } 
/* 284 */     if (!bool2) {
/* 285 */       if (log.isLoggable(Level.FINE)) {
/* 286 */         log.log(Level.FINE, "Couldn't validate the References");
/*     */       }
/* 288 */       this.validationStatus = false;
/* 289 */       this.validated = true;
/* 290 */       return this.validationStatus;
/*     */     } 
/*     */ 
/*     */     
/* 294 */     j = 1;
/* 295 */     if (Boolean.TRUE.equals(paramXMLValidateContext
/* 296 */         .getProperty("org.jcp.xml.dsig.validateManifests"))) {
/*     */       int m;
/* 298 */       for (k = 0, m = this.objects.size(); j != 0 && k < m; k++) {
/* 299 */         XMLObject xMLObject = this.objects.get(k);
/*     */         
/* 301 */         List<XMLStructure> list1 = xMLObject.getContent();
/* 302 */         int n = list1.size();
/* 303 */         for (byte b = 0; j != 0 && b < n; b++) {
/* 304 */           XMLStructure xMLStructure = list1.get(b);
/* 305 */           if (xMLStructure instanceof Manifest) {
/* 306 */             if (log.isLoggable(Level.FINE)) {
/* 307 */               log.log(Level.FINE, "validating manifest");
/*     */             }
/* 309 */             Manifest manifest = (Manifest)xMLStructure;
/*     */             
/* 311 */             List<Reference> list2 = manifest.getReferences();
/* 312 */             int i1 = list2.size();
/* 313 */             for (byte b1 = 0; j != 0 && b1 < i1; b1++) {
/* 314 */               Reference reference = list2.get(b1);
/* 315 */               int i2 = reference.validate(paramXMLValidateContext);
/* 316 */               if (log.isLoggable(Level.FINE)) {
/* 317 */                 log.log(Level.FINE, "Manifest ref[" + reference
/* 318 */                     .getURI() + "] is valid: " + i2);
/*     */               }
/*     */               
/* 321 */               i = j & i2;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 328 */     this.validationStatus = i;
/* 329 */     this.validated = true;
/* 330 */     return this.validationStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sign(XMLSignContext paramXMLSignContext) throws MarshalException, XMLSignatureException {
/* 336 */     if (paramXMLSignContext == null) {
/* 337 */       throw new NullPointerException("signContext cannot be null");
/*     */     }
/* 339 */     DOMSignContext dOMSignContext = (DOMSignContext)paramXMLSignContext;
/* 340 */     marshal(dOMSignContext.getParent(), dOMSignContext.getNextSibling(), 
/* 341 */         DOMUtils.getSignaturePrefix(dOMSignContext), dOMSignContext);
/*     */ 
/*     */     
/* 344 */     ArrayList<Reference> arrayList = new ArrayList();
/*     */ 
/*     */ 
/*     */     
/* 348 */     this.signatureIdMap = new HashMap<>();
/* 349 */     this.signatureIdMap.put(this.id, this);
/* 350 */     this.signatureIdMap.put(this.si.getId(), this.si);
/*     */     
/* 352 */     List<Reference> list = this.si.getReferences();
/* 353 */     for (Reference reference : list) {
/* 354 */       this.signatureIdMap.put(reference.getId(), reference);
/*     */     }
/* 356 */     for (XMLObject xMLObject : this.objects) {
/* 357 */       this.signatureIdMap.put(xMLObject.getId(), xMLObject);
/*     */       
/* 359 */       List<XMLStructure> list1 = xMLObject.getContent();
/* 360 */       for (XMLStructure xMLStructure : list1) {
/* 361 */         if (xMLStructure instanceof Manifest) {
/* 362 */           Manifest manifest = (Manifest)xMLStructure;
/* 363 */           this.signatureIdMap.put(manifest.getId(), manifest);
/*     */           
/* 365 */           List<Reference> list2 = manifest.getReferences();
/* 366 */           for (Reference reference : list2) {
/* 367 */             arrayList.add(reference);
/* 368 */             this.signatureIdMap.put(reference.getId(), reference);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 375 */     arrayList.addAll(list);
/*     */ 
/*     */     
/* 378 */     for (Reference reference : arrayList) {
/* 379 */       digestReference((DOMReference)reference, paramXMLSignContext);
/*     */     }
/*     */ 
/*     */     
/* 383 */     for (Reference reference : arrayList) {
/* 384 */       if (((DOMReference)reference).isDigested()) {
/*     */         continue;
/*     */       }
/* 387 */       ((DOMReference)reference).digest(paramXMLSignContext);
/*     */     } 
/*     */     
/* 390 */     Key key = null;
/* 391 */     KeySelectorResult keySelectorResult = null;
/*     */     try {
/* 393 */       keySelectorResult = paramXMLSignContext.getKeySelector().select(this.ki, KeySelector.Purpose.SIGN, this.si
/*     */           
/* 395 */           .getSignatureMethod(), paramXMLSignContext);
/*     */       
/* 397 */       key = keySelectorResult.getKey();
/* 398 */       if (key == null) {
/* 399 */         throw new XMLSignatureException("the keySelector did not find a signing key");
/*     */       }
/*     */     }
/* 402 */     catch (KeySelectorException keySelectorException) {
/* 403 */       throw new XMLSignatureException("cannot find signing key", keySelectorException);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 409 */       byte[] arrayOfByte = ((AbstractDOMSignatureMethod)this.si.getSignatureMethod()).sign(key, this.si, paramXMLSignContext);
/* 410 */       ((DOMSignatureValue)this.sv).setValue(arrayOfByte);
/* 411 */     } catch (InvalidKeyException invalidKeyException) {
/* 412 */       throw new XMLSignatureException(invalidKeyException);
/*     */     } 
/*     */     
/* 415 */     this.localSigElem = this.sigElem;
/* 416 */     this.ksr = keySelectorResult;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 421 */     if (this == paramObject) {
/* 422 */       return true;
/*     */     }
/*     */     
/* 425 */     if (!(paramObject instanceof XMLSignature)) {
/* 426 */       return false;
/*     */     }
/* 428 */     XMLSignature xMLSignature = (XMLSignature)paramObject;
/*     */ 
/*     */     
/* 431 */     boolean bool1 = (this.id == null) ? ((xMLSignature.getId() == null) ? true : false) : this.id.equals(xMLSignature.getId());
/*     */ 
/*     */     
/* 434 */     boolean bool2 = (this.ki == null) ? ((xMLSignature.getKeyInfo() == null) ? true : false) : this.ki.equals(xMLSignature.getKeyInfo());
/*     */     
/* 436 */     return (bool1 && bool2 && this.sv
/* 437 */       .equals(xMLSignature.getSignatureValue()) && this.si
/* 438 */       .equals(xMLSignature.getSignedInfo()) && this.objects
/* 439 */       .equals(xMLSignature.getObjects()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 444 */     int i = 17;
/* 445 */     if (this.id != null) {
/* 446 */       i = 31 * i + this.id.hashCode();
/*     */     }
/* 448 */     if (this.ki != null) {
/* 449 */       i = 31 * i + this.ki.hashCode();
/*     */     }
/* 451 */     i = 31 * i + this.sv.hashCode();
/* 452 */     i = 31 * i + this.si.hashCode();
/* 453 */     i = 31 * i + this.objects.hashCode();
/*     */     
/* 455 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void digestReference(DOMReference paramDOMReference, XMLSignContext paramXMLSignContext) throws XMLSignatureException {
/* 461 */     if (paramDOMReference.isDigested()) {
/*     */       return;
/*     */     }
/*     */     
/* 465 */     String str = paramDOMReference.getURI();
/* 466 */     if (Utils.sameDocumentURI(str)) {
/* 467 */       String str1 = Utils.parseIdFromSameDocumentURI(str);
/* 468 */       if (str1 != null && this.signatureIdMap.containsKey(str1)) {
/* 469 */         XMLStructure xMLStructure = this.signatureIdMap.get(str1);
/* 470 */         if (xMLStructure instanceof DOMReference) {
/* 471 */           digestReference((DOMReference)xMLStructure, paramXMLSignContext);
/* 472 */         } else if (xMLStructure instanceof Manifest) {
/* 473 */           Manifest manifest = (Manifest)xMLStructure;
/* 474 */           List<Reference> list = manifest.getReferences(); byte b; int i;
/* 475 */           for (b = 0, i = list.size(); b < i; b++) {
/* 476 */             digestReference((DOMReference)list.get(b), paramXMLSignContext);
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 484 */       if (str.length() == 0) {
/*     */         
/* 486 */         List list = paramDOMReference.getTransforms();
/* 487 */         for (Transform transform : list) {
/* 488 */           String str2 = transform.getAlgorithm();
/* 489 */           if (str2.equals("http://www.w3.org/TR/1999/REC-xpath-19991116") || str2
/* 490 */             .equals("http://www.w3.org/2002/06/xmldsig-filter2")) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 496 */     paramDOMReference.digest(paramXMLSignContext);
/*     */   }
/*     */   
/*     */   public class DOMSignatureValue
/*     */     extends DOMStructure
/*     */     implements XMLSignature.SignatureValue {
/*     */     private String id;
/*     */     private byte[] value;
/*     */     private String valueBase64;
/*     */     private Element sigValueElem;
/*     */     private boolean validated = false;
/*     */     private boolean validationStatus;
/*     */     
/*     */     DOMSignatureValue(String param1String) {
/* 510 */       this.id = param1String;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     DOMSignatureValue(Element param1Element, XMLCryptoContext param1XMLCryptoContext) throws MarshalException {
/*     */       try {
/* 518 */         this.value = Base64.decode(param1Element);
/* 519 */       } catch (Base64DecodingException base64DecodingException) {
/* 520 */         throw new MarshalException(base64DecodingException);
/*     */       } 
/*     */       
/* 523 */       Attr attr = param1Element.getAttributeNodeNS((String)null, "Id");
/* 524 */       if (attr != null) {
/* 525 */         this.id = attr.getValue();
/* 526 */         param1Element.setIdAttributeNode(attr, true);
/*     */       } else {
/* 528 */         this.id = null;
/*     */       } 
/* 530 */       this.sigValueElem = param1Element;
/*     */     }
/*     */     
/*     */     public String getId() {
/* 534 */       return this.id;
/*     */     }
/*     */     
/*     */     public byte[] getValue() {
/* 538 */       return (this.value == null) ? null : (byte[])this.value.clone();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean validate(XMLValidateContext param1XMLValidateContext) throws XMLSignatureException {
/*     */       KeySelectorResult keySelectorResult;
/* 544 */       if (param1XMLValidateContext == null) {
/* 545 */         throw new NullPointerException("context cannot be null");
/*     */       }
/*     */       
/* 548 */       if (this.validated) {
/* 549 */         return this.validationStatus;
/*     */       }
/*     */ 
/*     */       
/* 553 */       SignatureMethod signatureMethod = DOMXMLSignature.this.si.getSignatureMethod();
/* 554 */       Key key = null;
/*     */ 
/*     */       
/*     */       try {
/* 558 */         keySelectorResult = param1XMLValidateContext.getKeySelector().select(DOMXMLSignature.this.ki, KeySelector.Purpose.VERIFY, signatureMethod, param1XMLValidateContext);
/* 559 */         key = keySelectorResult.getKey();
/* 560 */         if (key == null) {
/* 561 */           throw new XMLSignatureException("the keyselector did not find a validation key");
/*     */         }
/*     */       }
/* 564 */       catch (KeySelectorException keySelectorException) {
/* 565 */         throw new XMLSignatureException("cannot find validation key", keySelectorException);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 571 */         this
/* 572 */           .validationStatus = ((AbstractDOMSignatureMethod)signatureMethod).verify(key, DOMXMLSignature.this.si, this.value, param1XMLValidateContext);
/* 573 */       } catch (Exception exception) {
/* 574 */         throw new XMLSignatureException(exception);
/*     */       } 
/*     */       
/* 577 */       this.validated = true;
/* 578 */       DOMXMLSignature.this.ksr = keySelectorResult;
/* 579 */       return this.validationStatus;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 584 */       if (this == param1Object) {
/* 585 */         return true;
/*     */       }
/*     */       
/* 588 */       if (!(param1Object instanceof XMLSignature.SignatureValue)) {
/* 589 */         return false;
/*     */       }
/* 591 */       XMLSignature.SignatureValue signatureValue = (XMLSignature.SignatureValue)param1Object;
/*     */       
/* 593 */       return (this.id == null) ? (
/* 594 */         (signatureValue.getId() == null)) : this.id.equals(signatureValue.getId());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 602 */       int i = 17;
/* 603 */       if (this.id != null) {
/* 604 */         i = 31 * i + this.id.hashCode();
/*     */       }
/*     */       
/* 607 */       return i;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void marshal(Node param1Node, String param1String, DOMCryptoContext param1DOMCryptoContext) throws MarshalException {
/* 615 */       this.sigValueElem = DOMUtils.createElement(DOMXMLSignature.this.ownerDoc, "SignatureValue", "http://www.w3.org/2000/09/xmldsig#", param1String);
/*     */       
/* 617 */       if (this.valueBase64 != null) {
/* 618 */         this.sigValueElem.appendChild(DOMXMLSignature.this.ownerDoc.createTextNode(this.valueBase64));
/*     */       }
/*     */ 
/*     */       
/* 622 */       DOMUtils.setAttributeID(this.sigValueElem, "Id", this.id);
/* 623 */       param1Node.appendChild(this.sigValueElem);
/*     */     }
/*     */     
/*     */     void setValue(byte[] param1ArrayOfbyte) {
/* 627 */       this.value = param1ArrayOfbyte;
/* 628 */       this.valueBase64 = Base64.encode(param1ArrayOfbyte);
/* 629 */       this.sigValueElem.appendChild(DOMXMLSignature.this.ownerDoc.createTextNode(this.valueBase64));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMXMLSignature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */