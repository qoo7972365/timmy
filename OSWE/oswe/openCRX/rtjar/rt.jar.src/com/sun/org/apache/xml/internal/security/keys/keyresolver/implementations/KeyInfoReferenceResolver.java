/*     */ package com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.KeyInfo;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.KeyInfoReference;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverSpi;
/*     */ import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolver;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolver;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
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
/*     */ public class KeyInfoReferenceResolver
/*     */   extends KeyResolverSpi
/*     */ {
/*  46 */   private static Logger log = Logger.getLogger(KeyInfoReferenceResolver.class.getName());
/*     */ 
/*     */   
/*     */   public boolean engineCanResolve(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
/*  50 */     return XMLUtils.elementIsInSignature11Space(paramElement, "KeyInfoReference");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PublicKey engineLookupAndResolvePublicKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/*  57 */     if (log.isLoggable(Level.FINE)) {
/*  58 */       log.log(Level.FINE, "Can I resolve " + paramElement.getTagName());
/*     */     }
/*     */     
/*  61 */     if (!engineCanResolve(paramElement, paramString, paramStorageResolver)) {
/*  62 */       return null;
/*     */     }
/*     */     
/*     */     try {
/*  66 */       KeyInfo keyInfo = resolveReferentKeyInfo(paramElement, paramString, paramStorageResolver);
/*  67 */       if (keyInfo != null) {
/*  68 */         return keyInfo.getPublicKey();
/*     */       }
/*  70 */     } catch (XMLSecurityException xMLSecurityException) {
/*  71 */       if (log.isLoggable(Level.FINE)) {
/*  72 */         log.log(Level.FINE, "XMLSecurityException", xMLSecurityException);
/*     */       }
/*     */     } 
/*     */     
/*  76 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509Certificate engineLookupResolveX509Certificate(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/*  83 */     if (log.isLoggable(Level.FINE)) {
/*  84 */       log.log(Level.FINE, "Can I resolve " + paramElement.getTagName());
/*     */     }
/*     */     
/*  87 */     if (!engineCanResolve(paramElement, paramString, paramStorageResolver)) {
/*  88 */       return null;
/*     */     }
/*     */     
/*     */     try {
/*  92 */       KeyInfo keyInfo = resolveReferentKeyInfo(paramElement, paramString, paramStorageResolver);
/*  93 */       if (keyInfo != null) {
/*  94 */         return keyInfo.getX509Certificate();
/*     */       }
/*  96 */     } catch (XMLSecurityException xMLSecurityException) {
/*  97 */       if (log.isLoggable(Level.FINE)) {
/*  98 */         log.log(Level.FINE, "XMLSecurityException", xMLSecurityException);
/*     */       }
/*     */     } 
/*     */     
/* 102 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecretKey engineLookupAndResolveSecretKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/* 109 */     if (log.isLoggable(Level.FINE)) {
/* 110 */       log.log(Level.FINE, "Can I resolve " + paramElement.getTagName());
/*     */     }
/*     */     
/* 113 */     if (!engineCanResolve(paramElement, paramString, paramStorageResolver)) {
/* 114 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 118 */       KeyInfo keyInfo = resolveReferentKeyInfo(paramElement, paramString, paramStorageResolver);
/* 119 */       if (keyInfo != null) {
/* 120 */         return keyInfo.getSecretKey();
/*     */       }
/* 122 */     } catch (XMLSecurityException xMLSecurityException) {
/* 123 */       if (log.isLoggable(Level.FINE)) {
/* 124 */         log.log(Level.FINE, "XMLSecurityException", xMLSecurityException);
/*     */       }
/*     */     } 
/*     */     
/* 128 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrivateKey engineLookupAndResolvePrivateKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/* 135 */     if (log.isLoggable(Level.FINE)) {
/* 136 */       log.log(Level.FINE, "Can I resolve " + paramElement.getTagName());
/*     */     }
/*     */     
/* 139 */     if (!engineCanResolve(paramElement, paramString, paramStorageResolver)) {
/* 140 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 144 */       KeyInfo keyInfo = resolveReferentKeyInfo(paramElement, paramString, paramStorageResolver);
/* 145 */       if (keyInfo != null) {
/* 146 */         return keyInfo.getPrivateKey();
/*     */       }
/* 148 */     } catch (XMLSecurityException xMLSecurityException) {
/* 149 */       if (log.isLoggable(Level.FINE)) {
/* 150 */         log.log(Level.FINE, "XMLSecurityException", xMLSecurityException);
/*     */       }
/*     */     } 
/*     */     
/* 154 */     return null;
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
/*     */   private KeyInfo resolveReferentKeyInfo(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws XMLSecurityException {
/* 167 */     KeyInfoReference keyInfoReference = new KeyInfoReference(paramElement, paramString);
/* 168 */     Attr attr = keyInfoReference.getURIAttr();
/*     */     
/* 170 */     XMLSignatureInput xMLSignatureInput = resolveInput(attr, paramString, this.secureValidation);
/*     */     
/* 172 */     Element element = null;
/*     */     try {
/* 174 */       element = obtainReferenceElement(xMLSignatureInput);
/* 175 */     } catch (Exception exception) {
/* 176 */       if (log.isLoggable(Level.FINE)) {
/* 177 */         log.log(Level.FINE, "XMLSecurityException", exception);
/*     */       }
/* 179 */       return null;
/*     */     } 
/*     */     
/* 182 */     if (element == null) {
/* 183 */       log.log(Level.FINE, "De-reference of KeyInfoReference URI returned null: " + attr.getValue());
/* 184 */       return null;
/*     */     } 
/*     */     
/* 187 */     validateReference(element);
/*     */     
/* 189 */     KeyInfo keyInfo = new KeyInfo(element, paramString);
/* 190 */     keyInfo.addStorageResolver(paramStorageResolver);
/* 191 */     return keyInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void validateReference(Element paramElement) throws XMLSecurityException {
/* 202 */     if (!XMLUtils.elementIsInSignatureSpace(paramElement, "KeyInfo")) {
/* 203 */       Object[] arrayOfObject = { new QName(paramElement.getNamespaceURI(), paramElement.getLocalName()) };
/* 204 */       throw new XMLSecurityException("KeyInfoReferenceResolver.InvalidReferentElement.WrongType", arrayOfObject);
/*     */     } 
/*     */     
/* 207 */     KeyInfo keyInfo = new KeyInfo(paramElement, "");
/* 208 */     if (keyInfo.containsKeyInfoReference()) {
/* 209 */       if (this.secureValidation) {
/* 210 */         throw new XMLSecurityException("KeyInfoReferenceResolver.InvalidReferentElement.ReferenceWithSecure");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 215 */       throw new XMLSecurityException("KeyInfoReferenceResolver.InvalidReferentElement.ReferenceWithoutSecure");
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
/*     */   private XMLSignatureInput resolveInput(Attr paramAttr, String paramString, boolean paramBoolean) throws XMLSecurityException {
/* 232 */     ResourceResolver resourceResolver = ResourceResolver.getInstance(paramAttr, paramString, paramBoolean);
/* 233 */     return resourceResolver.resolve(paramAttr, paramString, paramBoolean);
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
/*     */   private Element obtainReferenceElement(XMLSignatureInput paramXMLSignatureInput) throws CanonicalizationException, ParserConfigurationException, IOException, SAXException, KeyResolverException {
/*     */     Element element;
/* 253 */     if (paramXMLSignatureInput.isElement())
/* 254 */     { element = (Element)paramXMLSignatureInput.getSubNode(); }
/* 255 */     else { if (paramXMLSignatureInput.isNodeSet()) {
/* 256 */         log.log(Level.FINE, "De-reference of KeyInfoReference returned an unsupported NodeSet");
/* 257 */         return null;
/*     */       } 
/*     */       
/* 260 */       byte[] arrayOfByte = paramXMLSignatureInput.getBytes();
/* 261 */       element = getDocFromBytes(arrayOfByte); }
/*     */     
/* 263 */     return element;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Element getDocFromBytes(byte[] paramArrayOfbyte) throws KeyResolverException {
/*     */     try {
/* 275 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 276 */       documentBuilderFactory.setNamespaceAware(true);
/* 277 */       documentBuilderFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE.booleanValue());
/* 278 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 279 */       Document document = documentBuilder.parse(new ByteArrayInputStream(paramArrayOfbyte));
/* 280 */       return document.getDocumentElement();
/* 281 */     } catch (SAXException sAXException) {
/* 282 */       throw new KeyResolverException("empty", sAXException);
/* 283 */     } catch (IOException iOException) {
/* 284 */       throw new KeyResolverException("empty", iOException);
/* 285 */     } catch (ParserConfigurationException parserConfigurationException) {
/* 286 */       throw new KeyResolverException("empty", parserConfigurationException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/keyresolver/implementations/KeyInfoReferenceResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */