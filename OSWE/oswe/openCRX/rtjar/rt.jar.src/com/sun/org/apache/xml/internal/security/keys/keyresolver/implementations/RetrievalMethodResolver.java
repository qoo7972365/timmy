/*     */ package com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.RetrievalMethod;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverSpi;
/*     */ import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolver;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.Transforms;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolver;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Attr;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RetrievalMethodResolver
/*     */   extends KeyResolverSpi
/*     */ {
/*  76 */   private static Logger log = Logger.getLogger(RetrievalMethodResolver.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PublicKey engineLookupAndResolvePublicKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
/*  88 */     if (!XMLUtils.elementIsInSignatureSpace(paramElement, "RetrievalMethod")) {
/*  89 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  94 */       RetrievalMethod retrievalMethod = new RetrievalMethod(paramElement, paramString);
/*  95 */       String str = retrievalMethod.getType();
/*  96 */       XMLSignatureInput xMLSignatureInput = resolveInput(retrievalMethod, paramString, this.secureValidation);
/*  97 */       if ("http://www.w3.org/2000/09/xmldsig#rawX509Certificate".equals(str)) {
/*     */         
/*  99 */         X509Certificate x509Certificate = getRawCertificate(xMLSignatureInput);
/* 100 */         if (x509Certificate != null) {
/* 101 */           return x509Certificate.getPublicKey();
/*     */         }
/* 103 */         return null;
/*     */       } 
/* 105 */       Element element = obtainReferenceElement(xMLSignatureInput);
/*     */ 
/*     */ 
/*     */       
/* 109 */       if (XMLUtils.elementIsInSignatureSpace(element, "RetrievalMethod")) {
/* 110 */         if (this.secureValidation) {
/* 111 */           String str1 = "Error: It is forbidden to have one RetrievalMethod point to another with secure validation";
/*     */           
/* 113 */           if (log.isLoggable(Level.FINE)) {
/* 114 */             log.log(Level.FINE, str1);
/*     */           }
/* 116 */           return null;
/*     */         } 
/* 118 */         RetrievalMethod retrievalMethod1 = new RetrievalMethod(element, paramString);
/* 119 */         XMLSignatureInput xMLSignatureInput1 = resolveInput(retrievalMethod1, paramString, this.secureValidation);
/* 120 */         Element element1 = obtainReferenceElement(xMLSignatureInput1);
/* 121 */         if (element1 == paramElement) {
/* 122 */           if (log.isLoggable(Level.FINE)) {
/* 123 */             log.log(Level.FINE, "Error: Can't have RetrievalMethods pointing to each other");
/*     */           }
/* 125 */           return null;
/*     */         } 
/*     */       } 
/*     */       
/* 129 */       return resolveKey(element, paramString, paramStorageResolver);
/* 130 */     } catch (XMLSecurityException xMLSecurityException) {
/* 131 */       if (log.isLoggable(Level.FINE)) {
/* 132 */         log.log(Level.FINE, "XMLSecurityException", xMLSecurityException);
/*     */       }
/* 134 */     } catch (CertificateException certificateException) {
/* 135 */       if (log.isLoggable(Level.FINE)) {
/* 136 */         log.log(Level.FINE, "CertificateException", certificateException);
/*     */       }
/* 138 */     } catch (IOException iOException) {
/* 139 */       if (log.isLoggable(Level.FINE)) {
/* 140 */         log.log(Level.FINE, "IOException", iOException);
/*     */       }
/* 142 */     } catch (ParserConfigurationException parserConfigurationException) {
/* 143 */       if (log.isLoggable(Level.FINE)) {
/* 144 */         log.log(Level.FINE, "ParserConfigurationException", parserConfigurationException);
/*     */       }
/* 146 */     } catch (SAXException sAXException) {
/* 147 */       if (log.isLoggable(Level.FINE)) {
/* 148 */         log.log(Level.FINE, "SAXException", sAXException);
/*     */       }
/*     */     } 
/* 151 */     return null;
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
/*     */   public X509Certificate engineLookupResolveX509Certificate(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
/* 163 */     if (!XMLUtils.elementIsInSignatureSpace(paramElement, "RetrievalMethod")) {
/* 164 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 168 */       RetrievalMethod retrievalMethod = new RetrievalMethod(paramElement, paramString);
/* 169 */       String str = retrievalMethod.getType();
/* 170 */       XMLSignatureInput xMLSignatureInput = resolveInput(retrievalMethod, paramString, this.secureValidation);
/* 171 */       if ("http://www.w3.org/2000/09/xmldsig#rawX509Certificate".equals(str)) {
/* 172 */         return getRawCertificate(xMLSignatureInput);
/*     */       }
/*     */       
/* 175 */       Element element = obtainReferenceElement(xMLSignatureInput);
/*     */ 
/*     */ 
/*     */       
/* 179 */       if (XMLUtils.elementIsInSignatureSpace(element, "RetrievalMethod")) {
/* 180 */         if (this.secureValidation) {
/* 181 */           String str1 = "Error: It is forbidden to have one RetrievalMethod point to another with secure validation";
/*     */           
/* 183 */           if (log.isLoggable(Level.FINE)) {
/* 184 */             log.log(Level.FINE, str1);
/*     */           }
/* 186 */           return null;
/*     */         } 
/* 188 */         RetrievalMethod retrievalMethod1 = new RetrievalMethod(element, paramString);
/* 189 */         XMLSignatureInput xMLSignatureInput1 = resolveInput(retrievalMethod1, paramString, this.secureValidation);
/* 190 */         Element element1 = obtainReferenceElement(xMLSignatureInput1);
/* 191 */         if (element1 == paramElement) {
/* 192 */           if (log.isLoggable(Level.FINE)) {
/* 193 */             log.log(Level.FINE, "Error: Can't have RetrievalMethods pointing to each other");
/*     */           }
/* 195 */           return null;
/*     */         } 
/*     */       } 
/*     */       
/* 199 */       return resolveCertificate(element, paramString, paramStorageResolver);
/* 200 */     } catch (XMLSecurityException xMLSecurityException) {
/* 201 */       if (log.isLoggable(Level.FINE)) {
/* 202 */         log.log(Level.FINE, "XMLSecurityException", xMLSecurityException);
/*     */       }
/* 204 */     } catch (CertificateException certificateException) {
/* 205 */       if (log.isLoggable(Level.FINE)) {
/* 206 */         log.log(Level.FINE, "CertificateException", certificateException);
/*     */       }
/* 208 */     } catch (IOException iOException) {
/* 209 */       if (log.isLoggable(Level.FINE)) {
/* 210 */         log.log(Level.FINE, "IOException", iOException);
/*     */       }
/* 212 */     } catch (ParserConfigurationException parserConfigurationException) {
/* 213 */       if (log.isLoggable(Level.FINE)) {
/* 214 */         log.log(Level.FINE, "ParserConfigurationException", parserConfigurationException);
/*     */       }
/* 216 */     } catch (SAXException sAXException) {
/* 217 */       if (log.isLoggable(Level.FINE)) {
/* 218 */         log.log(Level.FINE, "SAXException", sAXException);
/*     */       }
/*     */     } 
/* 221 */     return null;
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
/*     */   private static X509Certificate resolveCertificate(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/* 235 */     if (log.isLoggable(Level.FINE)) {
/* 236 */       log.log(Level.FINE, "Now we have a {" + paramElement.getNamespaceURI() + "}" + paramElement
/* 237 */           .getLocalName() + " Element");
/*     */     }
/*     */     
/* 240 */     if (paramElement != null) {
/* 241 */       return KeyResolver.getX509Certificate(paramElement, paramString, paramStorageResolver);
/*     */     }
/* 243 */     return null;
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
/*     */   private static PublicKey resolveKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/* 257 */     if (log.isLoggable(Level.FINE)) {
/* 258 */       log.log(Level.FINE, "Now we have a {" + paramElement.getNamespaceURI() + "}" + paramElement
/* 259 */           .getLocalName() + " Element");
/*     */     }
/*     */     
/* 262 */     if (paramElement != null) {
/* 263 */       return KeyResolver.getPublicKey(paramElement, paramString, paramStorageResolver);
/*     */     }
/* 265 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Element obtainReferenceElement(XMLSignatureInput paramXMLSignatureInput) throws CanonicalizationException, ParserConfigurationException, IOException, SAXException, KeyResolverException {
/*     */     Element element;
/* 272 */     if (paramXMLSignatureInput.isElement()) {
/* 273 */       element = (Element)paramXMLSignatureInput.getSubNode();
/* 274 */     } else if (paramXMLSignatureInput.isNodeSet()) {
/*     */       
/* 276 */       element = getDocumentElement(paramXMLSignatureInput.getNodeSet());
/*     */     } else {
/*     */       
/* 279 */       byte[] arrayOfByte = paramXMLSignatureInput.getBytes();
/* 280 */       element = getDocFromBytes(arrayOfByte);
/*     */       
/* 282 */       if (log.isLoggable(Level.FINE)) {
/* 283 */         log.log(Level.FINE, "we have to parse " + arrayOfByte.length + " bytes");
/*     */       }
/*     */     } 
/* 286 */     return element;
/*     */   }
/*     */ 
/*     */   
/*     */   private static X509Certificate getRawCertificate(XMLSignatureInput paramXMLSignatureInput) throws CanonicalizationException, IOException, CertificateException {
/* 291 */     byte[] arrayOfByte = paramXMLSignatureInput.getBytes();
/*     */ 
/*     */     
/* 294 */     CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
/* 295 */     return (X509Certificate)certificateFactory
/* 296 */       .generateCertificate(new ByteArrayInputStream(arrayOfByte));
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
/*     */   private static XMLSignatureInput resolveInput(RetrievalMethod paramRetrievalMethod, String paramString, boolean paramBoolean) throws XMLSecurityException {
/* 308 */     Attr attr = paramRetrievalMethod.getURIAttr();
/*     */     
/* 310 */     Transforms transforms = paramRetrievalMethod.getTransforms();
/* 311 */     ResourceResolver resourceResolver = ResourceResolver.getInstance(attr, paramString, paramBoolean);
/* 312 */     XMLSignatureInput xMLSignatureInput = resourceResolver.resolve(attr, paramString, paramBoolean);
/* 313 */     if (transforms != null) {
/* 314 */       if (log.isLoggable(Level.FINE)) {
/* 315 */         log.log(Level.FINE, "We have Transforms");
/*     */       }
/* 317 */       xMLSignatureInput = transforms.performTransforms(xMLSignatureInput);
/*     */     } 
/* 319 */     return xMLSignatureInput;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Element getDocFromBytes(byte[] paramArrayOfbyte) throws KeyResolverException {
/*     */     try {
/* 331 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 332 */       documentBuilderFactory.setNamespaceAware(true);
/* 333 */       documentBuilderFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE.booleanValue());
/* 334 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 335 */       Document document = documentBuilder.parse(new ByteArrayInputStream(paramArrayOfbyte));
/* 336 */       return document.getDocumentElement();
/* 337 */     } catch (SAXException sAXException) {
/* 338 */       throw new KeyResolverException("empty", sAXException);
/* 339 */     } catch (IOException iOException) {
/* 340 */       throw new KeyResolverException("empty", iOException);
/* 341 */     } catch (ParserConfigurationException parserConfigurationException) {
/* 342 */       throw new KeyResolverException("empty", parserConfigurationException);
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
/*     */   public SecretKey engineLookupAndResolveSecretKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
/* 356 */     return null;
/*     */   }
/*     */   
/*     */   private static Element getDocumentElement(Set<Node> paramSet) {
/* 360 */     Iterator<Node> iterator = paramSet.iterator();
/* 361 */     Element element1 = null;
/* 362 */     while (iterator.hasNext()) {
/* 363 */       Node node = iterator.next();
/* 364 */       if (node != null && 1 == node.getNodeType()) {
/* 365 */         element1 = (Element)node;
/*     */         break;
/*     */       } 
/*     */     } 
/* 369 */     ArrayList<Element> arrayList = new ArrayList();
/*     */ 
/*     */     
/* 372 */     while (element1 != null) {
/* 373 */       arrayList.add(element1);
/* 374 */       Node node = element1.getParentNode();
/* 375 */       if (node == null || 1 != node.getNodeType()) {
/*     */         break;
/*     */       }
/* 378 */       element1 = (Element)node;
/*     */     } 
/*     */     
/* 381 */     ListIterator<Element> listIterator = arrayList.listIterator(arrayList.size() - 1);
/* 382 */     Element element2 = null;
/* 383 */     while (listIterator.hasPrevious()) {
/* 384 */       element2 = listIterator.previous();
/* 385 */       if (paramSet.contains(element2)) {
/* 386 */         return element2;
/*     */       }
/*     */     } 
/* 389 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/keyresolver/implementations/RetrievalMethodResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */