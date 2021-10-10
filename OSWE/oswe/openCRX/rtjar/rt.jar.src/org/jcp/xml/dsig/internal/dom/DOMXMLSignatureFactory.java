/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.List;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.URIDereferencer;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.XMLStructure;
/*     */ import javax.xml.crypto.dom.DOMCryptoContext;
/*     */ import javax.xml.crypto.dom.DOMStructure;
/*     */ import javax.xml.crypto.dsig.CanonicalizationMethod;
/*     */ import javax.xml.crypto.dsig.DigestMethod;
/*     */ import javax.xml.crypto.dsig.Manifest;
/*     */ import javax.xml.crypto.dsig.Reference;
/*     */ import javax.xml.crypto.dsig.SignatureMethod;
/*     */ import javax.xml.crypto.dsig.SignatureProperties;
/*     */ import javax.xml.crypto.dsig.SignatureProperty;
/*     */ import javax.xml.crypto.dsig.SignedInfo;
/*     */ import javax.xml.crypto.dsig.Transform;
/*     */ import javax.xml.crypto.dsig.TransformService;
/*     */ import javax.xml.crypto.dsig.XMLObject;
/*     */ import javax.xml.crypto.dsig.XMLSignature;
/*     */ import javax.xml.crypto.dsig.XMLSignatureFactory;
/*     */ import javax.xml.crypto.dsig.XMLValidateContext;
/*     */ import javax.xml.crypto.dsig.dom.DOMValidateContext;
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyInfo;
/*     */ import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
/*     */ import javax.xml.crypto.dsig.spec.DigestMethodParameterSpec;
/*     */ import javax.xml.crypto.dsig.spec.SignatureMethodParameterSpec;
/*     */ import javax.xml.crypto.dsig.spec.TransformParameterSpec;
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
/*     */ public final class DOMXMLSignatureFactory
/*     */   extends XMLSignatureFactory
/*     */ {
/*     */   public XMLSignature newXMLSignature(SignedInfo paramSignedInfo, KeyInfo paramKeyInfo) {
/*  58 */     return new DOMXMLSignature(paramSignedInfo, paramKeyInfo, null, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSignature newXMLSignature(SignedInfo paramSignedInfo, KeyInfo paramKeyInfo, List<? extends XMLObject> paramList, String paramString1, String paramString2) {
/*  64 */     return new DOMXMLSignature(paramSignedInfo, paramKeyInfo, paramList, paramString1, paramString2);
/*     */   }
/*     */   
/*     */   public Reference newReference(String paramString, DigestMethod paramDigestMethod) {
/*  68 */     return newReference(paramString, paramDigestMethod, null, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference newReference(String paramString1, DigestMethod paramDigestMethod, List<? extends Transform> paramList, String paramString2, String paramString3) {
/*  74 */     return new DOMReference(paramString1, paramString2, paramDigestMethod, paramList, paramString3, getProvider());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference newReference(String paramString1, DigestMethod paramDigestMethod, List<? extends Transform> paramList1, Data paramData, List<? extends Transform> paramList2, String paramString2, String paramString3) {
/*  81 */     if (paramList1 == null) {
/*  82 */       throw new NullPointerException("appliedTransforms cannot be null");
/*     */     }
/*  84 */     if (paramList1.isEmpty()) {
/*  85 */       throw new NullPointerException("appliedTransforms cannot be empty");
/*     */     }
/*  87 */     if (paramData == null) {
/*  88 */       throw new NullPointerException("result cannot be null");
/*     */     }
/*  90 */     return new DOMReference(paramString1, paramString2, paramDigestMethod, paramList1, paramData, paramList2, paramString3, 
/*  91 */         getProvider());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference newReference(String paramString1, DigestMethod paramDigestMethod, List<? extends Transform> paramList, String paramString2, String paramString3, byte[] paramArrayOfbyte) {
/*  97 */     if (paramArrayOfbyte == null) {
/*  98 */       throw new NullPointerException("digestValue cannot be null");
/*     */     }
/* 100 */     return new DOMReference(paramString1, paramString2, paramDigestMethod, null, null, paramList, paramString3, paramArrayOfbyte, 
/* 101 */         getProvider());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SignedInfo newSignedInfo(CanonicalizationMethod paramCanonicalizationMethod, SignatureMethod paramSignatureMethod, List paramList) {
/* 107 */     return newSignedInfo(paramCanonicalizationMethod, paramSignatureMethod, paramList, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SignedInfo newSignedInfo(CanonicalizationMethod paramCanonicalizationMethod, SignatureMethod paramSignatureMethod, List<? extends Reference> paramList, String paramString) {
/* 113 */     return new DOMSignedInfo(paramCanonicalizationMethod, paramSignatureMethod, paramList, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLObject newXMLObject(List<? extends XMLStructure> paramList, String paramString1, String paramString2, String paramString3) {
/* 120 */     return new DOMXMLObject(paramList, paramString1, paramString2, paramString3);
/*     */   }
/*     */ 
/*     */   
/*     */   public Manifest newManifest(List paramList) {
/* 125 */     return newManifest(paramList, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Manifest newManifest(List<? extends Reference> paramList, String paramString) {
/* 130 */     return new DOMManifest(paramList, paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureProperties newSignatureProperties(List<? extends SignatureProperty> paramList, String paramString) {
/* 135 */     return new DOMSignatureProperties(paramList, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureProperty newSignatureProperty(List<? extends XMLStructure> paramList, String paramString1, String paramString2) {
/* 141 */     return new DOMSignatureProperty(paramList, paramString1, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSignature unmarshalXMLSignature(XMLValidateContext paramXMLValidateContext) throws MarshalException {
/* 147 */     if (paramXMLValidateContext == null) {
/* 148 */       throw new NullPointerException("context cannot be null");
/*     */     }
/* 150 */     return unmarshal(((DOMValidateContext)paramXMLValidateContext).getNode(), paramXMLValidateContext);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSignature unmarshalXMLSignature(XMLStructure paramXMLStructure) throws MarshalException {
/* 156 */     if (paramXMLStructure == null) {
/* 157 */       throw new NullPointerException("xmlStructure cannot be null");
/*     */     }
/* 159 */     if (!(paramXMLStructure instanceof DOMStructure)) {
/* 160 */       throw new ClassCastException("xmlStructure must be of type DOMStructure");
/*     */     }
/* 162 */     return 
/* 163 */       unmarshal(((DOMStructure)paramXMLStructure).getNode(), new UnmarshalContext());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class UnmarshalContext
/*     */     extends DOMCryptoContext {}
/*     */ 
/*     */ 
/*     */   
/*     */   private XMLSignature unmarshal(Node paramNode, XMLCryptoContext paramXMLCryptoContext) throws MarshalException {
/* 174 */     paramNode.normalize();
/*     */     
/* 176 */     Element element = null;
/* 177 */     if (paramNode.getNodeType() == 9) {
/* 178 */       element = ((Document)paramNode).getDocumentElement();
/* 179 */     } else if (paramNode.getNodeType() == 1) {
/* 180 */       element = (Element)paramNode;
/*     */     } else {
/* 182 */       throw new MarshalException("Signature element is not a proper Node");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 187 */     String str = element.getLocalName();
/* 188 */     if (str == null) {
/* 189 */       throw new MarshalException("Document implementation must support DOM Level 2 and be namespace aware");
/*     */     }
/*     */     
/* 192 */     if (str.equals("Signature")) {
/* 193 */       return new DOMXMLSignature(element, paramXMLCryptoContext, getProvider());
/*     */     }
/* 195 */     throw new MarshalException("invalid Signature tag: " + str);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFeatureSupported(String paramString) {
/* 200 */     if (paramString == null) {
/* 201 */       throw new NullPointerException();
/*     */     }
/* 203 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DigestMethod newDigestMethod(String paramString, DigestMethodParameterSpec paramDigestMethodParameterSpec) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
/* 210 */     if (paramString == null) {
/* 211 */       throw new NullPointerException();
/*     */     }
/* 213 */     if (paramString.equals("http://www.w3.org/2000/09/xmldsig#sha1"))
/* 214 */       return new DOMDigestMethod.SHA1(paramDigestMethodParameterSpec); 
/* 215 */     if (paramString.equals("http://www.w3.org/2001/04/xmlenc#sha256"))
/* 216 */       return new DOMDigestMethod.SHA256(paramDigestMethodParameterSpec); 
/* 217 */     if (paramString.equals("http://www.w3.org/2001/04/xmldsig-more#sha384"))
/* 218 */       return new DOMDigestMethod.SHA384(paramDigestMethodParameterSpec); 
/* 219 */     if (paramString.equals("http://www.w3.org/2001/04/xmlenc#sha512")) {
/* 220 */       return new DOMDigestMethod.SHA512(paramDigestMethodParameterSpec);
/*     */     }
/* 222 */     throw new NoSuchAlgorithmException("unsupported algorithm");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureMethod newSignatureMethod(String paramString, SignatureMethodParameterSpec paramSignatureMethodParameterSpec) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
/* 229 */     if (paramString == null) {
/* 230 */       throw new NullPointerException();
/*     */     }
/* 232 */     if (paramString.equals("http://www.w3.org/2000/09/xmldsig#rsa-sha1"))
/* 233 */       return new DOMSignatureMethod.SHA1withRSA(paramSignatureMethodParameterSpec); 
/* 234 */     if (paramString.equals("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256"))
/* 235 */       return new DOMSignatureMethod.SHA256withRSA(paramSignatureMethodParameterSpec); 
/* 236 */     if (paramString.equals("http://www.w3.org/2001/04/xmldsig-more#rsa-sha384"))
/* 237 */       return new DOMSignatureMethod.SHA384withRSA(paramSignatureMethodParameterSpec); 
/* 238 */     if (paramString.equals("http://www.w3.org/2001/04/xmldsig-more#rsa-sha512"))
/* 239 */       return new DOMSignatureMethod.SHA512withRSA(paramSignatureMethodParameterSpec); 
/* 240 */     if (paramString.equals("http://www.w3.org/2000/09/xmldsig#dsa-sha1"))
/* 241 */       return new DOMSignatureMethod.SHA1withDSA(paramSignatureMethodParameterSpec); 
/* 242 */     if (paramString.equals("http://www.w3.org/2009/xmldsig11#dsa-sha256"))
/* 243 */       return new DOMSignatureMethod.SHA256withDSA(paramSignatureMethodParameterSpec); 
/* 244 */     if (paramString.equals("http://www.w3.org/2000/09/xmldsig#hmac-sha1"))
/* 245 */       return new DOMHMACSignatureMethod.SHA1(paramSignatureMethodParameterSpec); 
/* 246 */     if (paramString.equals("http://www.w3.org/2001/04/xmldsig-more#hmac-sha256"))
/* 247 */       return new DOMHMACSignatureMethod.SHA256(paramSignatureMethodParameterSpec); 
/* 248 */     if (paramString.equals("http://www.w3.org/2001/04/xmldsig-more#hmac-sha384"))
/* 249 */       return new DOMHMACSignatureMethod.SHA384(paramSignatureMethodParameterSpec); 
/* 250 */     if (paramString.equals("http://www.w3.org/2001/04/xmldsig-more#hmac-sha512"))
/* 251 */       return new DOMHMACSignatureMethod.SHA512(paramSignatureMethodParameterSpec); 
/* 252 */     if (paramString.equals("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1"))
/* 253 */       return new DOMSignatureMethod.SHA1withECDSA(paramSignatureMethodParameterSpec); 
/* 254 */     if (paramString.equals("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256"))
/* 255 */       return new DOMSignatureMethod.SHA256withECDSA(paramSignatureMethodParameterSpec); 
/* 256 */     if (paramString.equals("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha384"))
/* 257 */       return new DOMSignatureMethod.SHA384withECDSA(paramSignatureMethodParameterSpec); 
/* 258 */     if (paramString.equals("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha512")) {
/* 259 */       return new DOMSignatureMethod.SHA512withECDSA(paramSignatureMethodParameterSpec);
/*     */     }
/* 261 */     throw new NoSuchAlgorithmException("unsupported algorithm");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transform newTransform(String paramString, TransformParameterSpec paramTransformParameterSpec) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
/*     */     TransformService transformService;
/* 270 */     if (getProvider() == null) {
/* 271 */       transformService = TransformService.getInstance(paramString, "DOM");
/*     */     } else {
/*     */       try {
/* 274 */         transformService = TransformService.getInstance(paramString, "DOM", getProvider());
/* 275 */       } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 276 */         transformService = TransformService.getInstance(paramString, "DOM");
/*     */       } 
/*     */     } 
/*     */     
/* 280 */     transformService.init(paramTransformParameterSpec);
/* 281 */     return new DOMTransform(transformService);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Transform newTransform(String paramString, XMLStructure paramXMLStructure) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
/*     */     TransformService transformService;
/* 288 */     if (getProvider() == null) {
/* 289 */       transformService = TransformService.getInstance(paramString, "DOM");
/*     */     } else {
/*     */       try {
/* 292 */         transformService = TransformService.getInstance(paramString, "DOM", getProvider());
/* 293 */       } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 294 */         transformService = TransformService.getInstance(paramString, "DOM");
/*     */       } 
/*     */     } 
/*     */     
/* 298 */     if (paramXMLStructure == null) {
/* 299 */       transformService.init(null);
/*     */     } else {
/* 301 */       transformService.init(paramXMLStructure, null);
/*     */     } 
/* 303 */     return new DOMTransform(transformService);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CanonicalizationMethod newCanonicalizationMethod(String paramString, C14NMethodParameterSpec paramC14NMethodParameterSpec) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
/*     */     TransformService transformService;
/* 310 */     if (getProvider() == null) {
/* 311 */       transformService = TransformService.getInstance(paramString, "DOM");
/*     */     } else {
/*     */       try {
/* 314 */         transformService = TransformService.getInstance(paramString, "DOM", getProvider());
/* 315 */       } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 316 */         transformService = TransformService.getInstance(paramString, "DOM");
/*     */       } 
/*     */     } 
/*     */     
/* 320 */     transformService.init(paramC14NMethodParameterSpec);
/* 321 */     return new DOMCanonicalizationMethod(transformService);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CanonicalizationMethod newCanonicalizationMethod(String paramString, XMLStructure paramXMLStructure) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
/*     */     TransformService transformService;
/* 328 */     if (getProvider() == null) {
/* 329 */       transformService = TransformService.getInstance(paramString, "DOM");
/*     */     } else {
/*     */       try {
/* 332 */         transformService = TransformService.getInstance(paramString, "DOM", getProvider());
/* 333 */       } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 334 */         transformService = TransformService.getInstance(paramString, "DOM");
/*     */       } 
/*     */     } 
/* 337 */     if (paramXMLStructure == null) {
/* 338 */       transformService.init(null);
/*     */     } else {
/* 340 */       transformService.init(paramXMLStructure, null);
/*     */     } 
/*     */     
/* 343 */     return new DOMCanonicalizationMethod(transformService);
/*     */   }
/*     */   
/*     */   public URIDereferencer getURIDereferencer() {
/* 347 */     return DOMURIDereferencer.INSTANCE;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMXMLSignatureFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */