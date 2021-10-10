/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*     */ import com.sun.org.apache.xml.internal.security.utils.Base64;
/*     */ import com.sun.org.apache.xml.internal.security.utils.UnsyncBufferedOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.security.AccessController;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.Provider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.NodeSetData;
/*     */ import javax.xml.crypto.OctetStreamData;
/*     */ import javax.xml.crypto.URIDereferencer;
/*     */ import javax.xml.crypto.URIReferenceException;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.dom.DOMCryptoContext;
/*     */ import javax.xml.crypto.dom.DOMURIReference;
/*     */ import javax.xml.crypto.dsig.DigestMethod;
/*     */ import javax.xml.crypto.dsig.Reference;
/*     */ import javax.xml.crypto.dsig.Transform;
/*     */ import javax.xml.crypto.dsig.TransformException;
/*     */ import javax.xml.crypto.dsig.TransformService;
/*     */ import javax.xml.crypto.dsig.XMLSignContext;
/*     */ import javax.xml.crypto.dsig.XMLSignatureException;
/*     */ import javax.xml.crypto.dsig.XMLValidateContext;
/*     */ import org.jcp.xml.dsig.internal.DigesterOutputStream;
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
/*     */ public final class DOMReference
/*     */   extends DOMStructure
/*     */   implements Reference, DOMURIReference
/*     */ {
/*  76 */   private static boolean useC14N11 = ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() {
/*     */         public Boolean run() {
/*  78 */           return Boolean.valueOf(
/*  79 */               Boolean.getBoolean("com.sun.org.apache.xml.internal.security.useC14N11"));
/*     */         }
/*     */       })).booleanValue();
/*     */ 
/*     */   
/*  84 */   private static Logger log = Logger.getLogger("org.jcp.xml.dsig.internal.dom");
/*     */   
/*     */   private final DigestMethod digestMethod;
/*     */   
/*     */   private final String id;
/*     */   
/*     */   private final List<Transform> transforms;
/*     */   
/*     */   private List<Transform> allTransforms;
/*     */   
/*     */   private final Data appliedTransformData;
/*     */   
/*     */   private Attr here;
/*     */   
/*     */   private final String uri;
/*     */   
/*     */   private final String type;
/*     */   
/*     */   private byte[] digestValue;
/*     */   
/*     */   private byte[] calcDigestValue;
/*     */   
/*     */   private Element refElem;
/*     */   
/*     */   private boolean digested = false;
/*     */   
/*     */   private boolean validated = false;
/*     */   
/*     */   private boolean validationStatus;
/*     */   
/*     */   private Data derefData;
/*     */   
/*     */   private InputStream dis;
/*     */   
/*     */   private MessageDigest md;
/*     */   
/*     */   private Provider provider;
/*     */ 
/*     */   
/*     */   public DOMReference(String paramString1, String paramString2, DigestMethod paramDigestMethod, List<? extends Transform> paramList, String paramString3, Provider paramProvider) {
/* 124 */     this(paramString1, paramString2, paramDigestMethod, null, null, paramList, paramString3, null, paramProvider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DOMReference(String paramString1, String paramString2, DigestMethod paramDigestMethod, List<? extends Transform> paramList1, Data paramData, List<? extends Transform> paramList2, String paramString3, Provider paramProvider) {
/* 132 */     this(paramString1, paramString2, paramDigestMethod, paramList1, paramData, paramList2, paramString3, null, paramProvider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DOMReference(String paramString1, String paramString2, DigestMethod paramDigestMethod, List<? extends Transform> paramList1, Data paramData, List<? extends Transform> paramList2, String paramString3, byte[] paramArrayOfbyte, Provider paramProvider) {
/* 141 */     if (paramDigestMethod == null) {
/* 142 */       throw new NullPointerException("DigestMethod must be non-null");
/*     */     }
/* 144 */     if (paramList1 == null) {
/* 145 */       this.allTransforms = new ArrayList<>();
/*     */     } else {
/* 147 */       this.allTransforms = new ArrayList<>(paramList1); byte b; int i;
/* 148 */       for (b = 0, i = this.allTransforms.size(); b < i; b++) {
/* 149 */         if (!(this.allTransforms.get(b) instanceof Transform)) {
/* 150 */           throw new ClassCastException("appliedTransforms[" + b + "] is not a valid type");
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 155 */     if (paramList2 == null) {
/* 156 */       this.transforms = Collections.emptyList();
/*     */     } else {
/* 158 */       this.transforms = new ArrayList<>(paramList2); byte b; int i;
/* 159 */       for (b = 0, i = this.transforms.size(); b < i; b++) {
/* 160 */         if (!(this.transforms.get(b) instanceof Transform)) {
/* 161 */           throw new ClassCastException("transforms[" + b + "] is not a valid type");
/*     */         }
/*     */       } 
/*     */       
/* 165 */       this.allTransforms.addAll(this.transforms);
/*     */     } 
/* 167 */     this.digestMethod = paramDigestMethod;
/* 168 */     this.uri = paramString1;
/* 169 */     if (paramString1 != null && !paramString1.equals("")) {
/*     */       try {
/* 171 */         new URI(paramString1);
/* 172 */       } catch (URISyntaxException uRISyntaxException) {
/* 173 */         throw new IllegalArgumentException(uRISyntaxException.getMessage());
/*     */       } 
/*     */     }
/* 176 */     this.type = paramString2;
/* 177 */     this.id = paramString3;
/* 178 */     if (paramArrayOfbyte != null) {
/* 179 */       this.digestValue = (byte[])paramArrayOfbyte.clone();
/* 180 */       this.digested = true;
/*     */     } 
/* 182 */     this.appliedTransformData = paramData;
/* 183 */     this.provider = paramProvider;
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
/*     */   public DOMReference(Element paramElement, XMLCryptoContext paramXMLCryptoContext, Provider paramProvider) throws MarshalException {
/* 195 */     boolean bool = Utils.secureValidation(paramXMLCryptoContext);
/*     */ 
/*     */     
/* 198 */     Element element1 = DOMUtils.getFirstChildElement(paramElement);
/* 199 */     ArrayList<DOMTransform> arrayList = new ArrayList(5);
/* 200 */     if (element1.getLocalName().equals("Transforms")) {
/* 201 */       Element element = DOMUtils.getFirstChildElement(element1, "Transform");
/*     */       
/* 203 */       arrayList.add(new DOMTransform(element, paramXMLCryptoContext, paramProvider));
/* 204 */       element = DOMUtils.getNextSiblingElement(element);
/* 205 */       while (element != null) {
/* 206 */         String str1 = element.getLocalName();
/* 207 */         if (!str1.equals("Transform")) {
/* 208 */           throw new MarshalException("Invalid element name: " + str1 + ", expected Transform");
/*     */         }
/*     */ 
/*     */         
/* 212 */         arrayList
/* 213 */           .add(new DOMTransform(element, paramXMLCryptoContext, paramProvider));
/* 214 */         if (bool && Policy.restrictNumTransforms(arrayList.size())) {
/* 215 */           String str2 = "A maximum of " + Policy.maxTransforms() + " transforms per Reference are allowed when secure validation is enabled";
/*     */ 
/*     */           
/* 218 */           throw new MarshalException(str2);
/*     */         } 
/* 220 */         element = DOMUtils.getNextSiblingElement(element);
/*     */       } 
/* 222 */       element1 = DOMUtils.getNextSiblingElement(element1);
/*     */     } 
/* 224 */     if (!element1.getLocalName().equals("DigestMethod")) {
/* 225 */       throw new MarshalException("Invalid element name: " + element1
/* 226 */           .getLocalName() + ", expected DigestMethod");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 231 */     Element element2 = element1;
/* 232 */     this.digestMethod = DOMDigestMethod.unmarshal(element2);
/* 233 */     String str = this.digestMethod.getAlgorithm();
/* 234 */     if (bool && Policy.restrictAlg(str)) {
/* 235 */       throw new MarshalException("It is forbidden to use algorithm " + str + " when secure validation is enabled");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 242 */     Element element3 = DOMUtils.getNextSiblingElement(element2, "DigestValue");
/*     */     try {
/* 244 */       this.digestValue = Base64.decode(element3);
/* 245 */     } catch (Base64DecodingException base64DecodingException) {
/* 246 */       throw new MarshalException(base64DecodingException);
/*     */     } 
/*     */ 
/*     */     
/* 250 */     if (DOMUtils.getNextSiblingElement(element3) != null) {
/* 251 */       throw new MarshalException("Unexpected element after DigestValue element");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 256 */     this.uri = DOMUtils.getAttributeValue(paramElement, "URI");
/*     */     
/* 258 */     Attr attr = paramElement.getAttributeNodeNS((String)null, "Id");
/* 259 */     if (attr != null) {
/* 260 */       this.id = attr.getValue();
/* 261 */       paramElement.setIdAttributeNode(attr, true);
/*     */     } else {
/* 263 */       this.id = null;
/*     */     } 
/*     */     
/* 266 */     this.type = DOMUtils.getAttributeValue(paramElement, "Type");
/* 267 */     this.here = paramElement.getAttributeNodeNS((String)null, "URI");
/* 268 */     this.refElem = paramElement;
/* 269 */     this.transforms = (List)arrayList;
/* 270 */     this.allTransforms = (List)arrayList;
/* 271 */     this.appliedTransformData = null;
/* 272 */     this.provider = paramProvider;
/*     */   }
/*     */   
/*     */   public DigestMethod getDigestMethod() {
/* 276 */     return this.digestMethod;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 280 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getURI() {
/* 284 */     return this.uri;
/*     */   }
/*     */   
/*     */   public String getType() {
/* 288 */     return this.type;
/*     */   }
/*     */   
/*     */   public List getTransforms() {
/* 292 */     return Collections.unmodifiableList(this.allTransforms);
/*     */   }
/*     */   
/*     */   public byte[] getDigestValue() {
/* 296 */     return (this.digestValue == null) ? null : (byte[])this.digestValue.clone();
/*     */   }
/*     */   
/*     */   public byte[] getCalculatedDigestValue() {
/* 300 */     return (this.calcDigestValue == null) ? null : (byte[])this.calcDigestValue
/* 301 */       .clone();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
/* 307 */     if (log.isLoggable(Level.FINE)) {
/* 308 */       log.log(Level.FINE, "Marshalling Reference");
/*     */     }
/* 310 */     Document document = DOMUtils.getOwnerDocument(paramNode);
/*     */     
/* 312 */     this.refElem = DOMUtils.createElement(document, "Reference", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */ 
/*     */ 
/*     */     
/* 316 */     DOMUtils.setAttributeID(this.refElem, "Id", this.id);
/* 317 */     DOMUtils.setAttribute(this.refElem, "URI", this.uri);
/* 318 */     DOMUtils.setAttribute(this.refElem, "Type", this.type);
/*     */ 
/*     */     
/* 321 */     if (!this.allTransforms.isEmpty()) {
/* 322 */       Element element1 = DOMUtils.createElement(document, "Transforms", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */ 
/*     */ 
/*     */       
/* 326 */       this.refElem.appendChild(element1);
/* 327 */       for (Transform transform : this.allTransforms) {
/* 328 */         ((DOMStructure)transform).marshal(element1, paramString, paramDOMCryptoContext);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 334 */     ((DOMDigestMethod)this.digestMethod).marshal(this.refElem, paramString, paramDOMCryptoContext);
/*     */ 
/*     */     
/* 337 */     if (log.isLoggable(Level.FINE)) {
/* 338 */       log.log(Level.FINE, "Adding digestValueElem");
/*     */     }
/* 340 */     Element element = DOMUtils.createElement(document, "DigestValue", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */ 
/*     */ 
/*     */     
/* 344 */     if (this.digestValue != null) {
/* 345 */       element
/* 346 */         .appendChild(document.createTextNode(Base64.encode(this.digestValue)));
/*     */     }
/* 348 */     this.refElem.appendChild(element);
/*     */     
/* 350 */     paramNode.appendChild(this.refElem);
/* 351 */     this.here = this.refElem.getAttributeNodeNS((String)null, "URI");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void digest(XMLSignContext paramXMLSignContext) throws XMLSignatureException {
/* 357 */     Data data = null;
/* 358 */     if (this.appliedTransformData == null) {
/* 359 */       data = dereference(paramXMLSignContext);
/*     */     } else {
/* 361 */       data = this.appliedTransformData;
/*     */     } 
/* 363 */     this.digestValue = transform(data, paramXMLSignContext);
/*     */ 
/*     */     
/* 366 */     String str = Base64.encode(this.digestValue);
/* 367 */     if (log.isLoggable(Level.FINE)) {
/* 368 */       log.log(Level.FINE, "Reference object uri = " + this.uri);
/*     */     }
/* 370 */     Element element = DOMUtils.getLastChildElement(this.refElem);
/* 371 */     if (element == null) {
/* 372 */       throw new XMLSignatureException("DigestValue element expected");
/*     */     }
/* 374 */     DOMUtils.removeAllChildren(element);
/* 375 */     element
/* 376 */       .appendChild(this.refElem.getOwnerDocument().createTextNode(str));
/*     */     
/* 378 */     this.digested = true;
/* 379 */     if (log.isLoggable(Level.FINE)) {
/* 380 */       log.log(Level.FINE, "Reference digesting completed");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validate(XMLValidateContext paramXMLValidateContext) throws XMLSignatureException {
/* 387 */     if (paramXMLValidateContext == null) {
/* 388 */       throw new NullPointerException("validateContext cannot be null");
/*     */     }
/* 390 */     if (this.validated) {
/* 391 */       return this.validationStatus;
/*     */     }
/* 393 */     Data data = dereference(paramXMLValidateContext);
/* 394 */     this.calcDigestValue = transform(data, paramXMLValidateContext);
/*     */     
/* 396 */     if (log.isLoggable(Level.FINE)) {
/* 397 */       log.log(Level.FINE, "Expected digest: " + Base64.encode(this.digestValue));
/* 398 */       log.log(Level.FINE, "Actual digest: " + Base64.encode(this.calcDigestValue));
/*     */     } 
/*     */     
/* 401 */     this.validationStatus = Arrays.equals(this.digestValue, this.calcDigestValue);
/* 402 */     this.validated = true;
/* 403 */     return this.validationStatus;
/*     */   }
/*     */   
/*     */   public Data getDereferencedData() {
/* 407 */     return this.derefData;
/*     */   }
/*     */   
/*     */   public InputStream getDigestInputStream() {
/* 411 */     return this.dis;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Data dereference(XMLCryptoContext paramXMLCryptoContext) throws XMLSignatureException {
/* 417 */     Data data = null;
/*     */ 
/*     */     
/* 420 */     URIDereferencer uRIDereferencer = paramXMLCryptoContext.getURIDereferencer();
/* 421 */     if (uRIDereferencer == null) {
/* 422 */       uRIDereferencer = DOMURIDereferencer.INSTANCE;
/*     */     }
/*     */     try {
/* 425 */       data = uRIDereferencer.dereference(this, paramXMLCryptoContext);
/* 426 */       if (log.isLoggable(Level.FINE)) {
/* 427 */         log.log(Level.FINE, "URIDereferencer class name: " + uRIDereferencer.getClass().getName());
/* 428 */         log.log(Level.FINE, "Data class name: " + data.getClass().getName());
/*     */       } 
/* 430 */     } catch (URIReferenceException uRIReferenceException) {
/* 431 */       throw new XMLSignatureException(uRIReferenceException);
/*     */     } 
/*     */     
/* 434 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] transform(Data paramData, XMLCryptoContext paramXMLCryptoContext) throws XMLSignatureException {
/*     */     DigesterOutputStream digesterOutputStream;
/* 441 */     if (this.md == null) {
/*     */       try {
/* 443 */         this
/* 444 */           .md = MessageDigest.getInstance(((DOMDigestMethod)this.digestMethod).getMessageDigestAlgorithm());
/* 445 */       } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 446 */         throw new XMLSignatureException(noSuchAlgorithmException);
/*     */       } 
/*     */     }
/* 449 */     this.md.reset();
/*     */ 
/*     */     
/* 452 */     Boolean bool = (Boolean)paramXMLCryptoContext.getProperty("javax.xml.crypto.dsig.cacheReference");
/* 453 */     if (bool != null && bool.booleanValue()) {
/* 454 */       this.derefData = copyDerefData(paramData);
/* 455 */       digesterOutputStream = new DigesterOutputStream(this.md, true);
/*     */     } else {
/* 457 */       digesterOutputStream = new DigesterOutputStream(this.md);
/*     */     } 
/* 459 */     UnsyncBufferedOutputStream unsyncBufferedOutputStream = null;
/* 460 */     Data data = paramData;
/*     */     try {
/* 462 */       unsyncBufferedOutputStream = new UnsyncBufferedOutputStream(digesterOutputStream); byte b; int i;
/* 463 */       for (b = 0, i = this.transforms.size(); b < i; b++) {
/* 464 */         DOMTransform dOMTransform = (DOMTransform)this.transforms.get(b);
/* 465 */         if (b < i - 1) {
/* 466 */           data = dOMTransform.transform(data, paramXMLCryptoContext);
/*     */         } else {
/* 468 */           data = dOMTransform.transform(data, paramXMLCryptoContext, unsyncBufferedOutputStream);
/*     */         } 
/*     */       } 
/*     */       
/* 472 */       if (data != null) {
/*     */         XMLSignatureInput xMLSignatureInput;
/*     */ 
/*     */         
/* 476 */         boolean bool1 = useC14N11;
/* 477 */         String str = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
/* 478 */         if (paramXMLCryptoContext instanceof XMLSignContext) {
/* 479 */           if (!bool1) {
/*     */             
/* 481 */             Boolean bool2 = (Boolean)paramXMLCryptoContext.getProperty("com.sun.org.apache.xml.internal.security.useC14N11");
/* 482 */             bool1 = (bool2 != null && bool2.booleanValue());
/* 483 */             if (bool1) {
/* 484 */               str = "http://www.w3.org/2006/12/xml-c14n11";
/*     */             }
/*     */           } else {
/* 487 */             str = "http://www.w3.org/2006/12/xml-c14n11";
/*     */           } 
/*     */         }
/* 490 */         if (data instanceof ApacheData) {
/* 491 */           xMLSignatureInput = ((ApacheData)data).getXMLSignatureInput();
/* 492 */         } else if (data instanceof OctetStreamData) {
/*     */           
/* 494 */           xMLSignatureInput = new XMLSignatureInput(((OctetStreamData)data).getOctetStream());
/* 495 */         } else if (data instanceof NodeSetData) {
/* 496 */           TransformService transformService = null;
/* 497 */           if (this.provider == null) {
/* 498 */             transformService = TransformService.getInstance(str, "DOM");
/*     */           } else {
/*     */             try {
/* 501 */               transformService = TransformService.getInstance(str, "DOM", this.provider);
/* 502 */             } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 503 */               transformService = TransformService.getInstance(str, "DOM");
/*     */             } 
/*     */           } 
/* 506 */           data = transformService.transform(data, paramXMLCryptoContext);
/*     */           
/* 508 */           xMLSignatureInput = new XMLSignatureInput(((OctetStreamData)data).getOctetStream());
/*     */         } else {
/* 510 */           throw new XMLSignatureException("unrecognized Data type");
/*     */         } 
/* 512 */         if (paramXMLCryptoContext instanceof XMLSignContext && bool1 && 
/* 513 */           !xMLSignatureInput.isOctetStream() && !xMLSignatureInput.isOutputStreamSet()) {
/* 514 */           TransformService transformService = null;
/* 515 */           if (this.provider == null) {
/* 516 */             transformService = TransformService.getInstance(str, "DOM");
/*     */           } else {
/*     */             try {
/* 519 */               transformService = TransformService.getInstance(str, "DOM", this.provider);
/* 520 */             } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 521 */               transformService = TransformService.getInstance(str, "DOM");
/*     */             } 
/*     */           } 
/*     */           
/* 525 */           DOMTransform dOMTransform = new DOMTransform(transformService);
/* 526 */           Element element = null;
/* 527 */           String str1 = DOMUtils.getSignaturePrefix(paramXMLCryptoContext);
/* 528 */           if (this.allTransforms.isEmpty()) {
/* 529 */             element = DOMUtils.createElement(this.refElem
/* 530 */                 .getOwnerDocument(), "Transforms", "http://www.w3.org/2000/09/xmldsig#", str1);
/*     */             
/* 532 */             this.refElem.insertBefore(element, 
/* 533 */                 DOMUtils.getFirstChildElement(this.refElem));
/*     */           } else {
/* 535 */             element = DOMUtils.getFirstChildElement(this.refElem);
/*     */           } 
/* 537 */           dOMTransform.marshal(element, str1, (DOMCryptoContext)paramXMLCryptoContext);
/*     */           
/* 539 */           this.allTransforms.add(dOMTransform);
/* 540 */           xMLSignatureInput.updateOutputStream(unsyncBufferedOutputStream, true);
/*     */         } else {
/* 542 */           xMLSignatureInput.updateOutputStream(unsyncBufferedOutputStream);
/*     */         } 
/*     */       } 
/* 545 */       unsyncBufferedOutputStream.flush();
/* 546 */       if (bool != null && bool.booleanValue()) {
/* 547 */         this.dis = digesterOutputStream.getInputStream();
/*     */       }
/* 549 */       return digesterOutputStream.getDigestValue();
/* 550 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 551 */       throw new XMLSignatureException(noSuchAlgorithmException);
/* 552 */     } catch (TransformException transformException) {
/* 553 */       throw new XMLSignatureException(transformException);
/* 554 */     } catch (MarshalException marshalException) {
/* 555 */       throw new XMLSignatureException(marshalException);
/* 556 */     } catch (IOException iOException) {
/* 557 */       throw new XMLSignatureException(iOException);
/* 558 */     } catch (CanonicalizationException canonicalizationException) {
/* 559 */       throw new XMLSignatureException(canonicalizationException);
/*     */     } finally {
/* 561 */       if (unsyncBufferedOutputStream != null) {
/*     */         try {
/* 563 */           unsyncBufferedOutputStream.close();
/* 564 */         } catch (IOException iOException) {
/* 565 */           throw new XMLSignatureException(iOException);
/*     */         } 
/*     */       }
/* 568 */       if (digesterOutputStream != null) {
/*     */         try {
/* 570 */           digesterOutputStream.close();
/* 571 */         } catch (IOException iOException) {
/* 572 */           throw new XMLSignatureException(iOException);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Node getHere() {
/* 579 */     return this.here;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 584 */     if (this == paramObject) {
/* 585 */       return true;
/*     */     }
/*     */     
/* 588 */     if (!(paramObject instanceof Reference)) {
/* 589 */       return false;
/*     */     }
/* 591 */     Reference reference = (Reference)paramObject;
/*     */ 
/*     */     
/* 594 */     boolean bool1 = (this.id == null) ? ((reference.getId() == null) ? true : false) : this.id.equals(reference.getId());
/*     */     
/* 596 */     boolean bool2 = (this.uri == null) ? ((reference.getURI() == null) ? true : false) : this.uri.equals(reference.getURI());
/*     */     
/* 598 */     boolean bool3 = (this.type == null) ? ((reference.getType() == null) ? true : false) : this.type.equals(reference.getType());
/*     */     
/* 600 */     boolean bool = Arrays.equals(this.digestValue, reference.getDigestValue());
/*     */     
/* 602 */     return (this.digestMethod.equals(reference.getDigestMethod()) && bool1 && bool2 && bool3 && this.allTransforms
/*     */       
/* 604 */       .equals(reference.getTransforms()) && bool);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 609 */     int i = 17;
/* 610 */     if (this.id != null) {
/* 611 */       i = 31 * i + this.id.hashCode();
/*     */     }
/* 613 */     if (this.uri != null) {
/* 614 */       i = 31 * i + this.uri.hashCode();
/*     */     }
/* 616 */     if (this.type != null) {
/* 617 */       i = 31 * i + this.type.hashCode();
/*     */     }
/* 619 */     if (this.digestValue != null) {
/* 620 */       i = 31 * i + Arrays.hashCode(this.digestValue);
/*     */     }
/* 622 */     i = 31 * i + this.digestMethod.hashCode();
/* 623 */     i = 31 * i + this.allTransforms.hashCode();
/*     */     
/* 625 */     return i;
/*     */   }
/*     */   
/*     */   boolean isDigested() {
/* 629 */     return this.digested;
/*     */   }
/*     */   
/*     */   private static Data copyDerefData(Data paramData) {
/* 633 */     if (paramData instanceof ApacheData) {
/*     */       
/* 635 */       ApacheData apacheData = (ApacheData)paramData;
/* 636 */       XMLSignatureInput xMLSignatureInput = apacheData.getXMLSignatureInput();
/* 637 */       if (xMLSignatureInput.isNodeSet())
/*     */         try {
/* 639 */           final Set<Node> s = xMLSignatureInput.getNodeSet();
/* 640 */           return new NodeSetData() {
/* 641 */               public Iterator iterator() { return s.iterator(); }
/*     */             };
/* 643 */         } catch (Exception exception) {
/*     */           
/* 645 */           log.log(Level.WARNING, "cannot cache dereferenced data: " + exception);
/* 646 */           return null;
/*     */         }  
/* 648 */       if (xMLSignatureInput.isElement())
/* 649 */         return new DOMSubTreeData(xMLSignatureInput
/* 650 */             .getSubNode(), xMLSignatureInput.isExcludeComments()); 
/* 651 */       if (xMLSignatureInput.isOctetStream() || xMLSignatureInput.isByteArray()) {
/*     */         try {
/* 653 */           return new OctetStreamData(xMLSignatureInput
/* 654 */               .getOctetStream(), xMLSignatureInput.getSourceURI(), xMLSignatureInput
/* 655 */               .getMIMEType());
/* 656 */         } catch (IOException iOException) {
/*     */           
/* 658 */           log.log(Level.WARNING, "cannot cache dereferenced data: " + iOException);
/* 659 */           return null;
/*     */         } 
/*     */       }
/*     */     } 
/* 663 */     return paramData;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMReference.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */