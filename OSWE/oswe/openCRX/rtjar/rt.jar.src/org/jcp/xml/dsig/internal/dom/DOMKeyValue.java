/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.Base64;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.KeyException;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.PublicKey;
/*     */ import java.security.interfaces.DSAParams;
/*     */ import java.security.interfaces.DSAPublicKey;
/*     */ import java.security.interfaces.ECPublicKey;
/*     */ import java.security.interfaces.RSAPublicKey;
/*     */ import java.security.spec.DSAPublicKeySpec;
/*     */ import java.security.spec.ECParameterSpec;
/*     */ import java.security.spec.ECPoint;
/*     */ import java.security.spec.ECPublicKeySpec;
/*     */ import java.security.spec.EllipticCurve;
/*     */ import java.security.spec.InvalidKeySpecException;
/*     */ import java.security.spec.KeySpec;
/*     */ import java.security.spec.RSAPublicKeySpec;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.dom.DOMCryptoContext;
/*     */ import javax.xml.crypto.dom.DOMStructure;
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyValue;
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
/*     */ public abstract class DOMKeyValue
/*     */   extends DOMStructure
/*     */   implements KeyValue
/*     */ {
/*     */   private static final String XMLDSIG_11_XMLNS = "http://www.w3.org/2009/xmldsig11#";
/*     */   private final PublicKey publicKey;
/*     */   
/*     */   public DOMKeyValue(PublicKey paramPublicKey) throws KeyException {
/*  77 */     if (paramPublicKey == null) {
/*  78 */       throw new NullPointerException("key cannot be null");
/*     */     }
/*  80 */     this.publicKey = paramPublicKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DOMKeyValue(Element paramElement) throws MarshalException {
/*  89 */     this.publicKey = unmarshalKeyValue(paramElement);
/*     */   }
/*     */   
/*     */   static KeyValue unmarshal(Element paramElement) throws MarshalException {
/*  93 */     Element element = DOMUtils.getFirstChildElement(paramElement);
/*  94 */     if (element.getLocalName().equals("DSAKeyValue"))
/*  95 */       return new DSA(element); 
/*  96 */     if (element.getLocalName().equals("RSAKeyValue"))
/*  97 */       return new RSA(element); 
/*  98 */     if (element.getLocalName().equals("ECKeyValue")) {
/*  99 */       return new EC(element);
/*     */     }
/* 101 */     return new Unknown(element);
/*     */   }
/*     */ 
/*     */   
/*     */   public PublicKey getPublicKey() throws KeyException {
/* 106 */     if (this.publicKey == null) {
/* 107 */       throw new KeyException("can't convert KeyValue to PublicKey");
/*     */     }
/* 109 */     return this.publicKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
/* 116 */     Document document = DOMUtils.getOwnerDocument(paramNode);
/*     */ 
/*     */     
/* 119 */     Element element = DOMUtils.createElement(document, "KeyValue", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */     
/* 121 */     marshalPublicKey(element, document, paramString, paramDOMCryptoContext);
/*     */     
/* 123 */     paramNode.appendChild(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static PublicKey generatePublicKey(KeyFactory paramKeyFactory, KeySpec paramKeySpec) {
/*     */     try {
/* 134 */       return paramKeyFactory.generatePublic(paramKeySpec);
/* 135 */     } catch (InvalidKeySpecException invalidKeySpecException) {
/*     */       
/* 137 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 143 */     if (this == paramObject) {
/* 144 */       return true;
/*     */     }
/* 146 */     if (!(paramObject instanceof KeyValue)) {
/* 147 */       return false;
/*     */     }
/*     */     try {
/* 150 */       KeyValue keyValue = (KeyValue)paramObject;
/* 151 */       if (this.publicKey == null) {
/* 152 */         if (keyValue.getPublicKey() != null) {
/* 153 */           return false;
/*     */         }
/* 155 */       } else if (!this.publicKey.equals(keyValue.getPublicKey())) {
/* 156 */         return false;
/*     */       } 
/* 158 */     } catch (KeyException keyException) {
/*     */       
/* 160 */       return false;
/*     */     } 
/*     */     
/* 163 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 168 */     int i = 17;
/* 169 */     if (this.publicKey != null) {
/* 170 */       i = 31 * i + this.publicKey.hashCode();
/*     */     }
/*     */     
/* 173 */     return i;
/*     */   }
/*     */   abstract void marshalPublicKey(Node paramNode, Document paramDocument, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException;
/*     */   
/*     */   abstract PublicKey unmarshalKeyValue(Element paramElement) throws MarshalException;
/*     */   
/*     */   static final class RSA extends DOMKeyValue { private DOMCryptoBinary modulus;
/*     */     
/*     */     RSA(PublicKey param1PublicKey) throws KeyException {
/* 182 */       super(param1PublicKey);
/* 183 */       RSAPublicKey rSAPublicKey = (RSAPublicKey)param1PublicKey;
/* 184 */       this.exponent = new DOMCryptoBinary(rSAPublicKey.getPublicExponent());
/* 185 */       this.modulus = new DOMCryptoBinary(rSAPublicKey.getModulus());
/*     */     }
/*     */     private DOMCryptoBinary exponent; private KeyFactory rsakf;
/*     */     RSA(Element param1Element) throws MarshalException {
/* 189 */       super(param1Element);
/*     */     }
/*     */ 
/*     */     
/*     */     void marshalPublicKey(Node param1Node, Document param1Document, String param1String, DOMCryptoContext param1DOMCryptoContext) throws MarshalException {
/* 194 */       Element element1 = DOMUtils.createElement(param1Document, "RSAKeyValue", "http://www.w3.org/2000/09/xmldsig#", param1String);
/*     */ 
/*     */       
/* 197 */       Element element2 = DOMUtils.createElement(param1Document, "Modulus", "http://www.w3.org/2000/09/xmldsig#", param1String);
/*     */ 
/*     */       
/* 200 */       Element element3 = DOMUtils.createElement(param1Document, "Exponent", "http://www.w3.org/2000/09/xmldsig#", param1String);
/*     */ 
/*     */       
/* 203 */       this.modulus.marshal(element2, param1String, param1DOMCryptoContext);
/* 204 */       this.exponent.marshal(element3, param1String, param1DOMCryptoContext);
/* 205 */       element1.appendChild(element2);
/* 206 */       element1.appendChild(element3);
/* 207 */       param1Node.appendChild(element1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     PublicKey unmarshalKeyValue(Element param1Element) throws MarshalException {
/* 213 */       if (this.rsakf == null) {
/*     */         try {
/* 215 */           this.rsakf = KeyFactory.getInstance("RSA");
/* 216 */         } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 217 */           throw new RuntimeException("unable to create RSA KeyFactory: " + noSuchAlgorithmException
/* 218 */               .getMessage());
/*     */         } 
/*     */       }
/* 221 */       Element element1 = DOMUtils.getFirstChildElement(param1Element, "Modulus");
/*     */       
/* 223 */       this.modulus = new DOMCryptoBinary(element1.getFirstChild());
/* 224 */       Element element2 = DOMUtils.getNextSiblingElement(element1, "Exponent");
/*     */       
/* 226 */       this.exponent = new DOMCryptoBinary(element2.getFirstChild());
/*     */       
/* 228 */       RSAPublicKeySpec rSAPublicKeySpec = new RSAPublicKeySpec(this.modulus.getBigNum(), this.exponent.getBigNum());
/* 229 */       return DOMKeyValue.generatePublicKey(this.rsakf, rSAPublicKeySpec);
/*     */     } }
/*     */   static final class DSA extends DOMKeyValue { private DOMCryptoBinary p;
/*     */     private DOMCryptoBinary q;
/*     */     private DOMCryptoBinary g;
/*     */     private DOMCryptoBinary y;
/*     */     private DOMCryptoBinary j;
/*     */     private KeyFactory dsakf;
/*     */     
/*     */     DSA(PublicKey param1PublicKey) throws KeyException {
/* 239 */       super(param1PublicKey);
/* 240 */       DSAPublicKey dSAPublicKey = (DSAPublicKey)param1PublicKey;
/* 241 */       DSAParams dSAParams = dSAPublicKey.getParams();
/* 242 */       this.p = new DOMCryptoBinary(dSAParams.getP());
/* 243 */       this.q = new DOMCryptoBinary(dSAParams.getQ());
/* 244 */       this.g = new DOMCryptoBinary(dSAParams.getG());
/* 245 */       this.y = new DOMCryptoBinary(dSAPublicKey.getY());
/*     */     }
/*     */     
/*     */     DSA(Element param1Element) throws MarshalException {
/* 249 */       super(param1Element);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void marshalPublicKey(Node param1Node, Document param1Document, String param1String, DOMCryptoContext param1DOMCryptoContext) throws MarshalException {
/* 256 */       Element element1 = DOMUtils.createElement(param1Document, "DSAKeyValue", "http://www.w3.org/2000/09/xmldsig#", param1String);
/*     */ 
/*     */ 
/*     */       
/* 260 */       Element element2 = DOMUtils.createElement(param1Document, "P", "http://www.w3.org/2000/09/xmldsig#", param1String);
/*     */       
/* 262 */       Element element3 = DOMUtils.createElement(param1Document, "Q", "http://www.w3.org/2000/09/xmldsig#", param1String);
/*     */       
/* 264 */       Element element4 = DOMUtils.createElement(param1Document, "G", "http://www.w3.org/2000/09/xmldsig#", param1String);
/*     */       
/* 266 */       Element element5 = DOMUtils.createElement(param1Document, "Y", "http://www.w3.org/2000/09/xmldsig#", param1String);
/*     */       
/* 268 */       this.p.marshal(element2, param1String, param1DOMCryptoContext);
/* 269 */       this.q.marshal(element3, param1String, param1DOMCryptoContext);
/* 270 */       this.g.marshal(element4, param1String, param1DOMCryptoContext);
/* 271 */       this.y.marshal(element5, param1String, param1DOMCryptoContext);
/* 272 */       element1.appendChild(element2);
/* 273 */       element1.appendChild(element3);
/* 274 */       element1.appendChild(element4);
/* 275 */       element1.appendChild(element5);
/* 276 */       param1Node.appendChild(element1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     PublicKey unmarshalKeyValue(Element param1Element) throws MarshalException {
/* 282 */       if (this.dsakf == null) {
/*     */         try {
/* 284 */           this.dsakf = KeyFactory.getInstance("DSA");
/* 285 */         } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 286 */           throw new RuntimeException("unable to create DSA KeyFactory: " + noSuchAlgorithmException
/* 287 */               .getMessage());
/*     */         } 
/*     */       }
/* 290 */       Element element = DOMUtils.getFirstChildElement(param1Element);
/*     */       
/* 292 */       if (element.getLocalName().equals("P")) {
/* 293 */         this.p = new DOMCryptoBinary(element.getFirstChild());
/* 294 */         element = DOMUtils.getNextSiblingElement(element, "Q");
/* 295 */         this.q = new DOMCryptoBinary(element.getFirstChild());
/* 296 */         element = DOMUtils.getNextSiblingElement(element);
/*     */       } 
/* 298 */       if (element.getLocalName().equals("G")) {
/* 299 */         this.g = new DOMCryptoBinary(element.getFirstChild());
/* 300 */         element = DOMUtils.getNextSiblingElement(element, "Y");
/*     */       } 
/* 302 */       this.y = new DOMCryptoBinary(element.getFirstChild());
/* 303 */       element = DOMUtils.getNextSiblingElement(element);
/* 304 */       if (element != null && element.getLocalName().equals("J")) {
/* 305 */         this.j = new DOMCryptoBinary(element.getFirstChild());
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 319 */       DSAPublicKeySpec dSAPublicKeySpec = new DSAPublicKeySpec(this.y.getBigNum(), this.p.getBigNum(), this.q.getBigNum(), this.g.getBigNum());
/* 320 */       return DOMKeyValue.generatePublicKey(this.dsakf, dSAPublicKeySpec);
/*     */     } }
/*     */ 
/*     */   
/*     */   static final class EC extends DOMKeyValue { private byte[] ecPublicKey;
/*     */     private KeyFactory eckf;
/*     */     private ECParameterSpec ecParams;
/*     */     private Method encodePoint;
/*     */     private Method decodePoint;
/*     */     private Method getCurveName;
/*     */     private Method getECParameterSpec;
/*     */     
/*     */     EC(PublicKey param1PublicKey) throws KeyException {
/* 333 */       super(param1PublicKey);
/* 334 */       ECPublicKey eCPublicKey = (ECPublicKey)param1PublicKey;
/* 335 */       ECPoint eCPoint = eCPublicKey.getW();
/* 336 */       this.ecParams = eCPublicKey.getParams();
/*     */       try {
/* 338 */         AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*     */             {
/*     */               
/*     */               public Void run() throws ClassNotFoundException, NoSuchMethodException
/*     */               {
/* 343 */                 DOMKeyValue.EC.this.getMethods();
/* 344 */                 return null;
/*     */               }
/*     */             });
/*     */       }
/* 348 */       catch (PrivilegedActionException privilegedActionException) {
/* 349 */         throw new KeyException("ECKeyValue not supported", privilegedActionException
/* 350 */             .getException());
/*     */       } 
/* 352 */       Object[] arrayOfObject = { eCPoint, this.ecParams.getCurve() };
/*     */       try {
/* 354 */         this.ecPublicKey = (byte[])this.encodePoint.invoke(null, arrayOfObject);
/* 355 */       } catch (IllegalAccessException illegalAccessException) {
/* 356 */         throw new KeyException(illegalAccessException);
/* 357 */       } catch (InvocationTargetException invocationTargetException) {
/* 358 */         throw new KeyException(invocationTargetException);
/*     */       } 
/*     */     }
/*     */     
/*     */     EC(Element param1Element) throws MarshalException {
/* 363 */       super(param1Element);
/*     */     }
/*     */     
/*     */     void getMethods() throws ClassNotFoundException, NoSuchMethodException {
/* 367 */       Class<?> clazz = Class.forName("sun.security.ec.ECParameters");
/* 368 */       Class[] arrayOfClass = { ECPoint.class, EllipticCurve.class };
/* 369 */       this.encodePoint = clazz.getMethod("encodePoint", arrayOfClass);
/* 370 */       arrayOfClass = new Class[] { ECParameterSpec.class };
/* 371 */       this.getCurveName = clazz.getMethod("getCurveName", arrayOfClass);
/* 372 */       arrayOfClass = new Class[] { byte[].class, EllipticCurve.class };
/* 373 */       this.decodePoint = clazz.getMethod("decodePoint", arrayOfClass);
/* 374 */       clazz = Class.forName("sun.security.ec.NamedCurve");
/* 375 */       arrayOfClass = new Class[] { String.class };
/* 376 */       this.getECParameterSpec = clazz.getMethod("getECParameterSpec", arrayOfClass);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void marshalPublicKey(Node param1Node, Document param1Document, String param1String, DOMCryptoContext param1DOMCryptoContext) throws MarshalException {
/* 383 */       String str1 = DOMUtils.getNSPrefix(param1DOMCryptoContext, "http://www.w3.org/2009/xmldsig11#");
/* 384 */       Element element1 = DOMUtils.createElement(param1Document, "ECKeyValue", "http://www.w3.org/2009/xmldsig11#", str1);
/*     */ 
/*     */       
/* 387 */       Element element2 = DOMUtils.createElement(param1Document, "NamedCurve", "http://www.w3.org/2009/xmldsig11#", str1);
/*     */ 
/*     */       
/* 390 */       Element element3 = DOMUtils.createElement(param1Document, "PublicKey", "http://www.w3.org/2009/xmldsig11#", str1);
/*     */ 
/*     */       
/* 393 */       Object[] arrayOfObject = { this.ecParams };
/*     */       try {
/* 395 */         String str = (String)this.getCurveName.invoke(null, arrayOfObject);
/* 396 */         DOMUtils.setAttribute(element2, "URI", "urn:oid:" + str);
/* 397 */       } catch (IllegalAccessException illegalAccessException) {
/* 398 */         throw new MarshalException(illegalAccessException);
/* 399 */       } catch (InvocationTargetException invocationTargetException) {
/* 400 */         throw new MarshalException(invocationTargetException);
/*     */       } 
/* 402 */       String str2 = (str1 == null || str1.length() == 0) ? "xmlns" : ("xmlns:" + str1);
/*     */       
/* 404 */       element2.setAttributeNS("http://www.w3.org/2000/xmlns/", str2, "http://www.w3.org/2009/xmldsig11#");
/*     */       
/* 406 */       element1.appendChild(element2);
/* 407 */       String str3 = Base64.encode(this.ecPublicKey);
/* 408 */       element3
/* 409 */         .appendChild(DOMUtils.getOwnerDocument(element3).createTextNode(str3));
/* 410 */       element1.appendChild(element3);
/* 411 */       param1Node.appendChild(element1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     PublicKey unmarshalKeyValue(Element param1Element) throws MarshalException {
/* 417 */       if (this.eckf == null) {
/*     */         try {
/* 419 */           this.eckf = KeyFactory.getInstance("EC");
/* 420 */         } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 421 */           throw new RuntimeException("unable to create EC KeyFactory: " + noSuchAlgorithmException
/* 422 */               .getMessage());
/*     */         } 
/*     */       }
/*     */       try {
/* 426 */         AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*     */             {
/*     */               
/*     */               public Void run() throws ClassNotFoundException, NoSuchMethodException
/*     */               {
/* 431 */                 DOMKeyValue.EC.this.getMethods();
/* 432 */                 return null;
/*     */               }
/*     */             });
/*     */       }
/* 436 */       catch (PrivilegedActionException privilegedActionException) {
/* 437 */         throw new MarshalException("ECKeyValue not supported", privilegedActionException
/* 438 */             .getException());
/*     */       } 
/* 440 */       ECParameterSpec eCParameterSpec = null;
/* 441 */       Element element = DOMUtils.getFirstChildElement(param1Element);
/* 442 */       if (element.getLocalName().equals("ECParameters")) {
/* 443 */         throw new UnsupportedOperationException("ECParameters not supported");
/*     */       }
/* 445 */       if (element.getLocalName().equals("NamedCurve")) {
/* 446 */         String str = DOMUtils.getAttributeValue(element, "URI");
/*     */         
/* 448 */         if (str.startsWith("urn:oid:")) {
/* 449 */           String str1 = str.substring(8);
/*     */           try {
/* 451 */             Object[] arrayOfObject = { str1 };
/*     */             
/* 453 */             eCParameterSpec = (ECParameterSpec)this.getECParameterSpec.invoke(null, arrayOfObject);
/* 454 */           } catch (IllegalAccessException illegalAccessException) {
/* 455 */             throw new MarshalException(illegalAccessException);
/* 456 */           } catch (InvocationTargetException invocationTargetException) {
/* 457 */             throw new MarshalException(invocationTargetException);
/*     */           } 
/*     */         } else {
/* 460 */           throw new MarshalException("Invalid NamedCurve URI");
/*     */         } 
/*     */       } else {
/* 463 */         throw new MarshalException("Invalid ECKeyValue");
/*     */       } 
/* 465 */       element = DOMUtils.getNextSiblingElement(element, "PublicKey");
/* 466 */       ECPoint eCPoint = null;
/*     */       
/*     */       try {
/* 469 */         Object[] arrayOfObject = { Base64.decode(element), eCParameterSpec.getCurve() };
/* 470 */         eCPoint = (ECPoint)this.decodePoint.invoke(null, arrayOfObject);
/* 471 */       } catch (Base64DecodingException base64DecodingException) {
/* 472 */         throw new MarshalException("Invalid EC PublicKey", base64DecodingException);
/* 473 */       } catch (IllegalAccessException illegalAccessException) {
/* 474 */         throw new MarshalException(illegalAccessException);
/* 475 */       } catch (InvocationTargetException invocationTargetException) {
/* 476 */         throw new MarshalException(invocationTargetException);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 482 */       ECPublicKeySpec eCPublicKeySpec = new ECPublicKeySpec(eCPoint, eCParameterSpec);
/* 483 */       return DOMKeyValue.generatePublicKey(this.eckf, eCPublicKeySpec);
/*     */     } }
/*     */   
/*     */   static final class Unknown extends DOMKeyValue {
/*     */     private DOMStructure externalPublicKey;
/*     */     
/*     */     Unknown(Element param1Element) throws MarshalException {
/* 490 */       super(param1Element);
/*     */     }
/*     */     PublicKey unmarshalKeyValue(Element param1Element) throws MarshalException {
/* 493 */       this.externalPublicKey = new DOMStructure(param1Element);
/* 494 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void marshalPublicKey(Node param1Node, Document param1Document, String param1String, DOMCryptoContext param1DOMCryptoContext) throws MarshalException {
/* 500 */       param1Node.appendChild(this.externalPublicKey.getNode());
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMKeyValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */